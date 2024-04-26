package es.mde.miColegio.seguridad.controllers;

import es.mde.miColegio.seguridad.auth.AuthResponse;
import es.mde.miColegio.seguridad.auth.LoginRequest;
import es.mde.miColegio.seguridad.auth.RegisterRequest;
import es.mde.miColegio.seguridad.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final AuthService AUTHSERVICE;

  public AuthController(AuthService AUTHSERVICE) {
    this.AUTHSERVICE = AUTHSERVICE;
  }

  @PostMapping("/api/auth/login")
  @ResponseBody
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
    return ResponseEntity.ok(AUTHSERVICE.login(request));
  }

  @PostMapping("/api/auth/register")
  @ResponseBody
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
    return ResponseEntity.ok(AUTHSERVICE.register(request));
  }
}
