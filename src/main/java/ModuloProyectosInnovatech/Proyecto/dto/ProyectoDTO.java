package ModuloProyectosInnovatech.Proyecto.dto;


import lombok.Data;

@Data
public class ProyectoDTO {

    private Integer id;
    private String nombreProyecto;
    private String descripcionProyecto;
    private Boolean activo;
    private String jefeId;
    
}
