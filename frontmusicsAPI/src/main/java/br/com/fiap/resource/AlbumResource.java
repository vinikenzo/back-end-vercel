package br.com.fiap.resource;

import br.com.fiap.DAO.AlbumDAO;
import br.com.fiap.TO.AlbumTO;
import br.com.fiap.BO.AlbumBO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/album")
public class AlbumResource {

    private AlbumDAO Albumdao = new AlbumDAO();

    @GET
    @Path("/aleatorios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AlbumTO> buscaralbunsaleatorios() {
        return new AlbumDAO().buscaralbunsaleatorios();
    }
    
    @GET
    @Path("/buscarporcategoria/{links}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AlbumTO> buscarPorCategoria(@PathParam("links") String links) {
        return Albumdao.buscarPorCategoria(links);
    }
    
    @GET
    @Path("/buscarTODOSalbuns")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AlbumTO> listarTodosAlbuns() {
        return Albumdao.listarTodosAlbuns();
    }
    @GET
    @Path("/buscaralbuns")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AlbumTO> listarAlbuns() {
        return Albumdao.listarAlbuns();
    }

    @GET
    @Path("/buscaalbum/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    public AlbumTO buscarAlbum(@PathParam("slug") String slug) {
        return Albumdao.buscarAlbum(slug.toLowerCase());
    }
    
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarAlbum(AlbumTO album) {
        boolean sucesso = AlbumDAO.cadastrarAlbum(album);

        if (sucesso) {
            return Response.status(Response.Status.CREATED)
                           .entity("Álbum cadastrado com sucesso")
                           .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Erro ao cadastrar álbum")
                           .build();
        }
    }
    
    @PUT
    @Path("/excluir/{id}")
    public Response excluir(@PathParam("id") int id) {
    	AlbumBO bo = new AlbumBO();
    	bo.remover(id);
        return Response.noContent().build(); // Retorna 204 (sem conteúdo)
    }
    
    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, AlbumTO album) {
        album.setId(id);
        boolean sucesso = new AlbumBO().atualizar(album);

        if (sucesso) {
            return Response.ok().build(); // 200
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }
    
    @GET
    @Path("/buscar/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarAlbumAtualizar(@PathParam("slug") String slug) {
        try {
            AlbumDAO dao = new AlbumDAO();
            AlbumTO disco = dao.buscarAlbumAtualizar(slug.toLowerCase());

            if (disco != null) {
                return Response.ok(disco).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Álbum não encontrado.").build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro ao buscar álbum por slug.").build();
        }
    }


}
