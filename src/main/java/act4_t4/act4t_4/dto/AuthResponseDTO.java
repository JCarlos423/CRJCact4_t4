package act4_t4.act4t_4.dto;

public class AuthResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private UsuarioResponseDTO usuario;

    public AuthResponseDTO(String token, UsuarioResponseDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() { return token; }
    public String getTipo() { return tipo; }
    public UsuarioResponseDTO getUsuario() { return usuario; }
}