package org.example.bestelapp.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpSession;
import org.example.bestelapp.Model.Order;
import org.example.bestelapp.Model.OrderItem;
import org.example.bestelapp.Model.User;
import org.example.bestelapp.Repository.OrderDAO;
import org.example.bestelapp.Repository.UserDAO;
import org.springframework.ui.Model;
import org.example.bestelapp.Model.Product;
import org.example.bestelapp.Repository.CategoryDAO;
import org.example.bestelapp.Repository.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/index")
public class ProductViewController {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public ProductViewController(ProductDAO productDAO, CategoryDAO categoryDAO, OrderDAO orderDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
    }

    @GetMapping
    public String showProducts(@RequestParam(required = false) String search,
                               @RequestParam(required = false) Integer category,
                               HttpSession session,
                               Model model) {

        List<Product> allProducts;

        if (search != null && !search.isEmpty()) {
            allProducts = productDAO.findByNameContainingIgnoreCase(search);
        } else if (category != null && category != 0) {
            allProducts = productDAO.findByCategoryId(category);
        } else {
            allProducts = productDAO.findAll();
        }

        List<Product> popularProducts = productDAO.findTop6ByOrderByTimesOrderedDesc();

        Map<String, List<Product>> productsByCategory = new LinkedHashMap<>();
        categoryDAO.findAll().forEach(cat -> {
            List<Product> categoryProducts = allProducts.stream()
                    .filter(p -> p.getCategory().getId() == cat.getId())
                    .toList();
            if (!categoryProducts.isEmpty()) {
                productsByCategory.put(cat.getName(), categoryProducts);
            }
        });

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        Map<Product, Integer> cartItems = new HashMap<>();

        if (cart != null) {
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                productDAO.findById(entry.getKey()).ifPresent(product -> {
                    cartItems.put(product, entry.getValue());
                });
            }
        }

        model.addAttribute("popularProducts", popularProducts);
        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchTerm", search);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotalItems", cartItems.values().stream().mapToInt(Integer::intValue).sum());

        return "index";
    }

    @PostMapping("/add")
    public String AddToCart(@RequestParam("productId") Integer productId,
                            @RequestParam("aantal") Integer aantal,
                            HttpSession session) {

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        cart.put(productId, cart.getOrDefault(productId, 0) + aantal);
        session.setAttribute("cart", cart);

        return "redirect:/index";
    }

    @PostMapping("/remove")
    public String RemoveFromCart(@RequestParam("productId") Integer productId,
                                 HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(productId);
            session.setAttribute("cart", cart);
        }
        return "redirect:/index";
    }

    @PostMapping("/placeorder")
    public String ProcesOrder(@RequestParam("pickupLocation") String pickupLocation,
                              HttpSession session) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden: " + email));

        if (user == null) {
            return "redirect:/login";
        }

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/index";
        }

        Order order = new Order();
        order.setUser(user);
        order.setDate(LocalDate.now());
        order.setStatus(false);
        order.setPickupLocation(pickupLocation);

        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = productDAO.findById(entry.getKey()).orElse(null);
            if (product != null) {
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(entry.getValue());
                orderItems.add(item);

                product.setTimesOrdered(product.getTimesOrdered() + entry.getValue());
                productDAO.save(product);
            }
        }


        order.setOrderItems(orderItems);
        orderDAO.save(order);

        session.removeAttribute("cart");

        return "redirect:/index";
    }

}
