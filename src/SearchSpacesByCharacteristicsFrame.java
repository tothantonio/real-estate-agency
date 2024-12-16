import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchSpacesByCharacteristicsFrame extends JFrame {

    public SearchSpacesByCharacteristicsFrame() {
        setTitle("Search Spaces by Characteristics");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the window without stopping the application

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Add fields for characteristics
        JLabel lblCaracteristici = new JLabel("Enter Characteristics (comma-separated):");
        JTextField txtCaracteristici = new JTextField();
        JButton btnSearch = new JButton("Search");

        // Add components to the panel
        panel.add(lblCaracteristici);
        panel.add(txtCaracteristici);
        panel.add(new JLabel(""));  // Empty space for layout
        panel.add(btnSearch);

        // Add the panel to the main window
        add(panel, BorderLayout.CENTER);

        // Action on search button click
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String caracteristici = txtCaracteristici.getText();
                if (caracteristici.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchSpacesByCharacteristicsFrame.this, "Please enter some characteristics to search for.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Logic to search spaces based on characteristics
                    searchSpacesByCharacteristics(caracteristici);
                }
            }
        });
    }

    // Method to search spaces based on characteristics
    private void searchSpacesByCharacteristics(String caracteristici) {
        // Database connection
        String url = "jdbc:mysql://localhost:3306/real_estate"; // Example database URL
        String user = "root";
        String password = ""; // Replace with your password

        // Split the characteristics by comma
        String[] caracteristiciArray = caracteristici.split(",");
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Oferta O " +
                "JOIN Spatiu S ON O.id_spatiu = S.id_spatiu " +
                "JOIN Tip T ON S.id_tip = T.id_tip " +
                "WHERE O.vanzare = 'D' ");

        // Add conditions for each characteristic
        for (int i = 0; i < caracteristiciArray.length; i++) {
            queryBuilder.append("AND T.caracteristici LIKE ? ");
        }
        queryBuilder.append("ORDER BY O.pret ASC;");
        String query = queryBuilder.toString();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set parameters for the query
            for (int i = 0; i < caracteristiciArray.length; i++) {
                String caracteristiciPattern = "%" + caracteristiciArray[i].trim() + "%"; // Add % to search anywhere in the text
                stmt.setString(i + 1, caracteristiciPattern);
            }

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Display results in a dialog window
            StringBuilder results = new StringBuilder();
            while (rs.next()) {
                String ofertaDetails = "ID: " + rs.getInt("id_spatiu") +
                        ", Pret: " + rs.getDouble("pret") + rs.getString("moneda") +
                        ", Descriere: " + rs.getString("caracteristici");
                results.append(ofertaDetails).append("\n");
            }

            if (results.length() == 0) {
                JOptionPane.showMessageDialog(this, "No spaces found with the given characteristics.");
            } else {
                JOptionPane.showMessageDialog(this, "Found the following spaces:\n" + results.toString());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and display the search window
            SearchSpacesByCharacteristicsFrame frame = new SearchSpacesByCharacteristicsFrame();
            frame.setVisible(true);
        });
    }
}