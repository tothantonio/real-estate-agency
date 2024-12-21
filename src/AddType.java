import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddType {
    public static boolean addType(int idTip, String denumire, String caracteristici) {
        String query = "{CALL AddType(?, ?, ?)}";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idTip);
            stmt.setString(2, denumire);
            stmt.setString(3, caracteristici);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}