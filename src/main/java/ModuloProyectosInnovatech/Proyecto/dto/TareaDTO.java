package ModuloProyectosInnovatech.Proyecto.dto;

import lombok.Data;

@Data
public class TareaDTO {

    private Integer id;
    private String nombreTareas;
    private String descripcionTareas;
    private Boolean estado; 
    private String progreso;
    
    private Integer proyectoId;
    
}
