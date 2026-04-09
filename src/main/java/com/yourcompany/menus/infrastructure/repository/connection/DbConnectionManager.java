package com.yourcompany.menus.infrastructure.repository.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestionnaire de connexions à la base de données
 * 
 * Responsable de :
 * - Charger la configuration (host, port, user, password)
 * - Ouvrir les connexions à la base de données
 * - Faire du caching de la configuration (lazy loading)
 * 
 * Utilise DbConfigLoader pour charger la configuration depuis le fichier .env
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class DbConnectionManager {

    private volatile DbConfigLoader.DbConfig config;

    /**
     * Ouvre une nouvelle connexion à la base de données
     * 
     * La configuration est chargée une seule fois (lazy loading) et mise en cache.
     * 
     * @return une nouvelle connexion
     * @throws SQLException si la connexion échoue
     */
    public Connection openConnection() throws SQLException {
        DbConfigLoader.DbConfig current = config;
        if (current == null) {
            synchronized (this) {
                current = config;
                if (current == null) {
                    current = DbConfigLoader.load();
                    config = current;
                }
            }
        }
        return DriverManager.getConnection(current.jdbcUrl(), current.username(), current.password());
    }
}

