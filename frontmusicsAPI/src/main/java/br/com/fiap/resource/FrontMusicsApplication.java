package br.com.frontmusics;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api") // Define o prefixo de todos os seus endpoints
public class FrontMusicsApplication extends Application {
    // Você pode registrar classes aqui, se necessário
}
