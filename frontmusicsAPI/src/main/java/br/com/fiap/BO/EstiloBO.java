package br.com.fiap.BO;

import java.util.List;

import br.com.fiap.DAO.EstiloDAO;
import br.com.fiap.TO.EstiloTO;

public class EstiloBO {

	private EstiloDAO menuDAO;

	public EstiloBO() {
		this.menuDAO = new EstiloDAO();
	}

	public List<EstiloTO> listar() {
		return menuDAO.select();
	}
	
	public List<EstiloTO> listarTODOS() {
		return menuDAO.select();
	}

	public EstiloTO listar(String links) {
		return menuDAO.select(links);
	}

	public boolean cadastrar(EstiloTO links) {
		return menuDAO.insert(links);
	}

	public void atualizar(EstiloTO links) {
		menuDAO.update(links);
	}
	
	public void remover(int id) {
	    menuDAO.delete(id);
	}
}
