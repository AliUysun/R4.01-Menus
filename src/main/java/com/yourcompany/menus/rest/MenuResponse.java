package com.yourcompany.menus.rest;

import com.yourcompany.menus.domain.entity.Menu;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO pour la réponse HTTP d'un menu
 * 
 * Contient la sérialisation JSON d'un menu avec ses métadonnées.
 * Cette classe sépare la représentation HTTP de l'entité domaine.
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class MenuResponse {
    private Integer id;
    private String nom;
    private Integer createurId;
    private String createurNom;
    private LocalDate dateCreation;
    private LocalDate dateMiseAJour;
    private BigDecimal prixTotal;

    /**
     * Convertit une entité Menu en MenuResponse
     * 
     * @param menu l'entité Menu du domaine
     * @return la réponse HTTP sérialisable en JSON
     */
    public static MenuResponse fromDomain(Menu menu) {
        MenuResponse response = new MenuResponse();
        response.setId(menu.getId());
        response.setNom(menu.getNom());
        response.setCreateurId(menu.getCreateurId());
        response.setCreateurNom(menu.getCreateurNom());
        response.setDateCreation(menu.getDateCreation());
        response.setDateMiseAJour(menu.getDateMiseAJour());
        response.setPrixTotal(menu.getPrixTotal());
        return response;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCreateurId() {
        return createurId;
    }

    public void setCreateurId(Integer createurId) {
        this.createurId = createurId;
    }

    public String getCreateurNom() {
        return createurNom;
    }

    public void setCreateurNom(String createurNom) {
        this.createurNom = createurNom;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateMiseAJour() {
        return dateMiseAJour;
    }

    public void setDateMiseAJour(LocalDate dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

}

