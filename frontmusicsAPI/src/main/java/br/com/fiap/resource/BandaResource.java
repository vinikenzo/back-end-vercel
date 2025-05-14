package br.com.fiap.resource;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fiap.DAO.BandaDAO;
import br.com.fiap.TO.BandaTO;

@Path("/bandas")
public class BandaResource {
	
	private BandaDAO BandaDAO;

    public BandaResource() {
        this.BandaDAO = new BandaDAO();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BandaTO> listar() {
        return BandaDAO.listar();
    }

    @GET
    @Path("/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BandaTO> buscarPorLink(@PathParam("slug") String slug) {
        return new BandaDAO().buscarPorLink(slug);
    }
    
    @GET
    @Path("editar/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BandaTO> buscarPorSlug(@PathParam("slug") String slug) {
        return new BandaDAO().buscarPorSlug(slug);
    }
    
    @GET
    @Path("/editar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BandaTO> listarTODOS() {
        return BandaDAO.selectTODOS();
    }
    
    @PUT
    @Path("atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarBanda(@PathParam("id") int id, BandaTO banda) {
        BandaDAO bandaDAO = new BandaDAO();

        // Atualizar a banda com base no id
        boolean sucesso = bandaDAO.atualizarBanda(id, banda);

        if (sucesso) {
            return Response.ok().entity("Banda atualizada com sucesso").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar banda").build();
        }
    }

    @PUT
    @Path("/excluir/{id}")
    public Response excluirBanda(@PathParam("id") int id) {
    	BandaDAO.excluirBanda(id);
        return Response.noContent().build(); // Retorna 204 (sem conteúdo)
    }
    
    
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarBanda(BandaTO banda) {
        boolean sucesso = BandaDAO.cadastrarBanda(banda);
        
        if (sucesso) {
            return Response.status(Response.Status.CREATED)
                           .entity("Banda cadastrada com sucesso")
                           .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Erro ao cadastrar banda")
                           .build();
        }
    }


    
}
