package org.example.bestelapp;

import org.example.bestelapp.Model.*;
import org.example.bestelapp.Repository.CategoryDAO;
import org.example.bestelapp.Repository.ProductDAO;
import org.example.bestelapp.Repository.RoleDAO;
import org.example.bestelapp.Repository.UserDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                    admin.setPassword("admin123"); // nog versleutelen in je echte app
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
                        new Product(0, "Bouten: M6", categories.get("Bevestigingsmateriaal"), 100, false, null),
                        new Product(0, "Bouten: M8", categories.get("Bevestigingsmateriaal"), 100, false, null),
                        new Product(0, "Bouten: M10", categories.get("Bevestigingsmateriaal"), 100, false, null),
                        new Product(0, "Bouten: M12", categories.get("Bevestigingsmateriaal"), 100, false, null),
                        new Product(0, "Bouten: M16", categories.get("Bevestigingsmateriaal"), 100, false, null),
                        new Product(0, "Moeren: zeskantmoeren", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Moeren: borgmoeren", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Moeren: flensmoeren", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Ringen: sluitringen", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Ringen: veerringen", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Ringen: tandringen", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Ankerbouten", categories.get("Bevestigingsmateriaal"), 30, false, null),
                        new Product(0, "Chemische ankers (bv. Hilti HIT)", categories.get("Bevestigingsmateriaal"), 20, false, null),
                        new Product(0, "Keilbouten", categories.get("Bevestigingsmateriaal"), 40, false, null),
                        new Product(0, "Draadstangen (M6 t.e.m. M16)", categories.get("Bevestigingsmateriaal"), 60, false, null),
                        new Product(0, "Inslagmoeren", categories.get("Bevestigingsmateriaal"), 30, false, null),
                        new Product(0, "Tapbouten", categories.get("Bevestigingsmateriaal"), 40, false, null),
                        new Product(0, "Zeskantkop- en inbusbouten", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Torx- en kruiskopschroeven", categories.get("Bevestigingsmateriaal"), 70, false, null),
                        new Product(0, "Zelftappende vijzen", categories.get("Bevestigingsmateriaal"), 100, false, null),
                        new Product(0, "Parkervijzen", categories.get("Bevestigingsmateriaal"), 100, false, null),
                        new Product(0, "Spaanplaatschroeven", categories.get("Bevestigingsmateriaal"), 50, false, null),
                        new Product(0, "Slangenklemmen (div. diameters)", categories.get("Bevestigingsmateriaal"), 40, false, null),

                        new Product(0, "Veiligheidshelm (met kinband)", categories.get("Persoonlijke beschermingsmiddelen"), 20, false, null),
                        new Product(0, "Oordoppen / gehoorkappen", categories.get("Persoonlijke beschermingsmiddelen"), 50, false, null),
                        new Product(0, "Veiligheidsbril / gelaatsscherm", categories.get("Persoonlijke beschermingsmiddelen"), 50, false, null),
                        new Product(0, "Stofmaskers (FFP2, FFP3)", categories.get("Persoonlijke beschermingsmiddelen"), 60, false, null),
                        new Product(0, "Werkhandschoenen (snijvast, chemisch resistent, elektrisch geïsoleerd)", categories.get("Persoonlijke beschermingsmiddelen"), 80, false, null),
                        new Product(0, "Veiligheidsschoenen (S3, antistatisch, stalen tip)", categories.get("Persoonlijke beschermingsmiddelen"), 40, false, null),
                        new Product(0, "Werklaarzen (PVC, nitril, met stalen zool)", categories.get("Persoonlijke beschermingsmiddelen"), 30, false, null),
                        new Product(0, "Regenkledij (jassen, broeken, capes)", categories.get("Persoonlijke beschermingsmiddelen"), 20, false, null),
                        new Product(0, "Fluovesten / signalisatiekledij (EN ISO 20471)", categories.get("Persoonlijke beschermingsmiddelen"), 40, false, null),
                        new Product(0, "Overall (brandvertragend, antistatisch, waterafstotend)", categories.get("Persoonlijke beschermingsmiddelen"), 25, false, null),
                        new Product(0, "Valharnas en lijn", categories.get("Persoonlijke beschermingsmiddelen"), 15, false, null),
                        new Product(0, "Gasdetectiemeter (O₂, CH₄, H₂S, CO)", categories.get("Persoonlijke beschermingsmiddelen"), 10, false, null),
                        new Product(0, "Handontsmetting / EHBO-kit", categories.get("Persoonlijke beschermingsmiddelen"), 50, false, null),
                        new Product(0, "Klim- en valbeveiligingsmateriaal (harnas, lifeline, karabijnhaken)", categories.get("Persoonlijke beschermingsmiddelen"), 15, false, null),

                        new Product(0, "Dopsleutelsets (metrisch en inch)", categories.get("Gereedschap"), 20, false, null),
                        new Product(0, "Ringsleutels, steeksleutels", categories.get("Gereedschap"), 40, false, null),
                        new Product(0, "Momentsleutels", categories.get("Gereedschap"), 15, false, null),
                        new Product(0, "Inbussleutels (los en in set)", categories.get("Gereedschap"), 30, false, null),
                        new Product(0, "Schroevendraaiers (plat, kruiskop, Torx, geïsoleerd)", categories.get("Gereedschap"), 50, false, null),
                        new Product(0, "Tangen (combinatie, waterpomptang, kniptang, punttang)", categories.get("Gereedschap"), 40, false, null),
                        new Product(0, "Krimptang / kabelschoentang", categories.get("Gereedschap"), 25, false, null),
                        new Product(0, "Kabelstripper", categories.get("Gereedschap"), 30, false, null),
                        new Product(0, "Hamer, kunststofhamer, moker", categories.get("Gereedschap"), 40, false, null),
                        new Product(0, "Breekijzer", categories.get("Gereedschap"), 15, false, null),
                        new Product(0, "Slijpmachine (haakse slijper)", categories.get("Gereedschap"), 10, false, null),
                        new Product(0, "Accuboormachine / klopboormachine", categories.get("Gereedschap"), 10, false, null),
                        new Product(0, "Schroefmachine", categories.get("Gereedschap"), 20, false, null),
                        new Product(0, "Slagmoersleutel (pneumatisch of accu)", categories.get("Gereedschap"), 10, false, null),
                        new Product(0, "Waterpas / laserwaterpas", categories.get("Gereedschap"), 15, false, null),
                        new Product(0, "Meetlint, rolmeter", categories.get("Gereedschap"), 30, false, null),
                        new Product(0, "Spanningstester / multimeter", categories.get("Gereedschap"), 25, false, null),

                        new Product(0, "Smeervet (foodgrade, EP2, lithium)", categories.get("Technische onderhoudsmaterialen"), 40, false, null),
                        new Product(0, "O-ringen (div. maten en types)", categories.get("Technische onderhoudsmaterialen"), 50, false, null),
                        new Product(0, "Pakkingen (papier, rubber, EPDM)", categories.get("Technische onderhoudsmaterialen"), 30, false, null),
                        new Product(0, "PTFE tape / Loctite", categories.get("Technische onderhoudsmaterialen"), 40, false, null),
                        new Product(0, "Slangen (PVC, PE, persslangen)", categories.get("Technische onderhoudsmaterialen"), 20, false, null),
                        new Product(0, "PVC-fittingen, bochten, T-stukken", categories.get("Technische onderhoudsmaterialen"), 20, false, null),
                        new Product(0, "Koppelingen (Geka, Gardena, Camlock)", categories.get("Technische onderhoudsmaterialen"), 25, false, null),
                        new Product(0, "V-snaren / kettingen", categories.get("Technische onderhoudsmaterialen"), 15, false, null),
                        new Product(0, "Kabels en wartels (M16–M32)", categories.get("Technische onderhoudsmaterialen"), 10, false, null),
                        new Product(0, "Aansluitdozen", categories.get("Technische onderhoudsmaterialen"), 20, false, null),
                        new Product(0, "Leidingsystemen (druk/afvoer)", categories.get("Technische onderhoudsmaterialen"), 15, false, null),
                        new Product(0, "Pneumatische koppelingen", categories.get("Technische onderhoudsmaterialen"), 10, false, null),
                        new Product(0, "Trillingsdempers", categories.get("Technische onderhoudsmaterialen"), 15, false, null),

                        new Product(0, "Putdekselhaak / mangatopener", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 8, false, null),
                        new Product(0, "Rioolcamera / inspectiecamera", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 5, false, null),
                        new Product(0, "Gasdetectietoestellen (H₂S, CO, O₂)", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 10, false, null),
                        new Product(0, "Ontstoppingsveer / hogedrukreiniger", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 6, false, null),
                        new Product(0, "Slangenwagens", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 4, false, null),
                        new Product(0, "Dompelpompen", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 3, false, null),
                        new Product(0, "Rioolstoppen", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 8, false, null),
                        new Product(0, "Vlotterschakelaars", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 6, false, null),
                        new Product(0, "Niveaumeting (ultrasoon, radar)", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 10, false, null),
                        new Product(0, "Staalnamepotten", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 12, false, null),
                        new Product(0, "Monsternameapparatuur", categories.get("Specifieke Aquafin / riolering gerelateerde tools"), 10, false, null),

                        new Product(0, "Tie-wraps", categories.get("Diversen / Verbruiksgoederen"), 100, false, null),
                        new Product(0, "Kabelschoenen", categories.get("Diversen / Verbruiksgoederen"), 50, false, null),
                        new Product(0, "Markeringstape", categories.get("Diversen / Verbruiksgoederen"), 50, false, null),
                        new Product(0, "Siliconenkit / lijm", categories.get("Diversen / Verbruiksgoederen"), 40, false, null),
                        new Product(0, "Rags (reinigingsdoekjes)", categories.get("Diversen / Verbruiksgoederen"), 50, false, null),
                        new Product(0, "Spray’s (WD-40, contactspray, kettingspray)", categories.get("Diversen / Verbruiksgoederen"), 30, false, null),
                        new Product(0, "Plakband (duct tape, isolatietape)", categories.get("Diversen / Verbruiksgoederen"), 30, false, null),
                        new Product(0, "Batterijen / accu’s", categories.get("Diversen / Verbruiksgoederen"), 40, false, null),
                        new Product(0, "Reserveonderdelen (motoren, PLC-onderdelen, relais)", categories.get("Diversen / Verbruiksgoederen"), 20, false, null),
                        new Product(0, "Flessen met perslucht / gas", categories.get("Diversen / Verbruiksgoederen"), 10, false, null)
                ));
            }
        };
    }
}