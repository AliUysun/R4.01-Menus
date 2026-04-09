package com.yourcompany.menus.application.port;

import com.yourcompany.menus.domain.entity.Plat;

/**
 * Interface pour récupérer les plats disponibles
 * 
 * Port de sortie pour accéder aux plats.
 * Implémentée par PlatClient qui appelle l'API "Plats et Utilisateurs".
 * 
 * @author Projet Menus
 * @version 1.0
 */
public interface IPlatClient {
    /**
     * Récupère un plat par son ID
     * @param id l'identifiant du plat
     * @return le plat avec son nom et prix
     * @throws IllegalArgumentException si le plat n'existe pas
     */
    Plat getPlatById(Integer id);
}

