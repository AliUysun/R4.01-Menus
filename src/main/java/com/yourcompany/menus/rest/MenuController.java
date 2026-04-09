package com.yourcompany.menus.rest;

import com.yourcompany.menus.application.MenuApplicationService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Controller REST pour les menus* 
 * Point d'entrée pour toutes les requêtes HTTP concernant les menus.
 * Gère le routage, la sérialisation/désérialisation JSON et la conversion d'exceptions.
 * 
 * Base d'URL : /menus/api/menus
 * 
 * @author Projet Menus
 * @version 1.0
 */
@Path("/menus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class MenuController {

    @Inject
    private MenuApplicationService menuApplicationService;

    /**
     * GET /menus - Lister tous les menus
     * 
     * @return la liste de tous les menus
     */
    @GET
    public List<MenuResponse> listMenus() {
        return menuApplicationService.listMenus().stream()
                .map(MenuResponse::fromDomain)
                .toList();
    }

    /**
     * POST /menus - Créer un nouveau menu
     * 
     * @param request la requête contenant le nom et l'id du créateur
     * @return la réponse avec le menu créé et le header Location
     */
    @POST
    public Response createMenu(CreateMenuRequest request) {
        try {
            MenuResponse created = MenuResponse.fromDomain(
                    menuApplicationService.createMenu(request.getNom(), request.getCreateurId())
            );
            return Response.status(Response.Status.CREATED)
                    .header("Location", "/menus/" + created.getId())
                    .entity(created)
                    .build();
        } catch (IllegalArgumentException e) {
            throw toHttpException(e);
        }
    }

    /**
     * GET /menus/{id} - Obtenir un menu par son identifiant
     * 
     * @param id l'identifiant du menu
     * @return le menu demandé
     * @throws NotFoundException si le menu n'existe pas
     */
    @GET
    @Path("/{id}")
    public MenuResponse getMenuById(@PathParam("id") Integer id) {
        try {
            return MenuResponse.fromDomain(menuApplicationService.getMenuById(id));
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * PUT /menus/{id} - Mettre à jour un menu
     * 
     * @param id l'identifiant du menu
     * @param request la requête contenant les nouvelles données
     * @return le menu mis à jour
     * @throws NotFoundException si le menu n'existe pas
     */
    @PUT
    @Path("/{id}")
    public MenuResponse updateMenu(@PathParam("id") Integer id, CreateMenuRequest request) {
        try {
            return MenuResponse.fromDomain(
                    menuApplicationService.updateMenu(id, request.getNom(), request.getCreateurId())
            );
        } catch (IllegalArgumentException e) {
            throw toHttpException(e);
        }
    }

    /**
     * DELETE /menus/{id} - Supprimer un menu
     * 
     * @param id l'identifiant du menu
     * @return réponse 204 No Content
     * @throws NotFoundException si le menu n'existe pas
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMenu(@PathParam("id") Integer id) {
        try {
            menuApplicationService.deleteMenu(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(e.getMessage());
        }
    }


    /**
     * Convertit une exception métier en exception HTTP appropriée
     * 
     * @param e l'exception métier
     * @return une exception HTTP appropriée (404 ou 400)
     */
    private WebApplicationException toHttpException(IllegalArgumentException e) {
        String message = e.getMessage() == null ? "Requete invalide" : e.getMessage();
        if (message.contains("introuvable")) {
            return new NotFoundException(message);
        }
        return new WebApplicationException(message, Response.Status.BAD_REQUEST);
    }
}

