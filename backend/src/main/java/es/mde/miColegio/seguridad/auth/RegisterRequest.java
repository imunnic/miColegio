package es.mde.miColegio.seguridad.auth;

public class RegisterRequest {
  private String username;
  private String password;
  private String nombre;
  private String apellido;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public RegisterRequest(){}

  public RegisterRequest(String username, String password, String nombre, String apellido) {
    this.username = username;
    this.password = password;
    this.nombre = nombre;
    this.apellido = apellido;
  }
}
