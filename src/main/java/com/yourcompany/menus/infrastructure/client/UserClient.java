package com.yourcompany.menus.infrastructure.client;

import com.yourcompany.menus.application.port.IUserClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Client HTTP pour récupérer les utilisateurs depuis l'API "Plats et Utilisateurs"
 * 
 * Implémente le port IUserClient.
 * Appelle l'API distante sur http://localhost:3003/utilisateurs
 * 
 * @author Projet Menus
 * @version 1.0
 */
@ApplicationScoped
public class UserClient implements IUserClient {

    private static final String API_URL = "http://localhost:3003/utilisateurs";
    private HttpClient httpClient;

    // Constructeur vide pour CDI
    public UserClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Récupère le nom d'un utilisateur par son ID
     * 
     * @param userId l'identifiant de l'utilisateur
     * @return le nom de l'utilisateur
     * @throws IllegalArgumentException si l'utilisateur n'existe pas (404)
     * @throws IllegalStateException si une erreur serveur survient (5xx) ou problème réseau
     */
    @Override
    public String getUserNameById(Integer userId) {
        try {
            String url = API_URL + "/" + userId;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                throw new IllegalArgumentException("Utilisateur introuvable: " + userId);
            }

            if (response.statusCode() != 200) {
                throw new IllegalStateException("Erreur API Plats et Utilisateurs: " + response.statusCode());
            }

            // Parse la réponse JSON
            JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
            JsonObject jsonObject = jsonReader.readObject();

            return jsonObject.getString("nom");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Requête interrompue vers l'API Plats et Utilisateurs", e);
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors de l'appel à l'API Plats et Utilisateurs", e);
        }
    }
}

