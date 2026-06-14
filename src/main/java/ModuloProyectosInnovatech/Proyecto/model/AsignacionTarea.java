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
@Table(name="asignaciones_tareas")
public class AsignacionTarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="tarea_id", nullable = false)
    private Tarea tarea;

    @Column(name = "usuario_id", nullable = false)
    private String usuarioId;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean estado = true;
}