package ModuloProyectosInnovatech.Proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombreProyecto", nullable = false, unique = true)
    private String nombreProyecto;
    
    @Column(name = "descripcion", nullable = true)
    private String descripcionProyecto;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "jefe_id")
    private String jefeId;

}
