package com.yourcompany.menus.infrastructure.repository.menu;

/**
 * Constantes SQL pour le repository Menu
 * 
 * Centralise TOUTES les requêtes SQL au même endroit pour :
 * - Faciliter la maintenance
 * - Éviter la duplication
 * - Avoir une vue d'ensemble des opérations BD
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class MenuSqlQueries {
    public static final String SELECT_NEXT_ID = 
            "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM menus";
    
    public static final String SELECT_MENUS = 
            "SELECT id, nom, createur_id, createur_nom, date_creation, date_mise_a_jour, prix_total FROM menus ORDER BY id";
    
    public static final String SELECT_MENU_BY_ID = 
            "SELECT id, nom, createur_id, createur_nom, date_creation, date_mise_a_jour, prix_total FROM menus WHERE id = ?";
    
    public static final String UPSERT_MENU = 
            "INSERT INTO menus (id, nom, createur_id, createur_nom, date_creation, date_mise_a_jour, prix_total) VALUES (?, ?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE nom = VALUES(nom), createur_id = VALUES(createur_id), createur_nom = VALUES(createur_nom), " +
            "date_creation = VALUES(date_creation), date_mise_a_jour = VALUES(date_mise_a_jour), prix_total = VALUES(prix_total)";
    
    public static final String DELETE_MENU = 
            "DELETE FROM menus WHERE id = ?";

    private MenuSqlQueries() {
        // Classe utilitaire
    }
}

