package ModuloProyectosInnovatech.Proyecto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="registro_horas")
public class RegistroHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    private String usuarioId;

    @ManyToOne
    @JoinColumn(name="tarea_id", nullable = false)
    private Tarea tarea;

    @Column(name = "horas_trabajadas", nullable = false)
    private Float horasTrabajadas;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean estado = true;
    
}
