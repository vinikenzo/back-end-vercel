package br.com.fiap.TO;

public class BandaTO {
    private int id;
    private String banda;
    private String integrantes;
    private String descricao;
    private String links;
    private String slug;
    private String imagem;
    private String categoria;
    private int exibir;
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBanda() {
		return banda;
	}
	public void setBanda(String banda) {
		this.banda = banda;
	}
	public String getIntegrantes() {
		return integrantes;
	}
	public void setIntegrantes(String integrantes) {
		this.integrantes = integrantes;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public int getExibir() {
		return exibir;
	}
	public void setExibir(int exibir) {
		this.exibir = exibir;
	}
}