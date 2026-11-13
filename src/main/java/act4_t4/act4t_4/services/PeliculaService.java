package act4_t4.act4t_4.services;

import org.springframework.stereotype.Service;

import act4_t4.act4t_4.dto.PeliculaRequestDTO;
import act4_t4.act4t_4.dto.PeliculaResponseDTO;
import act4_t4.act4t_4.exceptions.ResourceNotFoundException;
import act4_t4.act4t_4.models.Director;
import act4_t4.act4t_4.models.Pelicula;
import act4_t4.act4t_4.repository.DirectorRepository;
import act4_t4.act4t_4.repository.PeliculaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;

    public PeliculaService(PeliculaRepository peliculaRepository, DirectorRepository directorRepository) {
        this.peliculaRepository = peliculaRepository;
        this.directorRepository = directorRepository;
    }

    public Page<PeliculaResponseDTO> listarPaginado(Pageable pageable) {
        return peliculaRepository.findAll(pageable).map(this::toDto);
    }

    public PeliculaResponseDTO obtenerPorId(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con ID: " + id));
        return toDto(pelicula);
    }

    public PeliculaResponseDTO crear(PeliculaRequestDTO dto) {
        Director director = directorRepository.findById(dto.getDirectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado con ID: " + dto.getDirectorId()));

        Pelicula pelicula = new Pelicula(dto.getTitulo(), dto.getGenero(), dto.getAnioEstreno(), director);
        return toDto(peliculaRepository.save(pelicula));
    }

    public PeliculaResponseDTO actualizar(Long id, PeliculaRequestDTO dto) {
        Pelicula existente = peliculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con ID: " + id));

        Director director = directorRepository.findById(dto.getDirectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado con ID: " + dto.getDirectorId()));

        existente.setTitulo(dto.getTitulo());
        existente.setGenero(dto.getGenero());
        existente.setAnioEstreno(dto.getAnioEstreno());
        existente.setDirector(director);

        return toDto(peliculaRepository.save(existente));
    }

    public void eliminar(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Película no encontrada con ID: " + id);
        }
        peliculaRepository.deleteById(id);
    }

    private PeliculaResponseDTO toDto(Pelicula p) {
        Long dirId = p.getDirector() != null ? p.getDirector().getId() : null;
        String dirNombre = p.getDirector() != null ? p.getDirector().getNombre() : null;
        return new PeliculaResponseDTO(p.getId(), p.getTitulo(), p.getGenero(), p.getAnioEstreno(), dirId, dirNombre);
    }
}