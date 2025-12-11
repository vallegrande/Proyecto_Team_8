package vallegrade.edu.pe.controller;

import vallegrade.edu.pe.model.Producto;
import vallegrade.edu.pe.view.FrmProducto;
import vallegrade.edu.pe.model.ProductoDAO;
import vallegrade.edu.pe.service.ReporteService;

import javax.swing.*;
import java.util.List;

public class ProductoController {

    private FrmProducto vista;
    private ProductoDAO dao;
    private ReporteService reporteService;

    public ProductoController(FrmProducto vista) {
        this.vista = vista;
        this.dao = new ProductoDAO();
        this.reporteService = new ReporteService();

        // Cargar productos al iniciar
        cargarProductos();

        // Registrar listeners de botones CRUD
        this.vista.btnAgregar.addActionListener(e -> agregarProducto());
        this.vista.btnModificar.addActionListener(e -> cargarProductoSeleccionado());
        this.vista.btnActualizar.addActionListener(e -> actualizarProducto());
        this.vista.btnEliminar.addActionListener(e -> eliminarProducto());
        this.vista.btnLimpiar.addActionListener(e -> vista.limpiarCampos());

        // Registrar listeners de botones de reportes
        this.vista.btnGenerarPDF.addActionListener(e -> generarReportePDF());
        this.vista.btnGenerarDOC.addActionListener(e -> generarReporteDOC());
        this.vista.btnGenerarExcel.addActionListener(e -> generarReporteExcel());

        // Listener de búsqueda en tiempo real
        this.vista.txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { buscar(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { buscar(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { buscar(); }
        });
    }

    // ======================= MÉTODOS PRINCIPALES =========================

    private void cargarProductos() {
        List<Producto> lista = dao.listar();
        vista.mostrarProductos(lista);
    }

    private void buscar() {
        String txt = vista.getTextoBusqueda();
        if (txt.isEmpty()) {
            cargarProductos();
        } else {
            List<Producto> resultados = dao.buscar(txt);
            vista.mostrarProductos(resultados);
        }
    }

    private void agregarProducto() {
        Producto p = vista.obtenerDatosFormulario();
        if (p != null && !p.getNombre().isEmpty()) {
            boolean ok = dao.agregar(p);
            if (ok) {
                vista.mostrarMensaje("✅ Producto agregado correctamente");
                cargarProductos();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("❌ Error al agregar producto");
            }
        } else {
            vista.mostrarError("⚠️ Complete todos los campos correctamente");
        }
    }

    private void cargarProductoSeleccionado() {
        int id = vista.obtenerIdSeleccionado();
        if (id == -1) {
            vista.mostrarError("⚠️ Seleccione un producto de la tabla para modificar");
            return;
        }
        Producto p = dao.buscarPorId(id);
        if (p != null) {
            vista.txtId.setText(String.valueOf(p.getId()));
            vista.txtNombre.setText(p.getNombre());
            vista.txtPrecio.setText(String.valueOf(p.getPrecio()));
            vista.txtStock.setText(String.valueOf(p.getStock()));
        } else {
            vista.mostrarError("❌ No se encontró el producto seleccionado");
        }
    }

    private void actualizarProducto() {
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            vista.mostrarError("⚠️ Seleccione un producto de la tabla para actualizar");
            return;
        }

        Producto p = vista.obtenerDatosFormulario();
        if (p != null) {
            try {
                int id = Integer.parseInt(vista.txtId.getText().trim());
                p.setId(id);
            } catch (NumberFormatException ex) {
                vista.mostrarError("❌ ID inválido para actualizar");
                return;
            }

            boolean ok = dao.actualizar(p);
            if (ok) {
                vista.mostrarMensaje("✅ Producto actualizado correctamente");
                cargarProductos();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("❌ Error al actualizar producto");
            }
        }
    }

    private void eliminarProducto() {
        int id = vista.obtenerIdSeleccionado();
        if (id == -1) {
            vista.mostrarError("⚠️ Seleccione un producto de la tabla para eliminar");
            return;
        }

        boolean confirmar = vista.confirmarAccion("¿Está seguro de que desea eliminar este producto?");
        if (confirmar) {
            boolean ok = dao.eliminar(id);
            if (ok) {
                vista.mostrarMensaje("✅ Producto eliminado correctamente");
                cargarProductos();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("❌ Error al eliminar producto");
            }
        }
    }

    // ======================= MÉTODOS DE REPORTES =========================

    private void generarReportePDF() {
        try {
            List<Producto> productos = dao.listar();
            if (productos.isEmpty()) {
                vista.mostrarError("⚠️ No hay productos para generar el reporte");
                return;
            }

            boolean ok = reporteService.generarReportePDF(productos);
            if (ok) {
                vista.mostrarMensaje("✅ Reporte PDF generado exitosamente");
            } else {
                vista.mostrarError("❌ Error al generar el reporte PDF");
            }
        } catch (Exception ex) {
            vista.mostrarError("❌ Error al generar PDF: " + ex.getMessage());
        }
    }

    private void generarReporteDOC() {
        try {
            List<Producto> productos = dao.listar();
            if (productos.isEmpty()) {
                vista.mostrarError("⚠️ No hay productos para generar el reporte");
                return;
            }

            boolean ok = reporteService.generarReporteDOC(productos);
            if (ok) {
                vista.mostrarMensaje("✅ Reporte DOC generado exitosamente");
            } else {
                vista.mostrarError("❌ Error al generar el reporte DOC");
            }
        } catch (Exception ex) {
            vista.mostrarError("❌ Error al generar DOC: " + ex.getMessage());
        }
    }

    private void generarReporteExcel() {
        try {
            List<Producto> productos = dao.listar();
            if (productos.isEmpty()) {
                vista.mostrarError("⚠️ No hay productos para generar el reporte");
                return;
            }

            boolean ok = reporteService.generarReporteExcel(productos);
            if (ok) {
                vista.mostrarMensaje("✅ Reporte Excel generado exitosamente");
            } else {
                vista.mostrarError("❌ Error al generar el reporte Excel");
            }
        } catch (Exception ex) {
            vista.mostrarError("❌ Error al generar Excel: " + ex.getMessage());
        }
    }
}