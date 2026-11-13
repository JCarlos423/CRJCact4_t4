package act4_t4.act4t_4.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;

    public PeliculaService(PeliculaRepository peliculaRepository, DirectorRepository directorRepository) {
        this.peliculaRepository = peliculaRepository;
        this.directorRepository = directorRepository;
    }

    public List<PeliculaDTO> listarTodas() {
        return peliculaRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public Optional<PeliculaDTO> obtenerPorId(Long id) {
        return peliculaRepository.findById(id).map(this::convertirADto);
    }

    public PeliculaDTO guardar(PeliculaDTO dto) {
        Pelicula pelicula = convertirAEntidad(dto);
        Pelicula guardada = peliculaRepository.save(pelicula);
        return convertirADto(guardada);
    }

    public Optional<PeliculaDTO> actualizar(Long id, PeliculaDTO dto) {
        return peliculaRepository.findById(id).map(existente -> {
            existente.setTitulo(dto.getTitulo());
            existente.setGenero(dto.getGenero());
            existente.setAnioEstreno(dto.getAnioEstreno());

            Director director = directorRepository.findById(dto.getDirectorId())
                    .orElseThrow(() -> new RuntimeException("Director no encontrado con ID: " + dto.getDirectorId()));
            existente.setDirector(director);

            Pelicula actualizada = peliculaRepository.save(existente);
            return convertirADto(actualizada);
        });
    }

    public boolean eliminar(Long id) {
        if (peliculaRepository.existsById(id)) {
            peliculaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PeliculaDTO convertirADto(Pelicula pelicula) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(pelicula.getId());
        dto.setTitulo(pelicula.getTitulo());
        dto.setGenero(pelicula.getGenero());
        dto.setAnioEstreno(pelicula.getAnioEstreno());
        if (pelicula.getDirector() != null) {
            dto.setDirectorId(pelicula.getDirector().getId());
            dto.setNombreDirector(pelicula.getDirector().getNombre());
        }
        return dto;
    }

    private Pelicula convertirAEntidad(PeliculaDTO dto) {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(dto.getId());
        pelicula.setTitulo(dto.getTitulo());
        pelicula.setGenero(dto.getGenero());
        pelicula.setAnioEstreno(dto.getAnioEstreno());

        Director director = directorRepository.findById(dto.getDirectorId())
                .orElseThrow(() -> new RuntimeException("Director no encontrado con ID: " + dto.getDirectorId()));
        pelicula.setDirector(director);

        return pelicula;
    }
}