package vallegrade.edu.pe.service;

import vallegrade.edu.pe.model.UsuarioDAO;
import vallegrade.edu.pe.model.Usuario;

import java.util.List;

public class UsuarioService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public boolean agregar(Usuario u) {
        return dao.agregar(u); // <-- corregido
    }

    public boolean modificar(Usuario u) {
        return dao.actualizar(u);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }

    public List<Usuario> listar() {
        return dao.listar();
    }

    public Usuario obtenerPorId(int id) {
        return dao.buscarPorId(id); // <-- corregido
    }

    public List<Usuario> buscar(String txt) {
        return dao.buscar(txt);
    }
}
