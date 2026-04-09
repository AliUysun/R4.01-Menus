package com.yourcompany.menus.infrastructure.repository.menu;

import com.yourcompany.menus.application.port.IMenuRepository;
import com.yourcompany.menus.domain.entity.Menu;
import com.yourcompany.menus.infrastructure.repository.connection.DbConnectionManager;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'accès aux menus en base de données
 * 
 * RESPONSABILITÉ UNIQUE : exécution des requêtes SQL SEULEMENT
 * - Pas de logique métier
 * - Pas de transformations de données
 * - Juste : SQL + mapping via MenuRowMapper
 * 
 * Implémente le port IMenuRepository pour les opérations CRUD sur les menus.
 * 
 * @author Projet Menus
 * @version 1.0
 */
@ApplicationScoped
public class MenuRepository implements IMenuRepository {

    private final DbConnectionManager dbConnectionManager = new DbConnectionManager();

    /**
     * Obtient le prochain ID disponible pour créer un menu
     * 
     * @return le prochain ID
     * @throws IllegalStateException si la requête échoue
     */
    @Override
    public Integer nextId() {
        try (Connection conn = dbConnectionManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(MenuSqlQueries.SELECT_NEXT_ID);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("next_id") : 1;
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible de calculer le prochain id de menu", e);
        }
    }

    /**
     * Sauvegarde ou met à jour un menu en base de données
     * 
     * Gère une transaction d'upsert sur la table menus.
     * 
     * @param menu le menu à sauvegarder
     * @return le menu sauvegardé
     * @throws IllegalStateException si une erreur SQL survient
     */
    @Override
    public Menu save(Menu menu) {
        try (Connection conn = dbConnectionManager.openConnection()) {
            conn.setAutoCommit(false);
            try {
                insertOrUpdateMenu(conn, menu);
                conn.commit();
                return menu;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible de sauvegarder le menu", e);
        }
    }

    /**
     * Récupère tous les menus de la base de données
     * 
     * @return la liste de tous les menus
     * @throws IllegalStateException si une erreur SQL survient
     */
    @Override
    public List<Menu> findAll() {
        try (Connection conn = dbConnectionManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(MenuSqlQueries.SELECT_MENUS);
             ResultSet rs = stmt.executeQuery()) {
            List<Menu> menus = new java.util.ArrayList<>();
            while (rs.next()) {
                menus.add(MenuRowMapper.mapMenu(rs));
            }
            return menus;
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible de recuperer les menus", e);
        }
    }

    /**
     * Récupère un menu par son ID
     * 
     * @param id l'identifiant du menu
     * @return le menu s'il existe, Optional.empty() sinon
     * @throws IllegalStateException si une erreur SQL survient
     */
    @Override
    public Optional<Menu> findById(Integer id) {
        try (Connection conn = dbConnectionManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(MenuSqlQueries.SELECT_MENU_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(MenuRowMapper.mapMenu(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible de recuperer le menu " + id, e);
        }
    }

    /**
     * Supprime un menu de la base de données
     * 
     * @param id l'identifiant du menu à supprimer
     * @throws IllegalStateException si une erreur SQL survient
     */
    @Override
    public void deleteById(Integer id) {
        try (Connection conn = dbConnectionManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(MenuSqlQueries.DELETE_MENU)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Impossible de supprimer le menu " + id, e);
        }
    }


    private void insertOrUpdateMenu(Connection conn, Menu menu) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(MenuSqlQueries.UPSERT_MENU)) {
            stmt.setInt(1, menu.getId());
            stmt.setString(2, menu.getNom());
            stmt.setInt(3, menu.getCreateurId());
            stmt.setString(4, menu.getCreateurNom());
            stmt.setDate(5, Date.valueOf(menu.getDateCreation()));
            stmt.setDate(6, Date.valueOf(menu.getDateMiseAJour()));
            stmt.setBigDecimal(7, menu.getPrixTotal());
            stmt.executeUpdate();
        }
    }

}

