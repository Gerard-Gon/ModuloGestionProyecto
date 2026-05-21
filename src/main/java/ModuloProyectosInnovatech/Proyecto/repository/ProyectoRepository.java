package ModuloProyectosInnovatech.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ModuloProyectosInnovatech.Proyecto.model.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto,Integer> {
    
    //Aca hacer un JOIN hacia Usuarios para poder busqueda
    //Del jefe de proyecto

}
