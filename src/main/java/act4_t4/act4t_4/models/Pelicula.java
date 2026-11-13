package act4_t4.act4t_4.models;

import jakarta.persistence.*;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String genero;

    private Integer anioEstreno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    public Pelicula() {}

    public Pelicula(String titulo, String genero, Integer anioEstreno, Director director) {
        this.titulo = titulo;
        this.genero = genero;
        this.anioEstreno = anioEstreno;
        this.director = director;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Integer getAnioEstreno() { return anioEstreno; }
    public void setAnioEstreno(Integer anioEstreno) { this.anioEstreno = anioEstreno; }

    public Director getDirector() { return director; }
    public void setDirector(Director director) { this.director = director; }
}