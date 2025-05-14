package br.com.fiap.config;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

import br.com.fiap.filter.CorsFilter;

@ApplicationPath("/")
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages("br.com.fiap.resource", "br.com.fiap.filter");
    }
}

