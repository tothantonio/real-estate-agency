import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OferteVanzare extends JFrame {

    private JTextField minPriceField, maxPriceField;
    private JButton searchButton;
    private JTextArea resultArea;

    public OferteVanzare() {
        setTitle("Search Offers by Price");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        JLabel minPriceLabel = new JLabel("Enter minimum price:");
        minPriceField = new JTextField(10);
        JLabel maxPriceLabel = new JLabel("Enter maximum price:");
        maxPriceField = new JTextField(10);
        searchButton = new JButton("Search");

        minPriceField.setPreferredSize(new Dimension(100, 30));
        maxPriceField.setPreferredSize(new Dimension(100, 30));
        searchButton.setPreferredSize(new Dimension(100, 20));

        topPanel.add(minPriceLabel);
        topPanel.add(minPriceField);
        topPanel.add(maxPriceLabel);
        topPanel.add(maxPriceField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double minPrice = Double.parseDouble(minPriceField.getText());
                    double maxPrice = Double.parseDouble(maxPriceField.getText());
                    if (minPrice <= maxPrice) {
                        searchOffersByPrice(minPrice, maxPrice);
                    } else {
                        resultArea.setText("The minimum price must be less than or equal to the maximum price.");
                    }
                } catch (NumberFormatException ex) {
                    resultArea.setText("Please enter valid numbers for prices.");
                }
            }
        });
    }

    private void searchOffersByPrice(double minPrice, double maxPrice) {
        String query = "SELECT * FROM Oferta WHERE vanzare = 'D' AND moneda = 'EUR' AND pret BETWEEN ? AND ? ORDER BY pret ASC";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);

            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append("ID Spatiu: ").append(rs.getInt("id_spatiu"))
                        .append(", Pret: ").append(rs.getDouble("pret"))
                        .append(", Moneda: ").append(rs.getString("moneda"))
                        .append("\n");
            }

            if (result.length() == 0) {
                result.append("No offers found in this price range.");
            }

            resultArea.setText(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error querying the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OferteVanzare app = new OferteVanzare();
            app.setVisible(true);
        });
    }
}
