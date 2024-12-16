import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentiSpatiiPretSimilar {
    public void getAgentiPretSimilar() {
        String query = "SELECT A.nume " +
                "FROM Agentie A " +
                "WHERE A.id_agentie IN ( " +
                "    SELECT O.id_agentie " +
                "    FROM Oferta O " +
                "    JOIN Spatiu S ON O.id_spatiu = S.id_spatiu " +
                "    JOIN Tip T ON S.id_tip = T.id_tip " +
                "    WHERE S.id_tip = ( " +
                "        SELECT S2.id_tip " +
                "        FROM Spatiu S2 " +
                "        WHERE S2.id_spatiu = 101 " +
                "    ) AND O.pret = ( " +
                "        SELECT O2.pret " +
                "        FROM Oferta O2 " +
                "        WHERE O2.id_agentie = 1 AND O2.id_spatiu = 101 " +
                "    ) AND O.moneda = ( " +
                "        SELECT O3.moneda " +
                "        FROM Oferta O3 " +
                "        WHERE O3.id_agentie = 1 AND O3.id_spatiu = 101 " +
                "    ))";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Agențiile care oferă spații de același tip și preț:");
            while (rs.next()) {
                System.out.println("Nume Agenție: " + rs.getString("nume"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
