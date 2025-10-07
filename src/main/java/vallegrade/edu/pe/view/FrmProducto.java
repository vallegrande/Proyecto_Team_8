package vallegrade.edu.pe.view;

import vallegrade.edu.pe.controller.ProductoController;
import vallegrade.edu.pe.model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmProducto extends JFrame {
    // Componentes de la tabla
    public JTable tabla;
    private DefaultTableModel modelo;

    // Botones
    public JButton btnAgregar;
    public JButton btnModificar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnLimpiar;

    // Campos de texto
    public JTextField txtId;
    public JTextField txtNombre;
    public JTextField txtPrecio;
    public JTextField txtStock;
    public JTextField txtBuscar;

    public FrmProducto() {
        setTitle("Gestión de Productos");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ======= PANEL DE BÚSQUEDA =======
        JPanel panelBuscar = new JPanel(new BorderLayout(5, 5));
        panelBuscar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelBuscar.add(new JLabel("Buscar:"), BorderLayout.WEST);
        txtBuscar = new JTextField();
        panelBuscar.add(txtBuscar, BorderLayout.CENTER);

        // ======= PANEL DE FORMULARIO =======
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);

        // ======= PANEL DE TABLA =======
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Agregar listener para cargar datos en el formulario al seleccionar una fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));
        panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ======= PANEL DE BOTONES =======
        JPanel panelBotones = new JPanel(new FlowLayout());

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

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

        // ======= PANEL SUPERIOR (Búsqueda + Formulario) =======
        JPanel panelSuperior = new JPanel(new BorderLayout());
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