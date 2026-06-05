package ModuloProyectosInnovatech.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ModuloProyectosInnovatech.Proyecto.model.AsignacionTarea;

@Repository
public interface AsignacionTareaRepository extends JpaRepository<AsignacionTarea, Integer> {

    // Desactiva todas las asignaciones de una tarea en una sola query (evita N+1)
    @Modifying
    @Query("UPDATE AsignacionTarea a SET a.estado = false WHERE a.tarea.id = :tareaId")
    void desactivarPorTarea(@Param("tareaId") Integer tareaId);

    // Desactiva todas las asignaciones de todas las tareas de un proyecto en una sola query
    @Modifying
    @Query("UPDATE AsignacionTarea a SET a.estado = false WHERE a.tarea.id IN " +
           "(SELECT t.id FROM Tarea t WHERE t.proyecto.id = :proyectoId)")
    void desactivarPorProyecto(@Param("proyectoId") Integer proyectoId);

}

