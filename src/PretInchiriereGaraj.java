import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PretInchiriereGaraj {
    public void getPreturiInchiriereGaraj() {
        String query = "SELECT S.zona, MIN(O.pret) AS pret_minim, AVG(O.pret) AS pret_mediu, MAX(O.pret) AS pret_maxim " +
                "FROM Oferta O " +
                "JOIN Spatiu S ON O.id_spatiu = S.id_spatiu " +
                "JOIN Tip T ON S.id_tip = T.id_tip " +
                "WHERE T.denumire = 'garaj' AND O.vanzare = 'N' " +
                "GROUP BY S.zona";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Preturi de închiriere pentru garaje pe zone:");
            while (rs.next()) {
                System.out.println("Zona: " + rs.getString("zona") +
                        ", Pret Minim: " + rs.getDouble("pret_minim") +
                        ", Pret Mediu: " + rs.getDouble("pret_mediu") +
                        ", Pret Maxim: " + rs.getDouble("pret_maxim"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
