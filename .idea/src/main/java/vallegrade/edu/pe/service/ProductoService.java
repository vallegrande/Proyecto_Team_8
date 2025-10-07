package vallegrade.edu.pe.service;


import vallegrade.edu.pe.model.Producto;
import vallegrade.edu.pe.model.ProductoDAO;

import java.util.List;

public class ProductoService {
    private ProductoDAO dao = new ProductoDAO();

    public List<Producto> obtenerProductos() {
        return dao.listar();
    }
}
