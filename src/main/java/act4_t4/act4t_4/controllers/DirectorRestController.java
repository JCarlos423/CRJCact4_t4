package act4_t4.act4t_4.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import act4_t4.act4t_4.dto.DirectorRequestDTO;
import act4_t4.act4t_4.dto.DirectorResponseDTO;
import act4_t4.act4t_4.services.DirectorService;

@RestController
@RequestMapping("/api/directores")
public class DirectorRestController {

    private final DirectorService directorService;

    public DirectorRestController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public ResponseEntity<Page<DirectorResponseDTO>> listar(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(directorService.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(directorService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<DirectorResponseDTO> crear(@Valid @RequestBody DirectorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(directorService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DirectorRequestDTO dto) {
        return ResponseEntity.ok(directorService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        directorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}