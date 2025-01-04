import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class OferteInchiriere extends JFrame {

    private JTextField minPriceField, maxPriceField;
    private JComboBox<String> offerTypeComboBox;
    private JTable resultTable;
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
                    String offerType = (String) offerTypeComboBox.getSelectedItem();
                    searchRentalOffers(minPrice, maxPrice, offerType);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(OferteInchiriere.this, "Please enter valid numbers for prices.", "Error", JOptionPane.ERROR_MESSAGE);
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
            DefaultTableModel model = new DefaultTableModel(new String[]{"Adresa", "Zona", "Suprafata", "Tip", "Pret", "Moneda"}, 0);

            while (rs.next()) {
                String adresa = rs.getString("adresa");
                String zona = rs.getString("zona");
                double suprafata = rs.getDouble("suprafata");
                String tip = rs.getString("denumire");
                double pret = rs.getDouble("pret");
                String moneda = rs.getString("moneda");

                model.addRow(new Object[]{adresa, zona, suprafata, tip, pret, moneda});
            }

            resultTable.setModel(model);

            // Set preferred widths for each column
            TableColumnModel columnModel = resultTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(150); // Adresa
            columnModel.getColumn(1).setPreferredWidth(100); // Zona
            columnModel.getColumn(2).setPreferredWidth(100); // Suprafata
            columnModel.getColumn(3).setPreferredWidth(100); // Tip
            columnModel.getColumn(4).setPreferredWidth(100); // Pret
            columnModel.getColumn(5).setPreferredWidth(50);  // Moneda

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error querying the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OferteInchiriere frame = new OferteInchiriere();
            frame.setVisible(true);
        });
    }
}