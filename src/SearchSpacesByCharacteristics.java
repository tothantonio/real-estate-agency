import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SearchSpacesByCharacteristics extends JFrame {

    private JTextField txtCaracteristici;
    private JTable resultTable;

    public SearchSpacesByCharacteristics() {
        setTitle("Search Spaces by Characteristics");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the window without stopping the application

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Add fields for characteristics
        JLabel lblCaracteristici = new JLabel("Enter Characteristics (comma-separated):");
        txtCaracteristici = new JTextField();
        JButton btnSearch = new JButton("Search");

        // Add components to the panel
        panel.add(lblCaracteristici);
        panel.add(txtCaracteristici);
        panel.add(new JLabel(""));  // Empty space for layout
        panel.add(btnSearch);

        // Add the panel to the main window
        add(panel, BorderLayout.NORTH);

        // Add result table
        resultTable = new JTable();
        resultTable.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        // Action on search button click
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String caracteristici = txtCaracteristici.getText();
                if (caracteristici.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchSpacesByCharacteristics.this, "Please enter some characteristics to search for.", "Error", JOptionPane.ERROR_MESSAGE);
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
        String url = "jdbc:mysql://localhost:3306/real_estate";
        String user = "root";
        String password = "";

        // Split the characteristics by comma
        String[] caracteristiciArray = caracteristici.split(",");
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Oferta O " +
                "JOIN Spatiu S ON O.id_spatiu = S.id_spatiu " +
                "JOIN Tip T ON S.id_tip = T.id_tip " +
                "WHERE O.vanzare = 'D' ");

        // Add conditions for each characteristic
        for (int i = 0; i < caracteristiciArray.length; i++) {
            queryBuilder.append("AND LOWER(T.caracteristici) LIKE ? ");
        }
        queryBuilder.append("ORDER BY O.pret ASC;");
        String query = queryBuilder.toString();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set parameters for the query
            for (int i = 0; i < caracteristiciArray.length; i++) {
                String caracteristiciPattern = "%" + caracteristiciArray[i].trim().toLowerCase() + "%"; // Add % to search anywhere in the text
                stmt.setString(i + 1, caracteristiciPattern);
            }

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Display results in the table
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Pret", "Moneda", "Descriere"}, 0);
            while (rs.next()) {
                int idSpatiu = rs.getInt("id_spatiu");
                double pret = rs.getDouble("pret");
                String moneda = rs.getString("moneda");
                String descriere = rs.getString("caracteristici");
                model.addRow(new Object[]{idSpatiu, pret, moneda, descriere});
            }

            resultTable.setModel(model);

            // Set preferred widths for each column
            TableColumnModel columnModel = resultTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(50);  // ID
            columnModel.getColumn(1).setPreferredWidth(100); // Pret
            columnModel.getColumn(2).setPreferredWidth(50);  // Moneda
            columnModel.getColumn(3).setPreferredWidth(300); // Descriere

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and display the search window
            SearchSpacesByCharacteristics frame = new SearchSpacesByCharacteristics();
            frame.setVisible(true);
        });
    }
}