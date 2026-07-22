package ec.edu.utn.golmundial.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

// Configura la ruta base y metadatos de OpenAPI para Swagger
@OpenAPIDefinition(
    info = @Info(
        title = "Servicio UTNGolCoin API",
        version = "1.0.0",
        description = "API REST para la gestion de la moneda virtual UTNGolCoin y predicciones del Mundial"
    ),
    servers = {
        @Server(url = "/UTNGolCoin", description = "Servidor WildFly Local")
    }
)
@ApplicationPath("/api")
public class RestApplication extends Application {
}
