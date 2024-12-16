import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SpatiiByAdresa extends JFrame {

    private JTextField adresaField;
    private JButton searchButton;
    private JTextArea resultArea;

    public SpatiiByAdresa() {
        setTitle("Searching spaces");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Close only this window

        setLayout(new BorderLayout());

        // Top panel with the address field and search button
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter the start of the address:"));
        adresaField = new JTextField(20);
        topPanel.add(adresaField);
        searchButton = new JButton("Search");
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prefixAdresa = adresaField.getText();
                if (!prefixAdresa.isEmpty()) {
                    searchSpatiiByAdresa(prefixAdresa);
                } else {
                    resultArea.setText("Please enter the start of the address.");
                }
            }
        });

        adresaField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prefixAdresa = adresaField.getText();
                if (!prefixAdresa.isEmpty()) {
                    searchSpatiiByAdresa(prefixAdresa);
                } else {
                    resultArea.setText("Please enter the start of the address.");
                }
            }
        });
    }

    private void searchSpatiiByAdresa(String prefixAdresa) {
        String query = "SELECT * FROM Spatiu WHERE adresa LIKE ? ORDER BY zona ASC, suprafata DESC";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prefixAdresa + "%");

            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id_spatiu"))
                        .append(", Adresa: ").append(rs.getString("adresa"))
                        .append(", Zona: ").append(rs.getString("zona"))
                        .append(", Suprafata: ").append(rs.getDouble("suprafata"))
                        .append("\n");
            }

            if (result.length() == 0) {
                result.append("No spaces found for address '" + prefixAdresa + "'.");
            }

            resultArea.setText(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error querying the database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpatiiByAdresa app = new SpatiiByAdresa();
            app.setVisible(true);
        });
    }
}
