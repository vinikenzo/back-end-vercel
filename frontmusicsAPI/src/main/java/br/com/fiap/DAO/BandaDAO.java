	package br.com.fiap.DAO;

import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.TO.BandaTO;
import br.com.fiap.factory.ConnectionFactory;

public class BandaDAO {

    public List<BandaTO> buscarPorLink(String link) {
        List<BandaTO> bandas = new ArrayList<>();

        String sql = "SELECT banda.id, banda.banda, banda.descricao, " + 
        			"banda.integrantes, banda.links, banda.slug, banda.imagem, estilo.estilo AS categoria " +
                     "FROM banda  INNER JOIN estilo ON banda.categoria = estilo.id " +
                     "WHERE banda.slug = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, link);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BandaTO banda = new BandaTO();
                    banda.setId(rs.getInt("id"));
                    banda.setBanda(rs.getString("banda"));
                    banda.setDescricao(rs.getString("descricao"));
                    banda.setIntegrantes(rs.getString("integrantes"));
                    banda.setLinks(rs.getString("links"));
                    banda.setSlug(rs.getString("slug"));
                    banda.setImagem(rs.getString("imagem"));
                    banda.setCategoria(rs.getString("categoria"));
                    bandas.add(banda);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bandas;
    }
    
    public List<BandaTO> buscarPorSlug(String slug) {
        List<BandaTO> bandas = new ArrayList<>();

        String sql = "SELECT banda.id, banda.banda, banda.descricao, " + 
        			 "banda.integrantes, banda.links, banda.slug, banda.imagem, banda.exibir, " +
        			 "banda.categoria, estilo.estilo AS categoria " +
        			 "FROM banda " +
        			 "INNER JOIN estilo ON banda.categoria = estilo.id " +
        			 "WHERE banda.slug = ?";
        

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slug);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BandaTO banda = new BandaTO();
                    banda.setId(rs.getInt("id"));
                    banda.setBanda(rs.getString("banda"));
                    banda.setDescricao(rs.getString("descricao"));
                    banda.setIntegrantes(rs.getString("integrantes"));
                    banda.setLinks(rs.getString("links"));
                    banda.setSlug(rs.getString("slug"));
                    banda.setImagem(rs.getString("imagem"));
                    banda.setCategoria(String.valueOf(rs.getInt("categoria")));
                    banda.setExibir(rs.getInt("exibir"));
                    bandas.add(banda);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bandas;
    }
    
    public List<BandaTO> selectTODOS() {
        List<BandaTO> bandas = new ArrayList<>();
        String sql = "SELECT * FROM banda ORDER BY banda ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	BandaTO banda = new BandaTO();
            	banda.setId(rs.getInt("id"));
                banda.setBanda(rs.getString("banda"));
                banda.setLinks(rs.getString("links"));
                banda.setExibir(rs.getInt("exibir"));
                banda.setSlug(rs.getString("slug"));
                bandas.add(banda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bandas;
    }
    
    public List<BandaTO> listar() {
        List<BandaTO> bandas = new ArrayList<>();
        String sql = "SELECT * FROM banda WHERE exibir = 1 ORDER BY banda ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BandaTO banda = new BandaTO();
                banda.setId(rs.getInt("id"));
                banda.setBanda(rs.getString("banda"));
                banda.setLinks(rs.getString("links"));
                banda.setSlug(rs.getString("slug"));
                bandas.add(banda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bandas;
    }
    
    public boolean atualizarBanda(int id, BandaTO banda) {
        String link = buscarLinkPorCategoria(banda.getCategoria());
        String sql = "UPDATE banda SET banda = ?, descricao = ?, integrantes = ?, links = ?, " +
                     "slug = ?, imagem = ?, exibir = ?, categoria = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String baseSlug = banda.getBanda();
            String slug = Normalizer.normalize(baseSlug, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "") // Remove acentos
                    .toLowerCase()
                    .replaceAll("[\\s,]+", "-")                          // Substitui espaços e vírgulas por hífens
                    .replaceAll("[^a-z0-9-]", "")                        // Remove caracteres especiais
                    .replaceAll("[-]{2,}", "-")                          // Substitui múltiplos hífens por um só
                    .replaceAll("^-|-$", "");

            // Definir os parâmetros para a consulta SQL
            ps.setString(1, banda.getBanda());
            ps.setString(2, banda.getDescricao());
            ps.setString(3, banda.getIntegrantes());
            ps.setString(4, link);
            ps.setString(5, slug);
            ps.setString(6, banda.getImagem());
            ps.setInt(7, banda.getExibir());
            ps.setInt(8, Integer.parseInt(banda.getCategoria())); // Definir o ID da categoria
            ps.setInt(9, banda.getId()); // Usar o slug para identificar a banda

            // Executar a atualização no banco
            int rowsUpdated = ps.executeUpdate();

            // Verificar se a atualização foi bem-sucedida
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de erro
        }
    }
    
    public boolean cadastrarBanda(BandaTO banda) {
    	String link = buscarLinkPorCategoria(banda.getCategoria());
        String sql = "INSERT INTO banda (banda, descricao, integrantes, links, slug, imagem, exibir, categoria) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String baseSlug = banda.getBanda();
            String slug = Normalizer.normalize(baseSlug, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "") // Remove acentos
                    .toLowerCase()
                    .replaceAll("[\\s,]+", "-")                          // Substitui espaços e vírgulas por hífens
                    .replaceAll("[^a-z0-9-]", "")                        // Remove caracteres especiais
                    .replaceAll("[-]{2,}", "-")                          // Substitui múltiplos hífens por um só
                    .replaceAll("^-|-$", "");
            ps.setString(1, banda.getBanda());
            ps.setString(2, banda.getDescricao());
            ps.setString(3, banda.getIntegrantes());
            ps.setString(4, link);
            ps.setString(5, slug);
            ps.setString(6, banda.getImagem());
            ps.setInt(7, 1);
            ps.setInt(8, Integer.parseInt(banda.getCategoria())); // categoria como ID

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static String buscarLinkPorCategoria(String categoriaId) {
	    String sql = "SELECT links FROM estilo WHERE id = ?";
	    
	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	         
	        stmt.setString(1, categoriaId);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("links");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    return ""; // ou null, dependendo do que preferir
	}

    
    public void excluirBanda(int id) {
        String sql = "UPDATE banda SET exibir = 0 WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);  // Corrigido: usa o id como condição WHERE, e não como valor de exibir
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}