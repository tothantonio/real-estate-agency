import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreturiSpatiuPerMoneda extends JFrame {

    private JTextField tipSpatiuField;
    private JTextArea resultArea;
    private JButton searchButton;

    public PreturiSpatiuPerMoneda() {
        setTitle("Prețuri Spații pe Monedă");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panoul de intrare pentru tipul spațiului
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5));

        JLabel tipSpatiuLabel = new JLabel("Introduceți tipul spațiului:");
        tipSpatiuField = new JTextField();

        searchButton = new JButton("Caută");
        inputPanel.add(tipSpatiuLabel);
        inputPanel.add(tipSpatiuField);
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Zonă pentru afișarea rezultatelor
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // Acțiunea la apăsarea butonului
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipSpatiu = tipSpatiuField.getText().trim();
                if (!tipSpatiu.isEmpty()) {
                    searchPricesBySpaceType(tipSpatiu);
                } else {
                    resultArea.setText("Vă rugăm să introduceți un tip valid de spațiu.");
                }
            }
        });
    }

    private void searchPricesBySpaceType(String tipSpatiu) {
        String query = """
            SELECT O.moneda, MIN(O.pret) AS pret_minim, AVG(O.pret) AS pret_mediu, MAX(O.pret) AS pret_maxim
            FROM Oferta O
            JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
            JOIN Tip T ON S.id_tip = T.id_tip
            WHERE T.denumire = ?
            GROUP BY O.moneda
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tipSpatiu);

            try (ResultSet rs = stmt.executeQuery()) {
                StringBuilder result = new StringBuilder("Prețuri pentru spații de tip '" + tipSpatiu + "' per monedă:\n\n");

                boolean hasResults = false;
                while (rs.next()) {
                    hasResults = true;
                    result.append("Moneda: ").append(rs.getString("moneda")).append("\n")
                            .append("  Preț Minim: ").append(rs.getDouble("pret_minim")).append("\n")
                            .append("  Preț Mediu: ").append(rs.getDouble("pret_mediu")).append("\n")
                            .append("  Preț Maxim: ").append(rs.getDouble("pret_maxim")).append("\n\n");
                }

                if (!hasResults) {
                    result.append("Nu s-au găsit rezultate pentru tipul specificat.");
                }

                resultArea.setText(result.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Eroare la interogarea bazei de date: " + e.getMessage());
        }
    }
}
