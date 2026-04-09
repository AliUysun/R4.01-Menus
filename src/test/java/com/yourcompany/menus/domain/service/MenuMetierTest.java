package com.yourcompany.menus.domain.service;

import com.yourcompany.menus.domain.entity.Menu;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests pour le service métier MenuDomainService
 */
class MenuMetierTest {

    private final MenuDomainService menuDomainService = new MenuDomainService();

    @Test
    void refuseMenuSansNom() {
        Menu menu = new Menu(1, "", 2, "Martin", LocalDate.now(), LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> menuDomainService.validerMenu(menu));
    }

    @Test
    void accepteMenuAvecNom() {
        Menu menu = new Menu(1, "Menu Valide", 2, "Martin", LocalDate.now(), LocalDate.now());

        // Ne doit pas lever d'exception
        menuDomainService.validerMenu(menu);
    }

    @Test
    void refuseMenuSansCreateur() {
        Menu menu = new Menu(1, "Menu", null, "", LocalDate.now(), LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> menuDomainService.validerMenu(menu));
    }
}


