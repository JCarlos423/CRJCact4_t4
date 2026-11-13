package act4_t4.act4t_4.controllers;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import act4_t4.act4t_4.dto.PeliculaRequestDTO;
import act4_t4.act4t_4.dto.PeliculaResponseDTO;
import act4_t4.act4t_4.services.PeliculaService;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaRestController {

    private final PeliculaService peliculaService;

    public PeliculaRestController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public ResponseEntity<Page<PeliculaResponseDTO>> listar(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(peliculaService.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PeliculaResponseDTO> crear(@Valid @RequestBody PeliculaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PeliculaRequestDTO dto) {
        return ResponseEntity.ok(peliculaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        peliculaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}