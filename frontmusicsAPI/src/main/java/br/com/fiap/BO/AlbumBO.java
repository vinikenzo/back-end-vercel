package br.com.fiap.BO;

import br.com.fiap.DAO.AlbumDAO;
import br.com.fiap.TO.AlbumTO;


public class AlbumBO {
    public boolean remover(int id) {
        AlbumDAO dao = new AlbumDAO();
        return dao.excluir(id);
    }
    public boolean atualizar(AlbumTO album) {
        AlbumDAO dao = new AlbumDAO();
        return dao.atualizar(album);
    }
}

