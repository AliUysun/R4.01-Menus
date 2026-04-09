package com.yourcompany.menus.application.port;

/**
 * Port de sortie pour accéder aux utilisateurs
 * 
 * Interface de contrat pour récupérer les informations des utilisateurs depuis l'API externe "Plats et Utilisateurs".
 * Implémentée par UserClient.
 * 
 * @author Projet Menus
 * @version 1.0
 */
public interface IUserClient {
    /**
     * Récupère le nom d'un utilisateur par son ID
     * @param userId l'identifiant de l'utilisateur
     * @return le nom de l'utilisateur
     * @throws IllegalArgumentException si l'utilisateur n'existe pas
     */
    String getUserNameById(Integer userId);
}

