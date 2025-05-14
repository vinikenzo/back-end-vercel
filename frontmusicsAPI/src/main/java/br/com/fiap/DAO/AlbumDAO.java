package br.com.fiap.DAO;

import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.TO.AlbumTO;
import br.com.fiap.factory.ConnectionFactory;

public class AlbumDAO {
	AlbumTO to = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
	
    public List<AlbumTO> buscaralbunsaleatorios() {
        List<AlbumTO> albuns = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT album.id AS album_id, album.imagem AS imagem_album, " + 
        			 "album.slug AS slug_album, album.album AS nome_album, " +
        			 "banda.banda AS nome_banda FROM album " +
        			 "INNER JOIN banda ON album.idbanda = banda.id ORDER BY DBMS_RANDOM.VALUE )" +
        			 "WHERE ROWNUM <= 12";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	AlbumTO album = new AlbumTO();
            	album.setId(rs.getInt("album_id"));
            	album.setAlbum(rs.getString("nome_album"));
            	album.setNomeBanda(rs.getString("nome_banda"));
            	album.setImagem(rs.getString("imagem_album"));
            	album.setSlug(rs.getString("slug_album"));
                albuns.add(album);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
  	        ConnectionFactory.close(conn, stmt, rs);
  	    }

        return albuns;
    }
    
    public List<AlbumTO> listarTodosAlbuns() {
        List<AlbumTO> albums = new ArrayList<>();
        String sql = "SELECT album.*, banda.slug AS slug_banda, " +
        			 "banda.banda, banda.integrantes " +
        			 "FROM album INNER JOIN banda ON album.idbanda = banda.id " +
        			 "ORDER BY album.album ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	AlbumTO album = new AlbumTO();
            	album.setId(rs.getInt("id"));
            	album.setAlbum(rs.getString("album"));
            	album.setNomeBanda(rs.getString("banda"));
            	album.setSlug(rs.getString("slug"));
            	album.setSlugBanda(rs.getString("slug_banda"));
            	album.setExibir(rs.getInt("exibir"));
            	albums.add(album);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
 	       ConnectionFactory.close(conn, stmt, rs);
 	    }

        return albums;
    }
	
	public List<AlbumTO> listarAlbuns() {
        List<AlbumTO> albums = new ArrayList<>();
        String sql = "SELECT album.*, banda.slug AS slug_banda, " +
        			 "banda.banda, banda.integrantes " +
        			 "FROM album INNER JOIN banda ON album.idbanda = banda.id " +
        			 "WHERE album.exibir = 1 ORDER BY album.album ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	AlbumTO album = new AlbumTO();
            	album.setId(rs.getInt("id"));
            	album.setAlbum(rs.getString("album"));
            	album.setNomeBanda(rs.getString("banda"));
            	album.setSlug(rs.getString("slug"));
            	album.setSlugBanda(rs.getString("slug_banda"));
            	album.setExibir(rs.getInt("exibir"));
            	albums.add(album);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
 	       ConnectionFactory.close(conn, stmt, rs);
 	    }

        return albums;
    }

	public List<AlbumTO> buscarPorCategoria(String links) {
	    List<AlbumTO> albums = new ArrayList<>();

	    String sql = "SELECT album.*, banda.banda, banda.integrantes, estilo.estilo " +
	                 "FROM album " +
	                 "INNER JOIN banda ON album.idbanda = banda.id " +
	                 "INNER JOIN estilo ON album.links = estilo.links " +
	                 "WHERE album.links = ? order by album.album ASC";

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, links);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                AlbumTO album = new AlbumTO();
	                album.setId(rs.getInt("id"));
	                album.setAlbum(rs.getString("album"));
	                album.setBanda(rs.getInt("idbanda"));
	                album.setCategoria(rs.getInt("categoria"));
	                album.setExibir(rs.getInt("exibir"));
	                album.setImagem(rs.getString("imagem"));
	                album.setLancamento(rs.getInt("lancamento"));
	                album.setFaixas(rs.getString("faixas"));
	                album.setDescricao(rs.getString("descricao"));
	                album.setNomeBanda(rs.getString("banda"));
	                album.setIntegrantes(rs.getString("integrantes"));
	                album.setLinks(rs.getString("links"));
	                album.setNomeEstilo(rs.getString("estilo"));
	                album.setSlug(rs.getString("slug"));
	                albums.add(album);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	       ConnectionFactory.close(conn, stmt, rs);
	    }
	    return albums;
	}

	
	public AlbumTO buscarAlbum(String slug) {
	    AlbumTO album = null;

	    String sql = "SELECT " +
	            	 "album.id, album.album, album.idbanda, album.categoria, album.imagem, " +
	            	 "album.lancamento, album.faixas, album.descricao, album.slug AS slug_album, " +
	            	 "album.links, album.exibir, estilo.estilo AS nome_estilo, " +
	            	 "banda.banda AS nome_banda, banda.slug AS slug_banda " +
	            	 "FROM album INNER JOIN estilo ON album.links = estilo.links " +
	            	 "INNER JOIN banda ON album.idbanda = banda.id " +
	            	 "WHERE album.slug = ?";

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, slug);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                album = new AlbumTO();
	                album.setId(rs.getInt("id"));
	                album.setAlbum(rs.getString("album"));
	                album.setBanda(rs.getInt("idbanda"));
	                album.setCategoria(rs.getInt("categoria"));
	                album.setImagem(rs.getString("imagem"));
	                album.setLancamento(rs.getInt("lancamento"));
	                album.setFaixas(rs.getString("faixas"));
	                album.setDescricao(rs.getString("descricao"));
	                album.setSlug(rs.getString("slug_album"));
	                album.setLinks(rs.getString("links"));
	                album.setExibir(rs.getInt("exibir"));
	                album.setNomeEstilo(rs.getString("nome_estilo"));
	                album.setNomeBanda(rs.getString("nome_banda"));
	                album.setSlugBanda(rs.getString("slug_banda"));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	       ConnectionFactory.close(conn, stmt, rs);
	    }
	    return album;
	}

	public static boolean cadastrarAlbum(AlbumTO album) {
	    //String slug = gerarSlug(album.getAlbum());
	    String linkSlug = buscarLinkPorCategoria(album.getCategoria()); // 👈 Pega o "links" da tabela estilo

	    String sql = "INSERT INTO album (album, imagem, categoria, links, lancamento, exibir, faixas, descricao, idbanda, slug) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = ConnectionFactory.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)) {

			String baseSlug = album.getAlbum();
			String slug = Normalizer.normalize(baseSlug, Normalizer.Form.NFD)
					.replaceAll("\\p{InCombiningDiacriticalMarks}+", "") // Remove acentos
					.toLowerCase()
					.replaceAll("[\\s,]+", "-")                          // Substitui espaços e vírgulas por hífens
					.replaceAll("[^a-z0-9-]", "")                        // Remove caracteres especiais
					.replaceAll("[-]{2,}", "-")                          // Substitui múltiplos hífens por um só
					.replaceAll("^-|-$", "");

	        stmt.setString(1, album.getAlbum());
	        stmt.setString(2, album.getImagem());
	        stmt.setInt(3, album.getCategoria());
	        stmt.setString(4, linkSlug);
	        stmt.setInt(5, album.getLancamento());
	        stmt.setInt(6, album.getExibir());
	        stmt.setString(7, album.getFaixas());
	        stmt.setString(8, album.getDescricao());
	        stmt.setInt(9, album.getBanda());
	        stmt.setString(10, slug);

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean excluir(Integer id) {
	    String sql = "UPDATE album SET exibir = 0 WHERE id = ?";

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, id);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }finally {
		       ConnectionFactory.close(conn, stmt, rs);
		}
	}
	
	private static String buscarLinkPorCategoria(int categoriaId) {
	    String sql = "SELECT links FROM estilo WHERE id = ?";
	    
	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	         
	        stmt.setInt(1, categoriaId);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("links");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    return ""; // ou null, dependendo do que preferir
	}

	public boolean atualizar(AlbumTO album) {
	    String sql = "UPDATE album SET album = ?, imagem = ?, categoria = ?, links = ?, lancamento = ?, exibir = ?, faixas = ?, descricao = ?, idbanda = ?, slug = ? WHERE id = ?";

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

			// Slug gerado a partir do título
			String baseSlug = album.getAlbum();
			String slug = Normalizer.normalize(baseSlug, Normalizer.Form.NFD)
					.replaceAll("\\p{InCombiningDiacriticalMarks}+", "") // Remove acentos
					.toLowerCase()
					.replaceAll("[\\s,]+", "-")                          // Substitui espaços e vírgulas por hífens
					.replaceAll("[^a-z0-9-]", "")                        // Remove caracteres especiais
					.replaceAll("[-]{2,}", "-")                          // Substitui múltiplos hífens por um só
					.replaceAll("^-|-$", "");

	        stmt.setString(1, album.getAlbum());
	        stmt.setString(2, album.getImagem());
	        stmt.setInt(3, album.getCategoria());
	        stmt.setString(4, album.getLinks());
	        stmt.setInt(5, album.getLancamento());
	        stmt.setInt(6, album.getExibir());
	        stmt.setString(7, album.getFaixas());
	        stmt.setString(8, album.getDescricao());
	        stmt.setInt(9, album.getBanda());
	        stmt.setString(10, slug);

	        stmt.setInt(11, album.getId());

	        int rows = stmt.executeUpdate();
	        return rows > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }finally {
		       ConnectionFactory.close(conn, stmt, rs);
		    }
	}

	public AlbumTO buscarAlbumAtualizar(String slug) {
	    AlbumTO to = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = ConnectionFactory.getConnection();
	        String sql = "SELECT album.*, " +
	                     "banda.banda AS nomeBanda, " +
	                     "estilo.id AS estilo_id, " +
	                     "estilo.links AS nomeEstilo " +
	                     "FROM album " +
	                     "LEFT JOIN estilo ON album.categoria = estilo.id " +
	                     "LEFT JOIN banda ON album.idbanda = banda.id " +
	                     "WHERE album.slug = ?";

	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, slug);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            to = new AlbumTO();
	            to.setId(rs.getInt("id"));
	            to.setAlbum(rs.getString("album"));
	            to.setBanda(rs.getInt("idbanda")); // ID da banda
	            to.setCategoria(rs.getInt("categoria"));
	            to.setImagem(rs.getString("imagem"));
	            to.setLancamento(rs.getInt("lancamento"));
	            to.setFaixas(rs.getString("faixas"));
	            to.setDescricao(rs.getString("descricao"));
	            to.setExibir(rs.getInt("exibir"));
	            to.setSlug(rs.getString("slug"));
	            to.setLinks(rs.getString("links"));
	            to.setNomeBanda(rs.getString("nomeBanda")); // alias da query
	            to.setNomeEstilo(rs.getString("nomeEstilo"));   // alias da query
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); // aqui você verá a coluna "problemática"
	    } finally {
	        ConnectionFactory.close(conn, stmt, rs);
	    }

	    return to;
	}


}
