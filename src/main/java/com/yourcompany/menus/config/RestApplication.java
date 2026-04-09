package com.yourcompany.menus.config;

import com.yourcompany.menus.rest.MenuController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Configuration de l'application REST
 * 
 * Enregistre les contrôleurs REST et configure le chemin de base de l'API.
 * Point d'entrée de la couche REST/HTTP.
 * 
 * Tous les endpoints seront disponibles à partir de /api/
 * 
 * @author Projet Menus
 * @version 1.0
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    /**
     * Enregistre les contrôleurs REST
     * 
     * @return les classes de contrôleurs à enregistrer
     */
    @Override
    public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<>();
		resources.add(MenuController.class);
		return resources;
	}
}

