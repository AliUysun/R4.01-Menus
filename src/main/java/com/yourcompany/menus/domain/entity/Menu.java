package com.yourcompany.menus.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité Menu du domaine métier
 * 
 * Représente un menu composé de plusieurs plats avec ses métadonnées.
 * Cette classe est l'entité principale du domaine, indépendante de toute infrastructure.
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class Menu {
    private Integer id;
    private String nom;
    private Integer createurId;
    private String createurNom;
    private LocalDate dateCreation;
    private LocalDate dateMiseAJour;
    private List<Plat> plats = new ArrayList<>();
    private BigDecimal prixTotal = BigDecimal.ZERO;

    /**
     * Constructeur par défaut
     */
    public Menu() {
    }

    /**
     * Constructeur avec tous les paramètres
     * 
     * @param id identifiant unique du menu
     * @param nom nom du menu
     * @param createurId identifiant du créateur
     * @param createurNom nom du créateur
     * @param dateCreation date de création
     * @param dateMiseAJour date de dernière mise à jour
     * @param plats liste des plats du menu
     */
    public Menu(Integer id, String nom, Integer createurId, String createurNom, LocalDate dateCreation, LocalDate dateMiseAJour, List<Plat> plats) {
        this.id = id;
        this.nom = nom;
        this.createurId = createurId;
        this.createurNom = createurNom;
        this.dateCreation = dateCreation;
        this.dateMiseAJour = dateMiseAJour;
        setPlats(plats);
    }

    /**
     * Obtient l'identifiant du menu
     * @return l'id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'identifiant du menu
     * @param id l'identifiant à définir
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtient le nom du menu
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du menu
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'identifiant du créateur
     * @return l'id du créateur
     */
    public Integer getCreateurId() {
        return createurId;
    }

    /**
     * Définit l'identifiant du créateur
     * @param createurId l'id du créateur
     */
    public void setCreateurId(Integer createurId) {
        this.createurId = createurId;
    }

    /**
     * Obtient le nom du créateur
     * @return le nom du créateur
     */
    public String getCreateurNom() {
        return createurNom;
    }

    /**
     * Définit le nom du créateur
     * @param createurNom le nom du créateur
     */
    public void setCreateurNom(String createurNom) {
        this.createurNom = createurNom;
    }

    /**
     * Obtient la date de création
     * @return la date de création
     */
    public LocalDate getDateCreation() {
        return dateCreation;
    }

    /**
     * Définit la date de création
     * @param dateCreation la date de création
     */
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * Obtient la date de dernière mise à jour
     * @return la date de mise à jour
     */
    public LocalDate getDateMiseAJour() {
        return dateMiseAJour;
    }

    /**
     * Définit la date de dernière mise à jour
     * @param dateMiseAJour la date de mise à jour
     */
    public void setDateMiseAJour(LocalDate dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }

    /**
     * Obtient la liste des plats du menu
     * @return une copie de la liste des plats
     */
    public List<Plat> getPlats() {
        return new ArrayList<>(plats);
    }

    /**
     * Définit la liste des plats du menu
     * @param plats la liste des plats
     */
    public void setPlats(List<Plat> plats) {
        this.plats = plats == null ? new ArrayList<>() : new ArrayList<>(plats);
    }

    /**
     * Obtient le prix total du menu
     * @return le prix total (somme des prix des plats)
     */
    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    /**
     * Définit le prix total du menu
     * @param prixTotal le prix total
     */
    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal == null ? BigDecimal.ZERO : prixTotal;
    }
}

