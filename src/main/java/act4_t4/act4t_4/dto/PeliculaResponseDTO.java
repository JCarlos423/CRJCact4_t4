package act4_t4.act4t_4.dto;

public class PeliculaResponseDTO {
    private Long id;
    private String titulo;
    private String genero;
    private Integer anioEstreno;
    private Long directorId;
    private String nombreDirector;

    public PeliculaResponseDTO(Long id, String titulo, String genero, Integer anioEstreno, Long directorId, String nombreDirector) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anioEstreno = anioEstreno;
        this.directorId = directorId;
        this.nombreDirector = nombreDirector;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public Integer getAnioEstreno() { return anioEstreno; }
    public Long getDirectorId() { return directorId; }
    public String getNombreDirector() { return nombreDirector; }
}