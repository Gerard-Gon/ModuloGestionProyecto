package ModuloProyectosInnovatech.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ModuloProyectosInnovatech.Proyecto.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    
}
