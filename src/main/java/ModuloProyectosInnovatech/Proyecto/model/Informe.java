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
@Table(name="informes")
public class Informe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="tarea_id", nullable = false)
    private Tarea tarea;

    @Column(name = "url_archivo")
    private String urlArchivo;

    @Column(name = "fecha_subida")
    private LocalDate fechaSubida;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean estado = true;
}
