import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OferteInchiriere extends JFrame {

    private JTextField minPriceField, maxPriceField;
    private JComboBox<String> offerTypeComboBox;
    private JTextArea resultArea;
    private JButton searchButton;

    public OferteInchiriere() {
        setTitle("Search Rental Offers");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JLabel minPriceLabel = new JLabel("Enter minimum price:");
        minPriceField = new JTextField();
        JLabel maxPriceLabel = new JLabel("Enter maximum price:");
        maxPriceField = new JTextField();
        JLabel offerTypeLabel = new JLabel("Select offer type:");

        offerTypeComboBox = new JComboBox<>(new String[]{"Apartament", "Garsoniera", "Garaj", "Vila", "Spatiu Comercial", "Duplex"});

        inputPanel.add(minPriceLabel);
        inputPanel.add(minPriceField);
        inputPanel.add(maxPriceLabel);
        inputPanel.add(maxPriceField);
        inputPanel.add(offerTypeLabel);
        inputPanel.add(offerTypeComboBox);

        searchButton = new JButton("Search");

        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double minPrice = Double.parseDouble(minPriceField.getText());
                    double maxPrice = Double.parseDouble(maxPriceField.getText());
                    String offerType = (String) offerTypeComboBox.getSelectedItem();
                    searchRentalOffers(minPrice, maxPrice, offerType);
                } catch (NumberFormatException ex) {
                    resultArea.setText("Please enter valid numbers for prices.");
                }
            }
        });
    }

    private void searchRentalOffers(double minPrice, double maxPrice, String offerType) {
        String query = "SELECT Spatiu.adresa, Spatiu.zona, Spatiu.suprafata, Tip.denumire, Oferta.pret, Oferta.moneda " +
                "FROM Oferta " +
                "JOIN Spatiu ON Oferta.id_spatiu = Spatiu.id_spatiu " +
                "JOIN Tip ON Spatiu.id_tip = Tip.id_tip " +
                "WHERE Oferta.vanzare = 'N' " +  // Căutăm doar oferte de închiriere
                "AND Tip.denumire = ? " +
                "AND Oferta.pret BETWEEN ? AND ? " +
                "ORDER BY Oferta.pret ASC";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, offerType);  // Folosim tipul de spațiu selectat
            stmt.setDouble(2, minPrice);
            stmt.setDouble(3, maxPrice);

            ResultSet rs = stmt.executeQuery();
            StringBuilder result = new StringBuilder();

            while (rs.next()) {
                result.append("Adresa: ").append(rs.getString("adresa"))
                        .append(", Zona: ").append(rs.getString("zona"))
                        .append(", Suprafata: ").append(rs.getDouble("suprafata"))
                        .append(", Tip: ").append(rs.getString("denumire"))
                        .append(", Pret: ").append(rs.getDouble("pret"))
                        .append(" ").append(rs.getString("moneda"))
                        .append("\n");
            }

            if (result.length() == 0) {
                result.append("No rental offers found in this price range.");
            }

            resultArea.setText(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error querying the database.");
        }
    }
}
