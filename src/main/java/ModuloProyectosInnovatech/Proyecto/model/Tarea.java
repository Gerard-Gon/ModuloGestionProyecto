package ModuloProyectosInnovatech.Proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombreTareas", nullable = false, unique = true)
    private String nombreTareas;
    
    @Column(name = "descripcion", nullable = true)
    private String descripcionTareas;

    @Column(nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name="proyecto_id",nullable = false)
    private Proyecto proyecto;


    
}
