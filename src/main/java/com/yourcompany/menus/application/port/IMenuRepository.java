package com.yourcompany.menus.application.port;

import com.yourcompany.menus.domain.entity.Menu;

import java.util.List;
import java.util.Optional;

/**
 * Interface pour l'accès aux menus en base de données
 */
public interface IMenuRepository {
    /**
     * Obtient le prochain ID disponible
     * @return le prochain ID
     */
    Integer nextId();

    /**
     * Sauvegarde ou met à jour un menu
     * @param menu le menu à sauvegarder
     * @return le menu sauvegardé
     */
    Menu save(Menu menu);

    /**
     * Récupère tous les menus
     * @return la liste de tous les menus
     */
    List<Menu> findAll();

    /**
     * Récupère un menu par son ID
     * @param id l'identifiant du menu
     * @return le menu ou Optional.empty()
     */
    Optional<Menu> findById(Integer id);

    /**
     * Supprime un menu
     * @param id l'identifiant du menu
     */
    void deleteById(Integer id);
}

