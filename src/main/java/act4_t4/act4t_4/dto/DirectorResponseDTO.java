package act4_t4.act4t_4.dto;

public class DirectorResponseDTO {
    private Long id;
    private String nombre;
    private String nacionalidad;

    public DirectorResponseDTO(Long id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNacionalidad() { return nacionalidad; }
}