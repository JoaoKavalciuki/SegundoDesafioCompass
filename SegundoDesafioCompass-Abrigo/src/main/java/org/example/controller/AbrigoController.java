package org.example.controller;

import org.example.entity.Abrigo;
import org.example.service.AbrigoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/abrigos")
public class AbrigoController {

    private AbrigoService service = new AbrigoService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAbrigo(Abrigo abrigo) {
        try {
            service.createAbrigo(abrigo);
            return Response.status(Response.Status.CREATED).entity(abrigo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAbrigo(@PathParam("id") Long id) {
        try {
            Abrigo abrigo = service.getAbrigo(id);
            return Response.ok(abrigo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Abrigo> getAllAbrigos() {
        return service.getAllAbrigos();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAbrigo(@PathParam("id") Long id, Abrigo abrigo) {
        try {
            abrigo.setId(id);
            service.updateAbrigo(id);
            return Response.ok(abrigo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAbrigo(@PathParam("id") Long id) {
        try {
            service.deleteAbrigo(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
