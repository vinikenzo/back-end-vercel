package br.com.fiap.resource;

import br.com.fiap.DAO.EstiloDAO;
import br.com.fiap.TO.EstiloTO;
import br.com.fiap.BO.EstiloBO;

import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;



@Path("/estilo")

public class EstiloResource {
    private EstiloDAO estiloDAO;
    private EstiloBO estiloBO;

    public EstiloResource() {
        this.estiloDAO = new EstiloDAO();
        this.estiloBO = new EstiloBO();
    }
    // montar estilo para editar
    @GET
    @Path("/Editar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstiloTO> listarTODOS() {
        return estiloDAO.selectTODOS();
    }
    
    // montar estilo 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstiloTO> listarCategorias() {
        return estiloDAO.select();
    }
    
    // popular os campos do fomulário para modificar o estilo
    @GET
    @Path("/{links}")
    @Produces(MediaType.APPLICATION_JSON)
    public EstiloTO buscarPorLinks(@PathParam("links") String links) {
        return estiloBO.listar(links);
    } 

    // inserir novo estilo
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(EstiloTO estilo, @Context UriInfo uriInfo) {
		estiloBO.cadastrar(estilo);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Integer.toString(estilo.getId()));
		return Response.created(builder.build()).build();
	}
    
    // atualizar estilo
    @PUT
    @Path("/{links}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(EstiloTO estilo	) {
        estiloBO.atualizar(estilo);
        return Response.ok().build();
    }
    
    // deletar item do estilo
    @PUT
    @Path("/excluir/{id}")
    public Response excluir(@PathParam("id") int id) {
        estiloBO.remover(id);
        return Response.noContent().build(); // Retorna 204 (sem conteúdo)
    }
    
}
