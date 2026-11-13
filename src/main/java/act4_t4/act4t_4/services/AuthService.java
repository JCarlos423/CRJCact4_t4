package act4_t4.act4t_4.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import act4_t4.act4t_4.dto.AuthResponseDTO;
import act4_t4.act4t_4.dto.LoginRequestDTO;
import act4_t4.act4t_4.dto.RegisterRequestDTO;
import act4_t4.act4t_4.dto.UsuarioResponseDTO;
import act4_t4.act4t_4.exceptions.BadRequestException;
import act4_t4.act4t_4.models.Usuario;
import act4_t4.act4t_4.repository.UsuarioRepository;
import act4_t4.act4t_4.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El correo electrónico ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol("ROLE_USER");

        Usuario guardado = usuarioRepository.save(usuario);
        String token = jwtService.generateToken(guardado);

        UsuarioResponseDTO userDto = new UsuarioResponseDTO(
                guardado.getId(), guardado.getNombre(), guardado.getEmail(), guardado.getRol()
        );
        return new AuthResponseDTO(token, userDto);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);
        UsuarioResponseDTO userDto = new UsuarioResponseDTO(
                usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol()
        );
        return new AuthResponseDTO(token, userDto);
    }
}