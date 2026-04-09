package com.yourcompany.menus.infrastructure.repository.menu;

import com.yourcompany.menus.domain.entity.Menu;
import com.yourcompany.menus.domain.entity.Plat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mappeur pour convertir les ResultSet SQL en objets Menu/Plat
 * 
 * RESPONSABILITÉ UNIQUE : conversion données SQL → objets domaine
 * 
 * Isolé en classe utilitaire pour éviter de mélanger la logique SQL
 * avec la logique de mapping.
 * 
 * @author Projet Menus
 * @version 1.0
 */
public class MenuRowMapper {

    /**
     * Mappe une ligne ResultSet vers un Menu (sans plats)
     * 
     * @param rs le ResultSet positionné sur une ligne de menu
     * @return l'objet Menu mappé
     * @throws SQLException si un accès au ResultSet échoue
     */
    public static Menu mapMenu(ResultSet rs) throws SQLException {
        Menu menu = new Menu();
        menu.setId(rs.getInt("id"));
        menu.setNom(rs.getString("nom"));
        menu.setCreateurId(rs.getInt("createur_id"));
        menu.setCreateurNom(rs.getString("createur_nom"));
        menu.setDateCreation(rs.getDate("date_creation") != null ? rs.getDate("date_creation").toLocalDate() : null);
        menu.setDateMiseAJour(rs.getDate("date_mise_a_jour") != null ? rs.getDate("date_mise_a_jour").toLocalDate() : null);
        menu.setPrixTotal(rs.getBigDecimal("prix_total"));
        return menu;
    }

    /**
     * Charge les plats d'un menu depuis un ResultSet
     * 
     * Itère sur les lignes du ResultSet et crée une liste d'objets Plat
     * 
     * @param rs le ResultSet positionné sur les lignes de plats
     * @return la liste des plats mappés
     * @throws SQLException si un accès au ResultSet échoue
     */
    public static List<Plat> mapPlats(ResultSet rs) throws SQLException {
        List<Plat> plats = new ArrayList<>();
        while (rs.next()) {
            plats.add(new Plat(
                    rs.getInt("plat_id"),
                    rs.getString("plat_nom"),
                    rs.getBigDecimal("plat_prix")
            ));
        }
        return plats;
    }
}

