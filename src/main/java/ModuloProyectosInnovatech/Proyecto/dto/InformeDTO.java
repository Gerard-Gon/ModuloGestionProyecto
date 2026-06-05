package ModuloProyectosInnovatech.Proyecto.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InformeDTO {

    private Integer id;
    private Integer tareaId;
    private String urlArchivo;
    private LocalDate fechaSubida;
    private Boolean estado;
    
}
