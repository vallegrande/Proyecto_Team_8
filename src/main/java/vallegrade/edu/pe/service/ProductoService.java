package vallegrade.edu.pe.service;

import vallegrade.edu.pe.model.Producto;
import vallegrade.edu.pe.model.ProductoDAO;

import java.util.List;

public class ProductoService {
    private ProductoDAO dao = new ProductoDAO();

    public List<Producto> obtenerProductos() {
        return dao.listar();
    }

    public boolean agregarProducto(Producto p) {
        return dao.agregar(p);
    }

    public boolean actualizarProducto(Producto p) {
        return dao.actualizar(p);
    }

    public boolean eliminarProducto(int id) {
        return dao.eliminar(id);
    }

    public Producto buscarProductoPorId(int id) {
        return dao.buscarPorId(id);
    }
}
