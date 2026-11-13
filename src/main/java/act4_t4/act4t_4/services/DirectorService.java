package act4_t4.act4t_4.services;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<DirectorDTO> listarTodos() {
        return directorRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public Optional<DirectorDTO> obtenerPorId(Long id) {
        return directorRepository.findById(id).map(this::convertirADto);
    }

    public DirectorDTO guardar(DirectorDTO dto) {
        Director director = convertirAEntidad(dto);
        Director guardado = directorRepository.save(director);
        return convertirADto(guardado);
    }

    public Optional<DirectorDTO> actualizar(Long id, DirectorDTO dto) {
        return directorRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setNacionalidad(dto.getNacionalidad());
            Director actualizado = directorRepository.save(existente);
            return convertirADto(actualizado);
        });
    }

    public boolean eliminar(Long id) {
        if (directorRepository.existsById(id)) {
            directorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private DirectorDTO convertirADto(Director director) {
        return new DirectorDTO(director.getId(), director.getNombre(), director.getNacionalidad());
    }

    private Director convertirAEntidad(DirectorDTO dto) {
        Director director = new Director();
        director.setId(dto.getId());
        director.setNombre(dto.getNombre());
        director.setNacionalidad(dto.getNacionalidad());
        return director;
    }
}