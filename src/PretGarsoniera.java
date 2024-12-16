import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PretGarsoniera {
    public void getPreturiGarsonierePerMoneda() {
        String query = "SELECT O.moneda, MIN(O.pret) AS pret_minim, AVG(O.pret) AS pret_mediu, MAX(O.pret) AS pret_maxim " +
                "FROM Oferta O " +
                "JOIN Spatiu S ON O.id_spatiu = S.id_spatiu " +
                "JOIN Tip T ON S.id_tip = T.id_tip " +
                "WHERE T.denumire = 'garsoniera' " +
                "GROUP BY O.moneda";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Preturi garsoniere per moneda:");
            while (rs.next()) {
                System.out.println("Moneda: " + rs.getString("moneda") +
                        ", Pret Minim: " + rs.getDouble("pret_minim") +
                        ", Pret Mediu: " + rs.getDouble("pret_mediu") +
                        ", Pret Maxim: " + rs.getDouble("pret_maxim"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
