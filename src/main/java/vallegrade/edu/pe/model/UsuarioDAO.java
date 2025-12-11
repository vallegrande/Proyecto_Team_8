package vallegrade.edu.pe.model;

import vallegrade.edu.pe.database.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String TABLE = "usuarios";

    // ========================= LISTAR =========================
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email, 'Usuario' as rol FROM " + TABLE;

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),  // ✅ Cambié de "correo" a "email"
                        rs.getString("rol")
                );
                lista.add(u);
            }
            System.out.println("✅ Se obtuvieron " + lista.size() + " usuarios de la BD");

        } catch (Exception e) {
            System.out.println("❌ Error en listar(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // ========================= AGREGAR =========================
    public boolean agregar(Usuario u) {
        String sql = "INSERT INTO " + TABLE + " (nombre, email) VALUES (?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());  // ✅ Correcto, usa getEmail()

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                System.out.println("✅ Usuario agregado correctamente");
            }
            return resultado > 0;

        } catch (Exception e) {
            System.out.println("❌ Error en agregar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // ======================= BUSCAR POR ID ======================
    public Usuario buscarPorId(int id) {
        String sql = "SELECT id, nombre, email, 'Usuario' as rol FROM " + TABLE + " WHERE id=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),  // ✅ Cambié de "correo" a "email"
                        rs.getString("rol")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error en buscarPorId(): " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // ========================= ACTUALIZAR =========================
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE " + TABLE + " SET nombre=?, email=? WHERE id=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());  // ✅ Cambié de "correo" a "email"
            ps.setInt(3, u.getId());

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                System.out.println("✅ Usuario actualizado correctamente");
            }
            return resultado > 0;

        } catch (Exception e) {
            System.out.println("❌ Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // ========================= ELIMINAR =========================
    public boolean eliminar(int id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("✅ Usuario eliminado correctamente");
            }
            return resultado > 0;

        } catch (Exception e) {
            System.out.println("❌ Error en eliminar(): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // ========================= BUSCAR TEXTO =========================
    public List<Usuario> buscar(String texto) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email, 'Usuario' as rol FROM " + TABLE +
                " WHERE nombre LIKE ? OR email LIKE ?";  // ✅ Cambié de "correo" a "email"

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String filtro = "%" + texto + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),  // ✅ Cambié de "correo" a "email"
                        rs.getString("rol")
                );
                lista.add(u);
            }

        } catch (Exception e) {
            System.out.println("❌ Error en buscar(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}