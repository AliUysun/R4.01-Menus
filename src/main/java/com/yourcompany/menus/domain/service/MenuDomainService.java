package com.yourcompany.menus.domain.service;

import com.yourcompany.menus.domain.entity.Menu;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service métier pour la validation des menus
 * 
 * Responsable de la cohérence des données du domaine.
 * Ce service encapsule la logique métier pure, indépendante de toute infrastructure.
 * 
 * @author Projet Menus
 * @version 1.0
 */
@ApplicationScoped
public class MenuDomainService {

    /**
     * Valide les règles métier d'un menu
     * 
     * Vérifie que :
     * - Le menu n'est pas null
     * - Le nom du menu n'est pas vide
     * - Le createurId est renseigné
     * 
     * @param menu le menu à valider
     * @throws IllegalArgumentException si une validation échoue
     */
    public void validerMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Le menu est obligatoire");
        }
        if (menu.getNom() == null || menu.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du menu est obligatoire");
        }
        if (menu.getCreateurId() == null) {
            throw new IllegalArgumentException("Le createurId est obligatoire");
        }
    }
}

