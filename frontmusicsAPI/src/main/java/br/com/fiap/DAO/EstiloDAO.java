package br.com.fiap.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.Normalizer;

import br.com.fiap.TO.EstiloTO;
import br.com.fiap.factory.ConnectionFactory;

public class EstiloDAO {

    public List<EstiloTO> select() {
        List<EstiloTO> estilos = new ArrayList<>();
        String sql = "SELECT * FROM estilo WHERE exibir = 1 ORDER BY estilo ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EstiloTO estilo = new EstiloTO();
                estilo.setId(rs.getInt("id"));
                estilo.setEstilo(rs.getString("estilo"));
                estilo.setLinks(rs.getString("links"));
                estilo.setExibir(rs.getInt("exibir"));
                estilos.add(estilo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estilos;
    }
    
    public List<EstiloTO> selectTODOS() {
        List<EstiloTO> estilos = new ArrayList<>();
        String sql = "SELECT * FROM estilo ORDER BY estilo ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EstiloTO estilo = new EstiloTO();
                estilo.setId(rs.getInt("id"));
                estilo.setEstilo(rs.getString("estilo"));
                estilo.setLinks(rs.getString("links"));
                estilo.setExibir(rs.getInt("exibir"));
                estilos.add(estilo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estilos;
    }

    public boolean insert(EstiloTO estilo) {
        String sql = "INSERT INTO estilo (links, estilo, exibir) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "id" })) {

            // Modificando o valor de 'estilo' para criar o valor de 'links'
            String estiloValue = estilo.getEstilo();  // Pegando o valor do campo 'estilo'
            
            // Se 'estilo' contém um espaço, substituímos por hífen ('-')
            //String linksValue = estiloValue.replace(" ", "-");
            // Gera o campo "links" limpo e com hífens
            String linksValue = Normalizer.normalize(estiloValue, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}", "")
                    .toLowerCase()
                    .replace(",", "-")
                    .replace(" ", "-");

            // Definindo os valores dos parâmetros
            stmt.setString(1, linksValue);  // 'links' recebe a versão modificada (sem espaços)
            stmt.setString(2, estiloValue);   // 'estilo' recebe o valor original
            stmt.setInt(3, estilo.getExibir());  // 'exibir' é mantido conforme a entrada

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        estilo.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public EstiloTO select(String links) {
        EstiloTO estilo = null;
        String sql = "SELECT * FROM estilo WHERE links = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, links);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                estilo = new EstiloTO();
                estilo.setId(rs.getInt("id"));
                estilo.setLinks(rs.getString("links"));
                estilo.setEstilo(rs.getString("estilo"));
                estilo.setExibir(rs.getInt("exibir"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estilo;
    }
    
    public void update(EstiloTO estilo) {
        String sql = "UPDATE estilo SET links = ?, estilo = ?, exibir = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String estiloValue = estilo.getEstilo();
            // Gera o campo "links" limpo e com hífens
            String linksValue = Normalizer.normalize(estiloValue, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}", "")
                    .toLowerCase()
                    .replace(",", "-")
                    .replace(" ", "-");

            stmt.setString(1, linksValue);
            stmt.setString(2, estilo.getEstilo());
            stmt.setInt(3, estilo.getExibir());
            stmt.setInt(4, estilo.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        String sql = "UPDATE estilo SET exibir = 0 WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);  // Corrigido: usa o id como condição WHERE, e não como valor de exibir
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar estilo", e);
        }
    }


}