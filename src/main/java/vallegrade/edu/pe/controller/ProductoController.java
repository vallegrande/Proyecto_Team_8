package vallegrade.edu.pe.controller;

import vallegrade.edu.pe.model.Producto;
import vallegrade.edu.pe.view.FrmProducto;
import vallegrade.edu.pe.dao.ProductoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController implements ActionListener {

    private FrmProducto vista;
    private ProductoDAO dao;

    public ProductoController(FrmProducto vista) {
        this.vista = vista;
        this.dao = new ProductoDAO();

        // Registrar listeners de botones
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

        // Mostrar productos al iniciar
        listarProductos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.btnAgregar) {
            agregarProducto();
        } else if (src == vista.btnModificar) {
            cargarProductoSeleccionado();
        } else if (src == vista.btnActualizar) {
            actualizarProducto();
        } else if (src == vista.btnEliminar) {
            eliminarProducto();
        } else if (src == vista.btnLimpiar) {
            vista.limpiarCampos();
        }
    }

    // ======================= MÉTODOS =========================

    private void listarProductos() {
        List<Producto> lista = dao.listar();
        vista.mostrarProductos(lista);
    }

    private void agregarProducto() {
        Producto p = vista.obtenerDatosFormulario();
        if (p != null) {
            boolean ok = dao.agregar(p);
            if (ok) {
                vista.mostrarMensaje("✅ Producto agregado correctamente");
                listarProductos();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("❌ Error al agregar producto");
            }
        }
    }

    private void cargarProductoSeleccionado() {
        int id = vista.obtenerIdSeleccionado();
        if (id == -1) {
            vista.mostrarError("Seleccione un producto de la tabla para modificar");
            return;
        }
        Producto p = dao.buscarPorId(id);
        if (p != null) {
            vista.txtId.setText(String.valueOf(p.getId()));
            vista.txtNombre.setText(p.getNombre());
            vista.txtPrecio.setText(String.valueOf(p.getPrecio()));
            vista.txtStock.setText(String.valueOf(p.getStock()));
        } else {
            vista.mostrarError("No se encontró el producto seleccionado");
        }
    }

    private void actualizarProducto() {
        // Verificar que haya un producto seleccionado en la tabla
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            listarProductos(); // 🔄 Recargar tabla por si la BD cambió externamente
            vista.mostrarError("⚠️ Seleccione un producto de la tabla para actualizar");
            return;
        }

        Producto p = vista.obtenerDatosFormulario();
        if (p != null) {
            try {
                int id = Integer.parseInt(vista.txtId.getText().trim());
                p.setId(id);  // ✅ Asignar el ID correcto al producto
            } catch (NumberFormatException ex) {
                vista.mostrarError("❌ ID inválido para actualizar");
                return;
            }

            boolean ok = dao.actualizar(p);
            if (ok) {
                vista.mostrarMensaje("✅ Producto actualizado correctamente");
                listarProductos();   // 🔄 Refrescar tabla después de actualizar
                vista.limpiarCampos();
            } else {
                vista.mostrarError("❌ Error al actualizar producto");
            }
        }
    }



    private void eliminarProducto() {
        int id = vista.obtenerIdSeleccionado();
        if (id == -1) {
            vista.mostrarError("Seleccione un producto de la tabla para eliminar");
            return;
        }

        boolean confirmar = vista.confirmarAccion("¿Seguro que desea eliminar este producto?");
        if (confirmar) {
            boolean ok = dao.eliminar(id);
            if (ok) {
                vista.mostrarMensaje("✅ Producto eliminado correctamente");
                listarProductos();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("❌ Error al eliminar producto");
            }
        }
    }
}
