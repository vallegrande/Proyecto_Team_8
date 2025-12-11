// ARCHIVO 1: LoginView.java
package vallegrade.edu.pe.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JButton loginButton;
    private JLabel mensajeLabel;

    public LoginView() {
        setTitle("Login");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titulo = new JLabel("Ingreso al Sistema");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        usuarioField = new JTextField(15);
        panel.add(usuarioField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        contrasenaField = new JPasswordField(15);
        panel.add(contrasenaField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.addActionListener(e -> manejarLogin());
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        mensajeLabel = new JLabel("");
        mensajeLabel.setForeground(Color.RED);
        panel.add(mensajeLabel, gbc);

        add(panel);
    }

    private void manejarLogin() {
        String usuario = usuarioField.getText();
        String contrasena = new String(contrasenaField.getPassword());

        if (validarCredenciales(usuario, contrasena)) {
            this.dispose();
            new MenuPrincipal(usuario).setVisible(true);
        } else {
            mensajeLabel.setText("Usuario o contraseña incorrectos");
        }
    }

    private boolean validarCredenciales(String usuario, String contrasena) {
        return (usuario.equals("admin") && contrasena.equals("1234")) ||
                (usuario.equals("Conca") && contrasena.equals("123"));
    }
}