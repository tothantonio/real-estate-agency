import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAgency {
    public static boolean addAgency(int idAgentie, String nume, String telefon) {
        String query = "{CALL AddAgency(?, ?, ?)}";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAgentie);
            stmt.setString(2, nume);
            stmt.setString(3, telefon);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setVisible(boolean b) {

    }
}