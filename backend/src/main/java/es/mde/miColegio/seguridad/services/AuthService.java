package es.mde.miColegio.seguridad.services;

import es.mde.miColegio.seguridad.auth.AuthResponse;
import es.mde.miColegio.seguridad.auth.LoginRequest;
import es.mde.miColegio.seguridad.auth.RegisterRequest;
import es.mde.miColegio.seguridad.usuarios.Rol;
import es.mde.miColegio.seguridad.usuarios.Usuario;
import es.mde.miColegio.seguridad.usuarios.UsuarioDAO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author JOSE LUIS PUENTES ALAMOS
 * Sobre el código inicial se ha añadido la asociación del profesor con el usuario
 */
@Service
public class AuthService {
  private final UsuarioDAO USUARIODAO;
  private final JwtService JWTSERVICE;
  private final PasswordEncoder ENCODER;
  private final AuthenticationManager MANAGER;

  public AuthService(UsuarioDAO USUARIODAO, JwtService JWTSERVICE, PasswordEncoder ENCODER,
      AuthenticationManager MANAGER) {
    this.USUARIODAO = USUARIODAO;
    this.JWTSERVICE = JWTSERVICE;
    this.ENCODER = ENCODER;
    this.MANAGER = MANAGER;
  }

  public AuthResponse login(LoginRequest request){
    this.MANAGER.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
    UserDetails user = this.USUARIODAO.findByUsername(request.getUsername()).orElseThrow();
    String token = this.JWTSERVICE.getToken(user);
    return new AuthResponse(token, request.getUsername());
  }

  public AuthResponse register(RegisterRequest request){
    Usuario usuario = new Usuario();
    usuario.setUsername(request.getUsername());
    usuario.setPassword(ENCODER.encode(request.getPassword()));
    usuario.setNombre(request.getNombre());
    usuario.setApellido(request.getApellido());
    usuario.setProfesor(request.getProfesor());
    usuario.setRol(Rol.PROFESOR);
    this.USUARIODAO.save(usuario);

    return new AuthResponse(this.JWTSERVICE.getToken(usuario), usuario.getUsername());

  }
}
