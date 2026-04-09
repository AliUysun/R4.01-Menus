package com.yourcompany.menus.domain.service;

import com.yourcompany.menus.domain.entity.Menu;
import com.yourcompany.menus.domain.entity.Plat;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests pour le service métier MenuDomainService
 */
class MenuMetierTest {

    private final MenuDomainService menuDomainService = new MenuDomainService();

    @Test
    void calculePrixTotal() {
        Menu menu = new Menu(1, "Midi", 2, "Martin", LocalDate.now(), LocalDate.now(), List.of(
                new Plat(1, "Salade", new BigDecimal("5.00")),
                new Plat(2, "Burger", new BigDecimal("10.50"))
        ));

        BigDecimal total = menuDomainService.calculerPrixTotal(menu);

        assertEquals(new BigDecimal("15.50"), total);
    }

    @Test
    void refuseMenuSansNom() {
        Menu menu = new Menu(1, "", 2, "Martin", LocalDate.now(), LocalDate.now(), List.of(
                new Plat(1, "Salade", new BigDecimal("5.00"))
        ));

        assertThrows(IllegalArgumentException.class, () -> menuDomainService.validerMenu(menu));
    }

    @Test
    void accepteMenuAvecNom() {
        Menu menu = new Menu(1, "Menu Valide", 2, "Martin", LocalDate.now(), LocalDate.now(), List.of(
                new Plat(1, "Salade", new BigDecimal("5.00"))
        ));

        // Ne doit pas lever d'exception
        menuDomainService.validerMenu(menu);
    }

    @Test
    void calculePrixTotalZeroSiAucunPlat() {
        Menu menu = new Menu(1, "Vide", 2, "Martin", LocalDate.now(), LocalDate.now(), List.of());

        BigDecimal total = menuDomainService.calculerPrixTotal(menu);

        assertEquals(BigDecimal.ZERO, total);
    }
}


