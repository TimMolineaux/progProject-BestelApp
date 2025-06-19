package org.example.bestelapp;

import org.example.bestelapp.Model.*;
import org.example.bestelapp.Repository.CategoryDAO;
import org.example.bestelapp.Repository.ProductDAO;
import org.example.bestelapp.Repository.RoleDAO;
import org.example.bestelapp.Repository.UserDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initRoles(RoleDAO roleDAO, UserDAO userDAO, CategoryDAO categoryDAO, ProductDAO productDAO) {
        return args -> {

            if (roleDAO.count() == 0) {
                roleDAO.save(new Role(0, "admin"));
                roleDAO.save(new Role(0, "technieker"));
            }

            if (userDAO.count() == 0) {
                Optional<Role> adminRole = roleDAO.findAll().stream()
                        .filter(r -> r.getName().equalsIgnoreCase("admin"))
                        .findFirst();

                if (adminRole.isPresent()) {
                    User admin = new User();
                    admin.setEmail("admin@aquafin.be");
                    admin.setPassword("$2a$10$r3/LRgcvIvUKW66VQnc5eOP7nFXWhMcwJnwsdxwto9iqp1TP9OUUu");
                    admin.setRole(adminRole.get());

                    userDAO.save(admin);
                }
            }

            if (categoryDAO.count() == 0) {
                categoryDAO.save(new Category(0, "Bevestigingsmateriaal"));
                categoryDAO.save(new Category(0, "Persoonlijke beschermingsmiddelen"));
                categoryDAO.save(new Category(0, "Gereedschap"));
                categoryDAO.save(new Category(0, "Technische onderhoudsmaterialen"));
                categoryDAO.save(new Category(0, "Specifieke Aquafin / riolering gerelateerde tools"));
                categoryDAO.save(new Category(0, "Diversen / Verbruiksgoederen"));
            }

            if (productDAO.count() == 0) {
                Map<String, Category> categories = categoryDAO.findAll().stream()
                        .collect(Collectors.toMap(Category::getName, c -> c));

                productDAO.saveAll(List.of(
                        new Product("Bouten: M6", categories.get("Bevestigingsmateriaal"), 100, 0),
                        new Product("Bouten: M8", categories.get("Bevestigingsmateriaal"), 100, 0),
                        new Product("Bouten: M10", categories.get("Bevestigingsmateriaal"), 100, 0),
                        new Product("Bouten: M12", categories.get("Bevestigingsmateriaal"), 100, 0),
                        new Product("Bouten: M16", categories.get("Bevestigingsmateriaal"), 100, 0),
                        new Product("Moeren: zeskantmoeren", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Moeren: borgmoeren", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Moeren: flensmoeren", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Ringen: sluitringen", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Ringen: veerringen", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Ringen: tandringen", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Ankerbouten", categories.get("Bevestigingsmateriaal"), 30, 0),
                        new Product("Chemische ankers (bv. Hilti HIT)", categories.get("Bevestigingsmateriaal"), 20, 0),
                        new Product("Keilbouten", categories.get("Bevestigingsmateriaal"), 40, 0),
                        new Product("Draadstangen (M6 t.e.m. M16)", categories.get("Bevestigingsmateriaal"), 60, 0),
                        new Product("Inslagmoeren", categories.get("Bevestigingsmateriaal"), 30, 0),
                        new Product("Tapbouten", categories.get("Bevestigingsmateriaal"), 40, 0),
                        new Product("Zeskantkop- en inbusbouten", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Torx- en kruiskopschroeven", categories.get("Bevestigingsmateriaal"), 70, 0),
                        new Product("Zelftappende vijzen", categories.get("Bevestigingsmateriaal"), 100, 0),
                        new Product("Parkervijzen", categories.get("Bevestigingsmateriaal"), 100, 0),
                        new Product("Spaanplaatschroeven", categories.get("Bevestigingsmateriaal"), 50, 0),
                        new Product("Slangenklemmen (div. diameters)", categories.get("Bevestigingsmateriaal"), 40, 0),

                        new Product("Veiligheidshelm (met kinband)", categories.get("Persoonlijke beschermingsmiddelen"), 20, 0),
                        new Product("Oordoppen / gehoorkappen", categories.get("Persoonlijke beschermingsmiddelen"), 50, 0),
                        new Product("Veiligheidsbril / gelaatsscherm", categories.get("Persoonlijke beschermingsmiddelen"), 50, 0),
                        new Product("Stofmaskers (FFP2, FFP3)", categories.get("Persoonlijke beschermingsmiddelen"), 60, 0),
                        new Product("Werkhandschoenen (snijvast, chemisch resistent, elektrisch geïsoleerd)", categories.get("Persoonlijke beschermingsmiddelen"), 80, 0),
                        new Product("Veiligheidsschoenen (S3, antistatisch, stalen tip)", categories.get("Persoonlijke beschermingsmiddelen"), 40, 0),
                        new Product("Werklaarzen (PVC, nitril, met stalen zool)", categories.get("Persoonlijke beschermingsmiddelen"), 30, 0),
                        new Product("Regenkledij (jassen, broeken, capes)", categories.get("Persoonlijke beschermingsmiddelen"), 20, 0),
                        new Product("Fluovesten / signalisatiekledij (EN ISO 20471)", categories.get("Persoonlijke beschermingsmiddelen"), 40, 0),
                        new Product("Overall (brandvertragend, antistatisch, waterafstotend)", categories.get("Persoonlijke beschermingsmiddelen"), 25, 0),
                        new Product("Valharnas en lijn", categories.get("Persoonlijke beschermingsmiddelen"), 15, 0),
                        new Product("Gasdetectiemeter (O₂, CH₄, H₂S, CO)", categories.get("Persoonlijke beschermingsmiddelen"), 10, 0),
                        new Product("Handontsmetting / EHBO-kit", categories.get("Persoonlijke beschermingsmiddelen"), 50, 0),
                        new Product("Klim- en valbeveiligingsmateriaal (harnas, lifeline, karabijnhaken)", categories.get("Persoonlijke beschermingsmiddelen"), 15, 0),

                        new Product("Dopsleutelsets (metrisch en inch)", categories.get("Gereedschap"), 20, 0),
                        new Product("Ringsleutels, steeksleutels", categories.get("Gereedschap"), 40, 0),
                        new Product("Momentsleutels", categories.get("Gereedschap"), 15, 0),
                        new Product("Inbussleutels (los en in set)", categories.get("Gereedschap"), 30, 0),
                        new Product("Schroevendraaiers (plat, kruiskop, Torx, geïsoleerd)", categories.get("Gereedschap"), 50, 0),
                        new Product("Tangen (combinatie, waterpomptang, kniptang, punttang)", categories.get("Gereedschap"), 40, 0),
                        new Product("Krimptang / kabelschoentang", categories.get("Gereedschap"), 25, 0),
                        new Product("Kabelstripper", categories.get("Gereedschap"), 30, 0),
                        new Product("Hamer, kunststofhamer, moker", categories.get("Gereedschap"), 40, 0),
                        new Product("Breekijzer", categories.get("Gereedschap"), 15, 0),
                        new Product("Slijpmachine (haakse slijper)", categories.get("Gereedschap"), 10, 0),
                        new Product("Accuboormachine / klopboormachine", categories.get("Gereedschap"), 10, 0),
                        new Product("Schroefmachine", categories.get("Gereedschap"), 20, 0),
                        new Product("Slagmoersleutel (pneumatisch of accu)", categories.get("Gereedschap"), 10, 0),
                        new Product("Waterpas / laserwaterpas", categories.get("Gereedschap"), 15, 0),
                        new Product("Meetlint, rolmeter", categories.get("Gereedschap"), 30, 0),
                        new Product("Spanningstester / multimeter", categories.get("Gereedschap"), 25, 0),

                        new Product("Smeervet (foodgrade, EP2, lithium)", categories.get("Technische onderhoudsmaterialen"), 40, 0),
                        new Product("O-ringen (div. maten en types)", categories.get("Technische onderhoudsmaterialen"), 50, 0),
                        new Product("Pakkingen (papier, rubber, EPDM)", categories.get("Technische onderhoudsmaterialen"), 30, 0),
                        new Product("PTFE tape / Loctite", categories.get("Technische onderhoudsmaterialen"), 40, 0),
                        new Product("Slangen (PVC, PE, persslangen)", categories.get("Technische onderhoudsmaterialen"), 20, 0),
                        new Product("PVC-fittingen, bochten, T-stukken", categories.get("Technische onderhoudsmaterialen"), 20, 0),
                        new Product("Koppelingen (Geka, Gardena, Camlock)", categories.get("Technische onderhoudsmaterialen"), 25, 0),
                        new Product("V-snaren / kettingen", categories.get("Technische onderhoudsmaterialen"), 15, 0),
                        new Product("Kabels en wartels (M16–M32)", categories.get("Technische onderhoudsmaterialen"), 10, 0),
                        new Product("Aansluitdozen", categories.get("Technische onderhoudsmaterialen"), 20, 0),
                        new Product("Leidingsystemen (druk/afvoer)", categories.get("Technische onderhoudsmaterialen"), 15, 0),
                        new Product("Pneumatische koppelingen", categories.get("Technische onderhoudsmaterialen"), 10, 0),
                        new Product("Trillingsdempers", categories.get("Technische onderhoudsmaterialen"), 15, 0),

                        new Product("Putdekselhaak / mangatopener", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 8, 0),
                        new Product("Rioolcamera / inspectiecamera", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 5, 0),
                        new Product("Gasdetectietoestellen (H₂S, CO, O₂)", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 10, 0),
                        new Product("Ontstoppingsveer / hogedrukreiniger", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 6, 0),
                        new Product("Slangenwagens", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 4, 0),
                        new Product("Dompelpompen", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 3, 0),
                        new Product("Rioolstoppen", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 8, 0),
                        new Product("Vlotterschakelaars", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 6, 0),
                        new Product("Niveaumeting (ultrasoon, radar)", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 10, 0),
                        new Product("Staalnamepotten", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 12, 0),
                        new Product("Monsternameapparatuur", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 10, 0),

                        new Product("Tie-wraps", categories.get("Diversen / Verbruiksgoederen"), 100, 0),
                        new Product("Kabelschoenen", categories.get("Diversen / Verbruiksgoederen"), 50, 0),
                        new Product("Markeringstape", categories.get("Diversen / Verbruiksgoederen"), 50, 0),
                        new Product("Siliconenkit / lijm", categories.get("Diversen / Verbruiksgoederen"), 40, 0),
                        new Product("Rags (reinigingsdoekjes)", categories.get("Diversen / Verbruiksgoederen"), 50, 0),
                        new Product("Spray’s (WD-40, contactspray, kettingspray)", categories.get("Diversen / Verbruiksgoederen"), 30, 0),
                        new Product("Plakband (duct tape, isolatietape)", categories.get("Diversen / Verbruiksgoederen"), 30, 0),
                        new Product("Batterijen / accu’s", categories.get("Diversen / Verbruiksgoederen"), 40, 0),
                        new Product("Reserveonderdelen (motoren, PLC-onderdelen, relais)", categories.get("Diversen / Verbruiksgoederen"), 20, 0),
                        new Product("Flessen met perslucht / gas", categories.get("Diversen / Verbruiksgoederen"), 10, 0)
                ));
            }
        };
    }
}

