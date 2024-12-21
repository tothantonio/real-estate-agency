import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddOffer {
    public static boolean addOffer(int idAgentie, int idSpatiu, char vanzare, int pret, String moneda) {
        String query = "{CALL AddOffer(?, ?, ?, ?, ?)}";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAgentie);
            stmt.setInt(2, idSpatiu);
            stmt.setString(3, String.valueOf(vanzare));
            stmt.setInt(4, pret);
            stmt.setString(5, moneda);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}