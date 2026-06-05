package ModuloProyectosInnovatech.Proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ModuloProyectosInnovatech.Proyecto.dto.UsuarioExternoDTO;

@Service
public class UsuarioClientService {

    private final WebClient webClient;

    // Header de seguridad compartido con el Gateway
    @Value("${GATEWAY_SECRET:local_test_back}")
    private String secretoCompartido;

    // La baseUrl se inyecta directamente como parámetro del constructor para que
    // Spring resuelva el @Value ANTES de construir el WebClient.
    // Si se declara como campo y se usa en el constructor, el campo aún no está
    // inicializado en ese momento → se ignora la variable de entorno.
    @Autowired
    public UsuarioClientService(
            WebClient.Builder webClientBuilder,
            @Value("${USUARIOS_SERVICE_URL:http://localhost:8081/api/v1/usuarios}") String baseUrl) {

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Consulta el microservicio de Recursos para obtener los datos del usuario
     * autenticado mediante su UID de Firebase.
     *
     * El microservicio no acepta un ID en el path; en su lugar expone el endpoint
     * GET /me y recibe el UID a través del header X-User-UID.
     *
     * @param usuarioId UID de Firebase del usuario a consultar
     * @return UsuarioExternoDTO con los datos del usuario, o null si el servicio
     *         no está disponible o el usuario no existe
     */
    public UsuarioExternoDTO obtenerUsuarioPorId(String usuarioId) {
        try {
            return webClient.get()
                    .uri("/me")
                    .header("X-Gateway-Secret", secretoCompartido)
                    .header("X-User-UID", usuarioId)
                    .retrieve()
                    .bodyToMono(UsuarioExternoDTO.class)
                    .doOnError(error -> System.err.println("ERROR WEBCLIENT RECURSOS: " + error.getMessage()))
                    // Si el microservicio devuelve 4xx o 5xx, retornamos un DTO vacío
                    .onErrorReturn(new UsuarioExternoDTO())
                    .block(); // Bloqueante: compatible con el stack MVC síncrono
        } catch (Exception e) {
            // Fallback si el microservicio está completamente caído (connection refused, timeout, etc.)
            return null;
        }
    }

}
