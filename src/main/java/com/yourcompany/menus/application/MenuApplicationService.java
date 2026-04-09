package com.yourcompany.menus.application;

import com.yourcompany.menus.application.port.IMenuRepository;
import com.yourcompany.menus.application.port.IUserClient;
import com.yourcompany.menus.domain.entity.Menu;
import com.yourcompany.menus.domain.service.MenuDomainService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service applicatif unifié pour tous les use-cases des menus
 * 
 * Orchestre les interactions entre :
 * - Le repository pour la persistance
 * - Les services externes (clients API)
 * - Le service métier pour la logique
 * 
 * Cette classe gère tous les use-cases (CRUD + opérations métier) sur les menus.
 * 
 * @author Projet Menus
 * @version 1.0
 */
@ApplicationScoped
public class MenuApplicationService {

    @Inject
    private IMenuRepository menuRepository;

    @Inject
    private IUserClient userClient;

    @Inject
    private MenuDomainService menuDomainService;


    /**
     * Crée un nouveau menu vide
     * 
     * Le menu est initialisé avec les données fournies et une date de création actuelle.
     * Le nom du créateur est résolu via l'API utilisateurs.
     * 
     * @param nom le nom du menu
     * @param createurId l'id du créateur (utilisateur)
     * @return le menu créé avec son id attribué
     * @throws IllegalArgumentException si le créateur n'existe pas
     */
    public Menu createMenu(String nom, Integer createurId) {
        String createurNom = resolveCreateurNom(createurId);
        LocalDate now = LocalDate.now();
        Menu menu = new Menu(menuRepository.nextId(), nom, createurId, createurNom, now, now);
        menuDomainService.validerMenu(menu);
        menu.setPrixTotal(BigDecimal.ZERO);
        return menuRepository.save(menu);
    }


    /**
     * Liste tous les menus
     * 
     * @return la liste complete de tous les menus
     */
    public List<Menu> listMenus() {
        return menuRepository.findAll();
    }

    /**
     * Récupère un menu par son identifiant
     * 
     * @param id l'identifiant du menu
     * @return le menu demandé
     * @throws IllegalArgumentException si le menu n'existe pas
     */
    public Menu getMenuById(Integer id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable: " + id));
    }


    /**
     * Met à jour le nom d'un menu
     * 
     * Met à jour le nom et le créateur du menu, puis met à jour la date de modification.
     * 
     * @param id l'identifiant du menu à mettre à jour
     * @param nom le nouveau nom
     * @param createurId le nouvel id du créateur
     * @return le menu mis à jour
     * @throws IllegalArgumentException si le menu ou le créateur n'existe pas
     */
    public Menu updateMenu(Integer id, String nom, Integer createurId) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu introuvable: " + id));
        menu.setNom(nom);
        menu.setCreateurId(createurId);
        menu.setCreateurNom(resolveCreateurNom(createurId));
        menu.setDateMiseAJour(LocalDate.now());
        menuDomainService.validerMenu(menu);
        return menuRepository.save(menu);
    }


    /**
     * Supprime un menu
     * 
     * @param id l'identifiant du menu à supprimer
     * @throws IllegalArgumentException si le menu n'existe pas
     */
    public void deleteMenu(Integer id) {
        if (menuRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Menu introuvable: " + id);
        }
        menuRepository.deleteById(id);
    }


    /**
     * Résout le nom du créateur à partir de son ID
     * 
     * @param createurId l'id du créateur
     * @return le nom du créateur
     * @throws IllegalArgumentException si le créateur n'existe pas
     */
    private String resolveCreateurNom(Integer createurId) {
        if (createurId == null) {
            throw new IllegalArgumentException("Le createurId est obligatoire");
        }
        // Récupère le nom du créateur depuis l'API "Plats et Utilisateurs"
        return userClient.getUserNameById(createurId);
    }

}

