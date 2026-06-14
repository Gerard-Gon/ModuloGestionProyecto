package ModuloProyectosInnovatech.Proyecto.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegistroHorasDTO {

    private Integer id;
    private String usuarioId;
    private Integer tareaId;
    private Float horasTrabajadas;
    private LocalDate fechaRegistro;
    private Boolean estado;
    
}
