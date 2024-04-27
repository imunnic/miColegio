package es.mde.miColegio.seguridad.auth;

/**
 * @author JOSE LUIS PUENTES ALAMOS
 * Sobre el código inicial se ha añadido a la request la asociación con el profesor.
 */
public class RegisterRequest {
  private String username;
  private String password;
  private String nombre;
  private String apellido;
  private int profesor;

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

  public int getProfesor() {
    return profesor;
  }

  public void setProfesor(int profesor) {
    this.profesor = profesor;
  }

  public RegisterRequest(){}

  public RegisterRequest(String username, String password, String nombre, String apellido, int profesor) {
    this.username = username;
    this.password = password;
    this.nombre = nombre;
    this.apellido = apellido;
    this.profesor = profesor;
  }
}
