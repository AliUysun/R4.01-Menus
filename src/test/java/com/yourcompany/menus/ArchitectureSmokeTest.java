package com.yourcompany.menus;

import com.yourcompany.menus.infrastructure.repository.menu.MenuRepository;
import com.yourcompany.menus.application.MenuApplicationService;
import com.yourcompany.menus.infrastructure.client.PlatClient;
import com.yourcompany.menus.domain.service.MenuDomainService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test de smoke pour vérifier que les composants principaux s'instancient correctement
 */
class ArchitectureSmokeTest {

    @Test
    void composantsPrincipauxSontInstanciables() {
        assertDoesNotThrow(() -> {
            // Vérifier que chaque composant s'instancie sans erreur
            new MenuRepository();
            new PlatClient();
            new MenuDomainService();
            new MenuApplicationService();
        });
    }
}
