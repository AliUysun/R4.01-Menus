package com.yourcompany.menus.application;

import com.yourcompany.menus.application.port.IMenuRepository;
import com.yourcompany.menus.application.port.IUserClient;
import com.yourcompany.menus.domain.entity.Menu;
import com.yourcompany.menus.domain.service.MenuDomainService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests pour le service applicatif unifié MenuApplicationService
 */
class MenuApplicationServiceTest {

    private final MenuDomainService menuDomainService = new MenuDomainService();
    private final FakeMenuRepository repository = new FakeMenuRepository();
    private final FakeUserClient userClient = new FakeUserClient();
    private final MenuApplicationService service = createServiceWithFakes();

    private MenuApplicationService createServiceWithFakes() {
        MenuApplicationService svc = new MenuApplicationService();
        // Utiliser la réflexion pour injecter les dépendances
        try {
            var menuRepositoryField = MenuApplicationService.class.getDeclaredField("menuRepository");
            menuRepositoryField.setAccessible(true);
            menuRepositoryField.set(svc, repository);

            var userClientField = MenuApplicationService.class.getDeclaredField("userClient");
            userClientField.setAccessible(true);
            userClientField.set(svc, userClient);

            var domainServiceField = MenuApplicationService.class.getDeclaredField("menuDomainService");
            domainServiceField.setAccessible(true);
            domainServiceField.set(svc, menuDomainService);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return svc;
    }

    @Test
    void creeUnNouveauMenu() {
        Menu menu = service.createMenu("Menu Test", 2);

        assertNotNull(menu.getId());
        assertEquals("Menu Test", menu.getNom());
        assertEquals(Integer.valueOf(2), menu.getCreateurId());
        assertEquals("Martin", menu.getCreateurNom());
        assertEquals(BigDecimal.ZERO, menu.getPrixTotal());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void rejetteSiCreateurInconnu() {
        assertThrows(IllegalArgumentException.class, () -> service.createMenu("Menu", 999));
    }

    @Test
    void listeAllMenus() {
        service.createMenu("Menu 1", 1);
        service.createMenu("Menu 2", 2);

        List<Menu> menus = service.listMenus();

        assertEquals(2, menus.size());
    }

    @Test
    void retrouveUnMenuParId() {
        Menu created = service.createMenu("Menu Trouve", 1);

        Menu found = service.getMenuById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("Menu Trouve", found.getNom());
    }

    @Test
    void rejetteSiMenuIntrouvable() {
        assertThrows(IllegalArgumentException.class, () -> service.getMenuById(999));
    }

    @Test
    void mettAJourUnMenu() {
        Menu menu = service.createMenu("Ancien Nom", 1);

        Menu updated = service.updateMenu(menu.getId(), "Nouveau Nom", 2);

        assertEquals("Nouveau Nom", updated.getNom());
        assertEquals(Integer.valueOf(2), updated.getCreateurId());
        assertEquals("Martin", updated.getCreateurNom());
    }

    @Test
    void supprimeUnMenu() {
        Menu menu = service.createMenu("A Supprimer", 1);

        service.deleteMenu(menu.getId());

        assertThrows(IllegalArgumentException.class, () -> service.getMenuById(menu.getId()));
    }

    // ==================== FAKES ====================

    private static class FakeMenuRepository implements IMenuRepository {
        private final Map<Integer, Menu> db = new HashMap<>();
        private int seq = 0;

        @Override
        public Integer nextId() {
            return ++seq;
        }

        @Override
        public Menu save(Menu menu) {
            db.put(menu.getId(), menu);
            return menu;
        }

        @Override
        public List<Menu> findAll() {
            return new ArrayList<>(db.values());
        }

        @Override
        public Optional<Menu> findById(Integer id) {
            return Optional.ofNullable(db.get(id));
        }

        @Override
        public void deleteById(Integer id) {
            db.remove(id);
        }
    }


    private static class FakeUserClient implements IUserClient {
        @Override
        public String getUserNameById(Integer userId) {
            return switch (userId) {
                case 1 -> "Dupont";
                case 2 -> "Martin";
                case 3 -> "Bernard";
                default -> throw new IllegalArgumentException("Utilisateur introuvable: " + userId);
            };
        }
    }
}

