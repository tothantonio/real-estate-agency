import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpatiiPretDiferenta {
    public void getPerechiSpatiiPretSub100() {
        String query = "SELECT O1.id_spatiu AS id_spatiu1, O2.id_spatiu AS id_spatiu2 " +
                "FROM Oferta O1 " +
                "JOIN Oferta O2 ON O1.id_agentie = O2.id_agentie AND O1.id_spatiu < O2.id_spatiu " +
                "WHERE O1.vanzare = 'D' AND O2.vanzare = 'D' " +
                "AND ABS(O1.pret - O2.pret) < 100";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Perechi de spații cu diferența de preț sub 100 EUR:");
            while (rs.next()) {
                System.out.println("ID Spațiu 1: " + rs.getInt("id_spatiu1") +
                        ", ID Spațiu 2: " + rs.getInt("id_spatiu2"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
