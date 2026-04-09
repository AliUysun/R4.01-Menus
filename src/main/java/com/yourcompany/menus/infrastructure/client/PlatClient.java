package com.yourcompany.menus.infrastructure.client;

import com.yourcompany.menus.application.port.IPlatClient;
import com.yourcompany.menus.domain.entity.Plat;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Client HTTP pour récupérer les plats depuis l'API "Plats et Utilisateurs"
 * 
 * Implémente le port IPlatClient.
 * Appelle l'API distante sur http://localhost:3003/plats
 * 
 * Cette classe est responsable de :
 * - La communication HTTP
 * - Le parsing JSON
 * - La gestion des erreurs réseau
 * 
 * @author Projet Menus
 * @version 1.0
 */
@ApplicationScoped
public class PlatClient implements IPlatClient {

    private static final String API_URL = "http://localhost:3003/plats";
    private HttpClient httpClient;

    // Constructeur vide pour CDI
    public PlatClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Récupère un plat par son ID depuis l'API
     * 
     * @param id l'identifiant du plat
     * @return le plat avec son nom et son prix
     * @throws IllegalArgumentException si le plat n'existe pas (404)
     * @throws IllegalStateException si une erreur serveur survient (5xx) ou problème réseau
     */
    @Override
    public Plat getPlatById(Integer id) {
        try {
            String url = API_URL + "/" + id;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                throw new IllegalArgumentException("Plat inconnu: " + id);
            }

            if (response.statusCode() != 200) {
                throw new IllegalStateException("Erreur API Plats et Utilisateurs: " + response.statusCode());
            }

            // Parse la réponse JSON
            JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
            JsonObject jsonObject = jsonReader.readObject();

            int platId = jsonObject.getInt("id");
            String nom = jsonObject.getString("nom");
            BigDecimal prix = new BigDecimal(jsonObject.getString("prix"));

            return new Plat(platId, nom, prix);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Requête interrompue vers l'API Plats et Utilisateurs", e);
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors de l'appel à l'API Plats et Utilisateurs", e);
        }
    }
}

