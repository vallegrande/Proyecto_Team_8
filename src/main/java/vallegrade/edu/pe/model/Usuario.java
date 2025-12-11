package vallegrade.edu.pe.model;

public class Usuario {
    private int id;
    private String nombre;
    private String email;  // ✅ Cambié de 'correo' a 'email'
    private String rol;

    public Usuario() {}

    public Usuario(int id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public Usuario(String nombre, String email, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {  // ✅ Cambié el nombre del getter de getCorreo()
        return email;
    }

    public void setEmail(String email) {  // ✅ Cambié el nombre del setter de setCorreo()
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}