import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SpatiiByAdresa extends JFrame {

    private JTextField adresaField;
    private JButton searchButton;
    private JTable resultTable;

    public SpatiiByAdresa() {
        setTitle("Searching spaces");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Close only this window

        setLayout(new BorderLayout());

        // Top panel with the address field and search button
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter the address:"));
        adresaField = new JTextField(20);
        topPanel.add(adresaField);
        searchButton = new JButton("Search");
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        resultTable = new JTable();
        resultTable.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prefixAdresa = adresaField.getText();
                if (!prefixAdresa.isEmpty()) {
                    searchSpatiiByAdresa(prefixAdresa);
                } else {
                    JOptionPane.showMessageDialog(SpatiiByAdresa.this, "Please enter the address.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(SpatiiByAdresa.this, "Please enter the address.", "Error", JOptionPane.ERROR_MESSAGE);
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

            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Adresa", "Zona", "Suprafata"}, 0);
            while (rs.next()) {
                int idSpatiu = rs.getInt("id_spatiu");
                String adresa = rs.getString("adresa");
                String zona = rs.getString("zona");
                double suprafata = rs.getDouble("suprafata");
                model.addRow(new Object[]{idSpatiu, adresa, zona, suprafata});
            }

            resultTable.setModel(model);

            // Set preferred widths for each column
            TableColumnModel columnModel = resultTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(50);  // ID
            columnModel.getColumn(1).setPreferredWidth(200); // Adresa
            columnModel.getColumn(2).setPreferredWidth(100); // Zona
            columnModel.getColumn(3).setPreferredWidth(100); // Suprafata

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error querying the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpatiiByAdresa app = new SpatiiByAdresa();
            app.setVisible(true);
        });
    }
}