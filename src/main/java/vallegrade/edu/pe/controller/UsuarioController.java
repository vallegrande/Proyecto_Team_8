package vallegrade.edu.pe.controller;

import vallegrade.edu.pe.model.Usuario;
import vallegrade.edu.pe.model.UsuarioDAO;
import vallegrade.edu.pe.view.FrmUsuarios;

import javax.swing.*;

public class UsuarioController {

    private FrmUsuarios vista;
    private UsuarioDAO dao;

    public UsuarioController(FrmUsuarios vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();

        // Cargar usuarios al abrir la ventana
        cargarTabla();

        // Listeners de botones
        this.vista.btnAgregar.addActionListener(e -> agregar());
        this.vista.btnModificar.addActionListener(e -> modificar());
        this.vista.btnActualizar.addActionListener(e -> cargarTabla());
        this.vista.btnEliminar.addActionListener(e -> eliminar());

        // Listeners de reportes
        this.vista.btnGenerarPDF.addActionListener(e -> generarPDF());
        this.vista.btnGenerarDOC.addActionListener(e -> generarDOC());
        this.vista.btnGenerarExcel.addActionListener(e -> generarExcel());

        // Listener de búsqueda en tiempo real
        this.vista.txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { buscar(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { buscar(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { buscar(); }
        });
    }

    // ===================== CRUD ===========================

    private void cargarTabla() {
        vista.mostrarUsuarios(dao.listar());
    }

    private void buscar() {
        String txt = vista.getTextoBusqueda();
        if (txt.isEmpty()) {
            cargarTabla();
        } else {
            vista.mostrarUsuarios(dao.buscar(txt));
        }
    }

    private void agregar() {
        Usuario u = vista.obtenerDatosFormulario();
        if (u == null) return;

        if (u.getNombre().isEmpty() || u.getEmail().isEmpty()) {
            vista.mostrarError("⚠️ Complete todos los campos");
            return;
        }

        if (dao.agregar(u)) {
            vista.mostrarMensaje("✅ Usuario agregado correctamente");
            cargarTabla();
            vista.limpiarCampos();
        } else {
            vista.mostrarError("❌ Error al agregar usuario");
        }
    }

    private void modificar() {
        Usuario u = vista.obtenerDatosFormulario();
        if (u == null) return;

        if (u.getId() == 0) {
            vista.mostrarError("⚠️ Seleccione un usuario de la tabla");
            return;
        }

        if (dao.actualizar(u)) {
            vista.mostrarMensaje("✅ Usuario modificado correctamente");
            cargarTabla();
            vista.limpiarCampos();
        } else {
            vista.mostrarError("❌ Error al modificar usuario");
        }
    }

    private void eliminar() {
        int id = vista.obtenerIdSeleccionado();
        if (id == -1) {
            vista.mostrarError("⚠️ Seleccione un usuario de la tabla");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar este usuario?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(id)) {
                vista.mostrarMensaje("✅ Usuario eliminado correctamente");
                cargarTabla();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("❌ Error al eliminar usuario");
            }
        }
    }

    // ========================= REPORTES =========================

    private void generarPDF() {
        vista.mostrarMensaje("📄 Función PDF en desarrollo");
    }

    private void generarDOC() {
        vista.mostrarMensaje("📝 Función DOC en desarrollo");
    }

    private void generarExcel() {
        vista.mostrarMensaje("📊 Función Excel en desarrollo");
    }
}