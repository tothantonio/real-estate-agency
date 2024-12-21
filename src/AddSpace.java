import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddSpace {
    public static boolean addSpace(int idSpatiu, String adresa, int zona, int suprafata, int idTip) {
        String query = "{CALL AddSpace(?, ?, ?, ?, ?)}";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idSpatiu);
            stmt.setString(2, adresa);
            stmt.setInt(3, zona);
            stmt.setInt(4, suprafata);
            stmt.setInt(5, idTip);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}