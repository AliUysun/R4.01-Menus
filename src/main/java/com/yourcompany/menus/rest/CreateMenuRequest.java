package com.yourcompany.menus.rest;

/**
 * Requête pour créer ou mettre à jour un menu
 * 
 * DTO reçu du client REST pour les opérations POST et PUT sur les menus.
 * Contient les données nécessaires pour créer/modifier un menu.
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class CreateMenuRequest {
    private String nom;
    private Integer createurId;

    /**
     * Obtient le nom du menu
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du menu
     * @param nom le nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'id du créateur
     * @return l'id du créateur
     */
    public Integer getCreateurId() {
        return createurId;
    }

    /**
     * Définit l'id du créateur
     * @param createurId l'id du créateur
     */
    public void setCreateurId(Integer createurId) {
        this.createurId = createurId;
    }
}

