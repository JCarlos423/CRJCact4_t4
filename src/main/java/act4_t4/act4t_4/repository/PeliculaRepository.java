package act4_t4.act4t_4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import act4_t4.act4t_4.models.Pelicula;



@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}