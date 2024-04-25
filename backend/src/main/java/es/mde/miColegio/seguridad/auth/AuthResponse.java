package es.mde.miColegio.seguridad.auth;

public class AuthResponse {
  private String token;
  private String username;

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AuthResponse() {
  }

  public AuthResponse(String token, String username) {
    this.token = token;
    this.username = username;
  }

}
