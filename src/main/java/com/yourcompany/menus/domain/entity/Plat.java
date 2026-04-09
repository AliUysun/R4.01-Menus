package com.yourcompany.menus.domain.entity;

import java.math.BigDecimal;

/**
 * Entité Plat du domaine métier
 * 
 * Représente un plat disponible avec son prix.
 * Cette classe est une entité simple du domaine, indépendante de toute infrastructure.
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class Plat {
    private Integer id;
    private String nom;
    private BigDecimal prix;

    /**
     * Constructeur par défaut
     */
    public Plat() {
    }

    /**
     * Constructeur avec tous les paramètres
     * 
     * @param id identifiant unique du plat
     * @param nom nom du plat
     * @param prix prix du plat
     */
    public Plat(Integer id, String nom, BigDecimal prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    /**
     * Obtient l'identifiant du plat
     * @return l'id du plat
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'identifiant du plat
     * @param id l'id du plat
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtient le nom du plat
     * @return le nom du plat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du plat
     * @param nom le nom du plat
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient le prix du plat
     * @return le prix du plat
     */
    public BigDecimal getPrix() {
        return prix;
    }

    /**
     * Définit le prix du plat
     * @param prix le prix du plat
     */
    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}

