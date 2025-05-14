package br.com.fiap.TO;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlbumTO {

    private int id;
    private String album;
    private int banda;
    private int categoria;
    private String imagem;
    private int lancamento;
    private String faixas;
    private int exibir;
    private String integrantes;
    private String nomeBanda;
    private String descricao;
    private String links;
    private String slug;
    private String nomeEstilo;
    private String slugBanda;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    public int getBanda() { return banda; }
    public void setBanda(int banda) { this.banda = banda; }

    public int getCategoria() { return categoria; }
    public void setCategoria(int categoria) { this.categoria = categoria; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public int getLancamento() { return lancamento; }
    public void setLancamento(int lancamento) { this.lancamento = lancamento; }

    public String getFaixas() { return faixas; }
    public void setFaixas(String faixas) { this.faixas = faixas; }

    public int getExibir() { return exibir; }
    public void setExibir(int exibir) { this.exibir = exibir; }

    public String getIntegrantes() { return integrantes; }
    public void setIntegrantes(String integrantes) { this.integrantes = integrantes; }

    public String getNomeBanda() { return nomeBanda; }
    public void setNomeBanda(String nomeBanda) { this.nomeBanda = nomeBanda; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLinks() { return links; }
    public void setLinks(String links) { this.links = links; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    
    public String getNomeEstilo() { return nomeEstilo; }
    public void setNomeEstilo(String nomeEstilo) { this.nomeEstilo = nomeEstilo; }
    
    public String getSlugBanda() { return slugBanda; }
    public void setSlugBanda(String slugBanda) { this.slugBanda = slugBanda; }
}
