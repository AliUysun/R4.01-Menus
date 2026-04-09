package com.yourcompany.menus.rest;

import com.yourcompany.menus.domain.entity.Menu;
import com.yourcompany.menus.domain.entity.Plat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO pour la réponse HTTP d'un menu
 * 
 * Contient la sérialisation JSON d'un menu avec ses métadonnées et ses plats.
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
    private List<PlatResponse> plats;
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
        response.setPlats(menu.getPlats().stream().map(PlatResponse::fromDomain).toList());
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

    public List<PlatResponse> getPlats() {
        return plats;
    }

    public void setPlats(List<PlatResponse> plats) {
        this.plats = plats;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    /**
     * DTO pour un plat en réponse
     */
    public static class PlatResponse {
        private Integer id;
        private String nom;
        private BigDecimal prix;

        public static PlatResponse fromDomain(Plat plat) {
            PlatResponse response = new PlatResponse();
            response.setId(plat.getId());
            response.setNom(plat.getNom());
            response.setPrix(plat.getPrix());
            return response;
        }

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

        public BigDecimal getPrix() {
            return prix;
        }

        public void setPrix(BigDecimal prix) {
            this.prix = prix;
        }
    }
}

