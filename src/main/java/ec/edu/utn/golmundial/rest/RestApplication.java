package ec.edu.utn.golmundial.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

// Configura la ruta base para todos los servicios REST de la aplicacion
@ApplicationPath("/api")
public class RestApplication extends Application {
}
