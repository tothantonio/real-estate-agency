import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Spatii3CamerePretMin {
    public void getSpatii3CamerePretMin() {
        String query = "SELECT * " +
                "FROM Oferta O " +
                "JOIN Spatiu S ON O.id_spatiu = S.id_spatiu " +
                "JOIN Tip T ON S.id_tip = T.id_tip " +
                "WHERE T.caracteristici LIKE '%3 camere%' " +
                "AND O.vanzare = 'D' " +
                "AND O.id_spatiu IN ( " +
                "    SELECT id_spatiu " +
                "    FROM Spatiu S " +
                "    JOIN Tip T ON S.id_tip = T.id_tip " +
                "    WHERE T.caracteristici LIKE '%3 camere%' " +
                ") ORDER BY O.pret ASC";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Spații cu 3 camere la prețul cel mai mic:");
            while (rs.next()) {
                System.out.println("ID Spațiu: " + rs.getInt("id_spatiu") +
                        ", Pret: " + rs.getDouble("pret") +
                        ", Moneda: " + rs.getString("moneda"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
