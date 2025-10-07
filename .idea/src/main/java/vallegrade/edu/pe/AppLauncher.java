package vallegrade.edu.pe;


import vallegrade.edu.pe.view.FrmProducto;

public class AppLauncher {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new FrmProducto().setVisible(true);
        });
    }
}
