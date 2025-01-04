import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class OferteVanzare extends JFrame {

    private JTextField minPriceField, maxPriceField;
    private JButton searchButton;
    private JTable resultTable;

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

        resultTable = new JTable();
        resultTable.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);
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
                        JOptionPane.showMessageDialog(OferteVanzare.this, "The minimum price must be less than or equal to the maximum price.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(OferteVanzare.this, "Please enter valid numbers for prices.", "Error", JOptionPane.ERROR_MESSAGE);
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
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID Spatiu", "Pret", "Moneda"}, 0);

            while (rs.next()) {
                int idSpatiu = rs.getInt("id_spatiu");
                double pret = rs.getDouble("pret");
                String moneda = rs.getString("moneda");

                model.addRow(new Object[]{idSpatiu, pret, moneda});
            }

            resultTable.setModel(model);

            // Set preferred widths for each column
            TableColumnModel columnModel = resultTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100); // ID Spatiu
            columnModel.getColumn(1).setPreferredWidth(100); // Pret
            columnModel.getColumn(2).setPreferredWidth(50);  // Moneda

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error querying the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OferteVanzare app = new OferteVanzare();
            app.setVisible(true);
        });
    }
}