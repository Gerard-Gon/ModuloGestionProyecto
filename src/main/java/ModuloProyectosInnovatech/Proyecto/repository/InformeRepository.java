package ModuloProyectosInnovatech.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ModuloProyectosInnovatech.Proyecto.model.Informe;

@Repository
public interface InformeRepository extends JpaRepository<Informe, Integer> {

    // Desactiva todos los informes de una tarea en una sola query (evita N+1)
    @Modifying
    @Query("UPDATE Informe i SET i.estado = false WHERE i.tarea.id = :tareaId")
    void desactivarPorTarea(@Param("tareaId") Integer tareaId);

    // Desactiva todos los informes de todas las tareas de un proyecto en una sola query
    @Modifying
    @Query("UPDATE Informe i SET i.estado = false WHERE i.tarea.id IN " +
           "(SELECT t.id FROM Tarea t WHERE t.proyecto.id = :proyectoId)")
    void desactivarPorProyecto(@Param("proyectoId") Integer proyectoId);

}

