package ModuloProyectosInnovatech.Proyecto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

// ignoreUnknown = true: Jackson ignorará los campos del JSON que no estén
// mapeados aquí, evitando errores por campos extra del microservicio de Recursos
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioExternoDTO {

    private Integer id;
    private String nombre;
    private String email;

    // Objeto anidado que representa el cargo del usuario en el microservicio de Recursos
    private CargoDTO cargo;

    // Objeto anidado que representa la categoría del usuario en el microservicio de Recursos
    private CategoriaDTO categoria;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CargoDTO {
        private String nombreCargo;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoriaDTO {
        private String categoria;
    }

}
