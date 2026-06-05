package ModuloProyectosInnovatech.Proyecto.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AsignacionTareaDTO {

    private Integer id;
    private Integer tareaId;
    private String usuarioId;
    private LocalDate fechaAsignacion;
    private Boolean estado;
    
}
