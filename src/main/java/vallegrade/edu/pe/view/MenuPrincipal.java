package vallegrade.edu.pe.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal(String usuario) {
        setTitle("Bienvenido - VALLEGRADE");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(25, 85, 145));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(25, 85, 145));
        panelTitulo.setPreferredSize(new Dimension(600, 80));

        JLabel lblBienvenida = new JLabel("Bienvenido a VALLEGRADE");
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblBienvenida);

        JPanel panelOpciones = new JPanel(new GridLayout(2, 2, 20, 20));
        panelOpciones.setBackground(new Color(240, 240, 240));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel panelAdminUsuarios = crearPanel("Administrar Usuarios", "Administre usuarios");
        JButton btnUsuarios = (JButton) panelAdminUsuarios.getComponent(0);
        btnUsuarios.addActionListener(e -> {
            this.dispose();
            new FrmUsuarios().setVisible(true);
        });
        panelOpciones.add(panelAdminUsuarios);

        JPanel panelAdminProductos = crearPanel("Administrar Productos", "Administre productos");
        JButton btnProductos = (JButton) panelAdminProductos.getComponent(0);
        btnProductos.addActionListener(e -> {
            this.dispose();
            new FrmProducto().setVisible(true);
        });
        panelOpciones.add(panelAdminProductos);

        JPanel panelReportes = crearPanel("Reportes", "Visualice reportes");
        panelOpciones.add(panelReportes);

        JPanel panelSalir = crearPanel("Salir", "Cerrar sesión");
        JButton btnSalir = (JButton) panelSalir.getComponent(0);
        btnSalir.addActionListener(e -> System.exit(0));
        panelOpciones.add(panelSalir);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelOpciones, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private JPanel crearPanel(String titulo, String desc) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JButton boton = new JButton(titulo);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);

        JLabel lbl = new JLabel(desc);
        lbl.setFont(new Font("Arial", Font.PLAIN, 11));

        panel.add(boton, BorderLayout.CENTER);
        panel.add(lbl, BorderLayout.SOUTH);
        return panel;
    }
}