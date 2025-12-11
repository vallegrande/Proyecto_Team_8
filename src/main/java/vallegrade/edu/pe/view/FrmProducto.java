package vallegrade.edu.pe.view;

import vallegrade.edu.pe.controller.ProductoController;
import vallegrade.edu.pe.model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmProducto extends JFrame {
    // Definir colores personalizados - Modo Oscuro
    private static final Color COLOR_FONDO_OSCURO = new Color(43, 43, 43);
    private static final Color COLOR_PANEL_OSCURO = new Color(60, 63, 65);
    private static final Color COLOR_TEXTO_CLARO = new Color(187, 187, 187);
    private static final Color COLOR_ACENTO = new Color(75, 110, 175);
    private static final Color COLOR_CAMPO_TEXTO = new Color(69, 73, 74);
    private static final Color COLOR_SELECCION = new Color(75, 110, 175);

    // Componentes de la tabla
    public JTable tabla;
    private DefaultTableModel modelo;

    // Botones
    public JButton btnAgregar;
    public JButton btnModificar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnLimpiar;
    // AGREGAR ESTAS LÍNEAS:
    public JButton btnGenerarPDF;
    public JButton btnGenerarDOC;
    public JButton btnGenerarExcel;

    // Campos de texto
    public JTextField txtId;
    public JTextField txtNombre;
    public JTextField txtPrecio;
    public JTextField txtStock;
    public JTextField txtBuscar;

    public FrmProducto() {
        setTitle("Gestión de Productos");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Aplicar color de fondo al frame
        getContentPane().setBackground(COLOR_FONDO_OSCURO);

        // ======= PANEL DE BÚSQUEDA =======
        JPanel panelBuscar = new JPanel(new BorderLayout(5, 5));
        panelBuscar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelBuscar.setBackground(COLOR_FONDO_OSCURO);

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setForeground(COLOR_TEXTO_CLARO);
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        panelBuscar.add(lblBuscar, BorderLayout.WEST);

        txtBuscar = new JTextField();
        txtBuscar.setBackground(COLOR_CAMPO_TEXTO);
        txtBuscar.setForeground(COLOR_TEXTO_CLARO);
        txtBuscar.setCaretColor(COLOR_TEXTO_CLARO);
        txtBuscar.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 2));
        panelBuscar.add(txtBuscar, BorderLayout.CENTER);

        // ======= PANEL DE FORMULARIO =======
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACENTO, 2),
                "Datos del Producto",
                0,
                0,
                new Font("Arial", Font.BOLD, 13),
                COLOR_TEXTO_CLARO
        ));
        panelFormulario.setBackground(COLOR_FONDO_OSCURO);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        // Estilizar campos de texto
        estilizarCampoTexto(txtId);
        estilizarCampoTexto(txtNombre);
        estilizarCampoTexto(txtPrecio);
        estilizarCampoTexto(txtStock);

        // Crear y estilizar labels
        JLabel lblId = crearLabel("ID:");
        JLabel lblNombre = crearLabel("Nombre:");
        JLabel lblPrecio = crearLabel("Precio:");
        JLabel lblStock = crearLabel("Stock:");

        panelFormulario.add(lblId);
        panelFormulario.add(txtId);
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblPrecio);
        panelFormulario.add(txtPrecio);
        panelFormulario.add(lblStock);
        panelFormulario.add(txtStock);

        // ======= PANEL DE TABLA =======
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estilizar tabla
        tabla.setBackground(COLOR_PANEL_OSCURO);
        tabla.setForeground(COLOR_TEXTO_CLARO);
        tabla.setSelectionBackground(COLOR_SELECCION);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(COLOR_FONDO_OSCURO);
        tabla.getTableHeader().setBackground(COLOR_CAMPO_TEXTO);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_CLARO);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Agregar listener para cargar datos en el formulario al seleccionar una fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(COLOR_PANEL_OSCURO);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 1));

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACENTO, 2),
                "Lista de Productos",
                0,
                0,
                new Font("Arial", Font.BOLD, 13),
                COLOR_TEXTO_CLARO
        ));
        panelTabla.setBackground(COLOR_FONDO_OSCURO);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // ======= PANEL DE BOTONES =======
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(COLOR_FONDO_OSCURO);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnGenerarPDF = new JButton("PDF");
        btnGenerarDOC = new JButton("DOC");
        btnGenerarExcel = new JButton("Excel");

        // Estilizar botones
        estilizarBoton(btnAgregar);
        estilizarBoton(btnModificar);
        estilizarBoton(btnActualizar);
        estilizarBoton(btnEliminar);
        estilizarBoton(btnLimpiar);
        // Estilizar los nuevos botones
        estilizarBoton(btnGenerarPDF);
        estilizarBoton(btnGenerarDOC);
        estilizarBoton(btnGenerarExcel);

        // Agregar iconos a los botones (opcional)
        btnAgregar.setIcon(UIManager.getIcon("FileView.fileIcon"));
        btnModificar.setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
        btnActualizar.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
        btnEliminar.setIcon(UIManager.getIcon("InternalFrame.closeIcon"));
        btnLimpiar.setIcon(UIManager.getIcon("FileView.directoryIcon"));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnGenerarPDF);
        panelBotones.add(btnGenerarDOC);
        panelBotones.add(btnGenerarExcel);
        // ======= PANEL SUPERIOR (Búsqueda + Formulario) =======
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO_OSCURO);
        panelSuperior.add(panelBuscar, BorderLayout.NORTH);
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);

        // ======= AGREGAR COMPONENTES AL FRAME =======
        add(panelSuperior, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ======= EVENTO PARA LIMPIAR =======
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // ======= Iniciar controlador =======
        new ProductoController(this);
    }

    // ======= MÉTODOS DE ESTILIZACIÓN =======

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COLOR_TEXTO_CLARO);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private void estilizarCampoTexto(JTextField campo) {
        campo.setBackground(COLOR_CAMPO_TEXTO);
        campo.setForeground(COLOR_TEXTO_CLARO);
        campo.setCaretColor(COLOR_TEXTO_CLARO);
        campo.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 1));
        campo.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(COLOR_ACENTO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_ACENTO.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_ACENTO);
            }
        });
    }

    // ======= MÉTODOS PÚBLICOS =======

    /**
     * Muestra la lista de productos en la tabla
     */
    public void mostrarProductos(List<Producto> lista) {
        modelo.setRowCount(0); // Limpiar tabla

        for (Producto p : lista) {
            Object[] fila = {
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock()
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Obtiene los datos del formulario y retorna un objeto Producto
     */
    public Producto obtenerDatosFormulario() {
        try {
            // Obtener valores del formulario
            int id = 0;
            if (!txtId.getText().trim().isEmpty()) {
                id = Integer.parseInt(txtId.getText().trim());
            }

            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            // Crear el producto con el constructor que requiere todos los parámetros
            Producto p = new Producto(id, nombre, precio, stock);

            return p;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese valores numéricos válidos para precio y stock",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Obtiene el ID del producto seleccionado en la tabla
     */
    public int obtenerIdSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            return Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        }
        return -1; // Retornar -1 si no hay selección
    }

    /**
     * Carga los datos de la fila seleccionada en el formulario
     */
    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtNombre.setText(tabla.getValueAt(fila, 1).toString());
            txtPrecio.setText(tabla.getValueAt(fila, 2).toString());
            txtStock.setText(tabla.getValueAt(fila, 3).toString());
        }
    }

    /**
     * Limpia todos los campos del formulario
     */
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtBuscar.setText("");
        tabla.clearSelection();
    }

    /**
     * Obtiene el texto del campo de búsqueda
     */
    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    /**
     * Muestra un mensaje de información
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Confirma una acción con el usuario
     */
    public boolean confirmarAccion(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this,
                mensaje,
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Método main para ejecutar la aplicación
     */
    public static void main(String[] args) {
        try {
            // Configurar el look and feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            FrmProducto frm = new FrmProducto();
            frm.setVisible(true);
        });
    }
}