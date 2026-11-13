package act4_t4.act4t_4.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import act4_t4.act4t_4.dto.DirectorRequestDTO;
import act4_t4.act4t_4.dto.DirectorResponseDTO;
import act4_t4.act4t_4.exceptions.ResourceNotFoundException;
import act4_t4.act4t_4.models.Director;
import act4_t4.act4t_4.repository.DirectorRepository;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Page<DirectorResponseDTO> listarPaginado(Pageable pageable) {
        return directorRepository.findAll(pageable).map(this::toDto);
    }

    public DirectorResponseDTO obtenerPorId(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado con ID: " + id));
        return toDto(director);
    }

    public DirectorResponseDTO crear(DirectorRequestDTO dto) {
        Director director = new Director(dto.getNombre(), dto.getNacionalidad());
        return toDto(directorRepository.save(director));
    }

    public DirectorResponseDTO actualizar(Long id, DirectorRequestDTO dto) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director no encontrado con ID: " + id));
        director.setNombre(dto.getNombre());
        director.setNacionalidad(dto.getNacionalidad());
        return toDto(directorRepository.save(director));
    }

    public void eliminar(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Director no encontrado con ID: " + id);
        }
        directorRepository.deleteById(id);
    }

    private DirectorResponseDTO toDto(Director d) {
        return new DirectorResponseDTO(d.getId(), d.getNombre(), d.getNacionalidad());
    }
}