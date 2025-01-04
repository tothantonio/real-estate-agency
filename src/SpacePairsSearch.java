import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SpacePairsSearch extends JFrame {

    private JTextField priceDifferenceField; // Input pentru diferența de preț
    private JTable resultTable; // Zonă pentru afișarea rezultatelor
    private JButton searchButton; // Butonul pentru căutare

    public SpacePairsSearch() {
        setTitle("Search Space Pairs by Price Difference");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrăm fereastra

        setLayout(new BorderLayout());

        // Panou pentru introducerea diferenței de preț
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JLabel priceDifferenceLabel = new JLabel("Enter price difference (EUR):");
        priceDifferenceField = new JTextField();

        inputPanel.add(priceDifferenceLabel);
        inputPanel.add(priceDifferenceField);

        searchButton = new JButton("Search");
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Zonă pentru afișarea rezultatelor
        resultTable = new JTable();
        resultTable.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        // Acțiune la apăsarea butonului
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double priceDifference = Double.parseDouble(priceDifferenceField.getText());
                    if (priceDifference < 0) {
                        JOptionPane.showMessageDialog(SpacePairsSearch.this, "Please enter a valid positive number for price difference.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        searchSpacePairs(priceDifference);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SpacePairsSearch.this, "Please enter a valid number for price difference.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void searchSpacePairs(double maxPriceDifference) {
        String query = """
    SELECT O1.id_spatiu AS id_spatiu1, O2.id_spatiu AS id_spatiu2, A.nume AS nume,
           ABS(O1.pret - O2.pret) AS price_difference
    FROM oferta O1
    JOIN oferta O2
        ON O1.id_agentie = O2.id_agentie AND O1.id_spatiu < O2.id_spatiu
    JOIN agentie A
        ON O1.id_agentie = A.id_agentie
    WHERE O1.vanzare = 'D' AND O2.vanzare = 'D'
      AND ABS(O1.pret - O2.pret) <= ?;
    """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, maxPriceDifference); // Set the user input dynamically

            ResultSet rs = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID Spatiu 1", "ID Spatiu 2", "Agency Name", "Price Difference"}, 0);

            while (rs.next()) {
                int idSpatiu1 = rs.getInt("id_spatiu1");
                int idSpatiu2 = rs.getInt("id_spatiu2");
                String denumireAgentie = rs.getString("nume");
                double priceDifference = rs.getDouble("price_difference");

                model.addRow(new Object[]{idSpatiu1, idSpatiu2, denumireAgentie, priceDifference});
            }

            resultTable.setModel(model);

            // Set preferred widths for each column
            TableColumnModel columnModel = resultTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100); // ID Spatiu 1
            columnModel.getColumn(1).setPreferredWidth(100); // ID Spatiu 2
            columnModel.getColumn(2).setPreferredWidth(200); // Agency Name
            columnModel.getColumn(3).setPreferredWidth(100); // Price Difference

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error querying the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpacePairsSearch frame = new SpacePairsSearch();
            frame.setVisible(true);
        });
    }
}