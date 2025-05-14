package br.com.fiap.TO;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EstiloTO {

	private int id;
	private String estilo;
	private int exibir;
	private String links;

	
	public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLinks() { return links; }
    public void setLinks(String links) { this.links = links; }

    public String getEstilo() { return estilo; }
    public void setEstilo(String estilo) { this.estilo = estilo; }

    public int getExibir() { return exibir; }
    public void setExibir(int exibir) { this.exibir = exibir; }
}
