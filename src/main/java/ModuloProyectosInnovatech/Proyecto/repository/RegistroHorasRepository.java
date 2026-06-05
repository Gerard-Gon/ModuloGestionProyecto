package ModuloProyectosInnovatech.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ModuloProyectosInnovatech.Proyecto.model.RegistroHoras;

@Repository
public interface RegistroHorasRepository extends JpaRepository<RegistroHoras, Integer> {

    // Desactiva todos los registros de horas de una tarea en una sola query (evita N+1)
    @Modifying
    @Query("UPDATE RegistroHoras r SET r.estado = false WHERE r.tarea.id = :tareaId")
    void desactivarPorTarea(@Param("tareaId") Integer tareaId);

    // Desactiva todos los registros de horas de todas las tareas de un proyecto en una sola query
    @Modifying
    @Query("UPDATE RegistroHoras r SET r.estado = false WHERE r.tarea.id IN " +
           "(SELECT t.id FROM Tarea t WHERE t.proyecto.id = :proyectoId)")
    void desactivarPorProyecto(@Param("proyectoId") Integer proyectoId);

}

