package vallegrade.edu.pe.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import vallegrade.edu.pe.model.Usuario;
import vallegrade.edu.pe.controller.UsuarioController;

public class FrmUsuarios extends JFrame {

    // ======= COLORES =======
    private static final Color COLOR_FONDO_OSCURO = new Color(43, 43, 43);
    private static final Color COLOR_PANEL_OSCURO = new Color(60, 63, 65);
    private static final Color COLOR_TEXTO_CLARO = new Color(187, 187, 187);
    private static final Color COLOR_ACENTO = new Color(75, 110, 175);
    private static final Color COLOR_CAMPO_TEXTO = new Color(69, 73, 74);
    private static final Color COLOR_SELECCION = new Color(75, 110, 175);

    // ======= COMPONENTES =======
    public JTable tabla;
    private DefaultTableModel modelo;

    public JButton btnAgregar, btnModificar, btnActualizar, btnEliminar, btnLimpiar;
    public JButton btnGenerarPDF, btnGenerarDOC, btnGenerarExcel;

    public JTextField txtId, txtNombre, txtEmail, txtRol, txtBuscar;  // ✅ Cambié txtCorreo por txtEmail

    public FrmUsuarios() {

        setTitle("Gestión de Usuarios");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_FONDO_OSCURO);

        // ================= PANEL BÚSQUEDA =================
        JPanel panelBuscar = new JPanel(new BorderLayout(5, 5));
        panelBuscar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelBuscar.setBackground(COLOR_FONDO_OSCURO);

        JLabel lblBuscar = crearLabel("Buscar:");
        panelBuscar.add(lblBuscar, BorderLayout.WEST);

        txtBuscar = new JTextField();
        estilizarCampoTexto(txtBuscar);
        txtBuscar.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 2));
        panelBuscar.add(txtBuscar, BorderLayout.CENTER);

        // ================= FORMULARIO =================
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(COLOR_ACENTO, 2),
                        "Datos del Usuario",
                        0, 0, new Font("Arial", Font.BOLD, 13),
                        COLOR_TEXTO_CLARO
                )
        );
        panelFormulario.setBackground(COLOR_FONDO_OSCURO);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtEmail = new JTextField();  // ✅ Cambié de txtCorreo
        txtRol = new JTextField();

        estilizarCampoTexto(txtId);
        estilizarCampoTexto(txtNombre);
        estilizarCampoTexto(txtEmail);  // ✅ Cambié de txtCorreo
        estilizarCampoTexto(txtRol);

        panelFormulario.add(crearLabel("ID:"));
        panelFormulario.add(txtId);

        panelFormulario.add(crearLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(crearLabel("Email:"));  // ✅ Cambié de "Correo:"
        panelFormulario.add(txtEmail);  // ✅ Cambié de txtCorreo

        panelFormulario.add(crearLabel("Rol:"));
        panelFormulario.add(txtRol);

        // ============== TABLA ==============
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Rol"}, 0);  // ✅ Cambié "Correo" por "Email"
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        estilizarTabla();

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarDatosSeleccionados();
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(COLOR_PANEL_OSCURO);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 1));

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(COLOR_FONDO_OSCURO);
        panelTabla.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(COLOR_ACENTO, 2),
                        "Lista de Usuarios",
                        0, 0, new Font("Arial", Font.BOLD, 13),
                        COLOR_TEXTO_CLARO
                )
        );
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // ============= BOTONES CRUD =============
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(COLOR_FONDO_OSCURO);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        estilizarBoton(btnAgregar);
        estilizarBoton(btnModificar);
        estilizarBoton(btnActualizar);
        estilizarBoton(btnEliminar);
        estilizarBoton(btnLimpiar);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // ============= BOTONES EXPORTAR =============
        JPanel panelExportar = new JPanel(new FlowLayout());
        panelExportar.setBackground(COLOR_FONDO_OSCURO);

        btnGenerarPDF = new JButton("PDF");
        btnGenerarDOC = new JButton("DOC");
        btnGenerarExcel = new JButton("Excel");

        estilizarBoton(btnGenerarPDF);
        estilizarBoton(btnGenerarDOC);
        estilizarBoton(btnGenerarExcel);

        panelExportar.add(btnGenerarPDF);
        panelExportar.add(btnGenerarDOC);
        panelExportar.add(btnGenerarExcel);

        // ARMAR VISTA COMPLETA
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO_OSCURO);
        panelSuperior.add(panelBuscar, BorderLayout.NORTH);
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(2, 1));
        panelInferior.add(panelBotones);
        panelInferior.add(panelExportar);
        add(panelInferior, BorderLayout.SOUTH);

        btnLimpiar.addActionListener(e -> limpiarCampos());

        // CONTROLADOR
        new UsuarioController(this);
    }

    // =====================================================
    // ==================== ESTILOS =========================
    // =====================================================

    private JLabel crearLabel(String t) {
        JLabel lbl = new JLabel(t);
        lbl.setForeground(COLOR_TEXTO_CLARO);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }

    private void estilizarCampoTexto(JTextField campo) {
        campo.setBackground(COLOR_CAMPO_TEXTO);
        campo.setForeground(COLOR_TEXTO_CLARO);
        campo.setCaretColor(COLOR_TEXTO_CLARO);
        campo.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 1));
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(COLOR_ACENTO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarTabla() {
        tabla.setBackground(COLOR_PANEL_OSCURO);
        tabla.setForeground(COLOR_TEXTO_CLARO);
        tabla.setSelectionBackground(COLOR_SELECCION);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(COLOR_FONDO_OSCURO);
        tabla.getTableHeader().setBackground(COLOR_CAMPO_TEXTO);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_CLARO);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
    }

    // =====================================================
    // ==================== LÓGICA =========================
    // =====================================================

    public void mostrarUsuarios(List<Usuario> lista) {
        modelo.setRowCount(0);
        for (Usuario u : lista)
            modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getEmail(), u.getRol()});  // ✅ Cambié getCorreo() por getEmail()
    }

    public Usuario obtenerDatosFormulario() {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
            return new Usuario(id, txtNombre.getText().trim(), txtEmail.getText().trim(), txtRol.getText().trim());  // ✅ Cambié txtCorreo por txtEmail
        } catch (Exception e) {
            mostrarError("Valores inválidos");
            return null;
        }
    }

    public int obtenerIdSeleccionado() {
        int fila = tabla.getSelectedRow();
        return fila != -1 ? Integer.parseInt(tabla.getValueAt(fila, 0).toString()) : -1;
    }

    private void cargarDatosSeleccionados() {
        int f = tabla.getSelectedRow();
        if (f != -1) {
            txtId.setText(tabla.getValueAt(f, 0).toString());
            txtNombre.setText(tabla.getValueAt(f, 1).toString());
            txtEmail.setText(tabla.getValueAt(f, 2).toString());  // ✅ Cambié de txtCorreo
            txtRol.setText(tabla.getValueAt(f, 3).toString());
        }
    }

    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEmail.setText("");  // ✅ Cambié de txtCorreo
        txtRol.setText("");
        txtBuscar.setText("");
        tabla.clearSelection();
    }

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    public void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String m) {
        JOptionPane.showMessageDialog(this, m, "Error", JOptionPane.ERROR_MESSAGE);
    }
}