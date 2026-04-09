package com.yourcompany.menus.domain.service;

import com.yourcompany.menus.domain.entity.Menu;
import com.yourcompany.menus.domain.entity.Plat;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;

/**
 * Service métier pour la logique de validation et calcul des menus
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
     * - Tous les plats ont un prix valide (>= 0)
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
        for (Plat plat : menu.getPlats()) {
            if (plat.getPrix() == null || plat.getPrix().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Le prix d'un plat est invalide");
            }
        }
    }

    /**
     * Calcule le prix total d'un menu
     * 
     * Le prix total est la somme des prix de tous les plats du menu.
     * 
     * @param menu le menu dont on veut calculer le prix total
     * @return le prix total (somme des prix des plats)
     */
    public BigDecimal calculerPrixTotal(Menu menu) {
        return menu.getPlats().stream()
                .map(Plat::getPrix)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

