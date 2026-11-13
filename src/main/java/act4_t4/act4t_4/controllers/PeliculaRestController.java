package act4_t4.act4t_4.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.act3_t4.act3_t4.dto.PeliculaDTO;
import com.act3_t4.act3_t4.services.PeliculaService;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaRestController {

    private final PeliculaService peliculaService;

    public PeliculaRestController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public List<PeliculaDTO> listar() {
        return peliculaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> obtenerPorId(@PathVariable Long id) {
        return peliculaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> crear(@RequestBody PeliculaDTO dto) {
        PeliculaDTO creada = peliculaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> actualizar(@PathVariable Long id, @RequestBody PeliculaDTO dto) {
        return peliculaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (peliculaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}