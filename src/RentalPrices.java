import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalPrices extends JFrame {

    private JTextField tipSpatiuField;
    private JTextArea resultArea;
    private JButton searchButton;

    public RentalPrices() {
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
        resultArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // Acțiunea la apăsarea butonului
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipSpatiu = tipSpatiuField.getText().trim();
                if (!tipSpatiu.isEmpty()) {
                    //System.out.println("Button clicked. Searching for: " + tipSpatiu);
                    searchPricesBySpaceType(tipSpatiu);
                } else {
                    resultArea.setText("Vă rugăm să introduceți un tip valid de spațiu.");
                }
            }
        });
    }

    private void searchPricesBySpaceType(String tipSpatiu) {
        String query = """
        SELECT S.zona, O.moneda, MIN(O.pret) AS pret_minim, AVG(O.pret) AS pret_mediu, MAX(O.pret) AS pret_maxim
        FROM Oferta O
        JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
        JOIN Tip T ON S.id_tip = T.id_tip
        WHERE T.denumire = ? AND O.vanzare = 'N'
        GROUP BY S.zona, O.moneda
    """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tipSpatiu);
            //System.out.println("Executing query: " + query);

            try (ResultSet rs = stmt.executeQuery()) {
                StringBuilder result = new StringBuilder("Prețuri de închiriere pentru spații de tip '" + tipSpatiu + "' per zonă și monedă:\n\n");

                boolean hasResults = false;
                while (rs.next()) {
                    hasResults = true;
                    result.append("Zonă: ").append(rs.getString("zona")).append("\n")
                            .append("Moneda: ").append(rs.getString("moneda")).append("\n")
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RentalPrices frame = new RentalPrices();
            frame.setVisible(true);
        });
    }
}