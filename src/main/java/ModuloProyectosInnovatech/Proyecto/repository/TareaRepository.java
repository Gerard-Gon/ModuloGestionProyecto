package ModuloProyectosInnovatech.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ModuloProyectosInnovatech.Proyecto.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    // Desactiva todas las tareas de un proyecto en una sola query (evita N+1)
    @Modifying
    @Query("UPDATE Tarea t SET t.estado = false WHERE t.proyecto.id = :proyectoId")
    void desactivarPorProyecto(@Param("proyectoId") Integer proyectoId);

}
