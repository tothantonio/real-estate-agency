import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PreturiSpatiuPerMoneda extends JFrame {

    private JTextField tipSpatiuField;
    private JTable resultTable;
    private JButton searchButton;

    public PreturiSpatiuPerMoneda() {
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

        searchButton = new JButton("Search");
        inputPanel.add(tipSpatiuLabel);
        inputPanel.add(tipSpatiuField);
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Zona pentru afișarea rezultatelor
        resultTable = new JTable();
        resultTable.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        // Acțiunea la apasarea butonului
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipSpatiu = tipSpatiuField.getText().trim();
                if (!tipSpatiu.isEmpty()) {
                    try {
                        searchPricesBySpaceType(tipSpatiu);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(PreturiSpatiuPerMoneda.this, "Eroare la interogarea bazei de date: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PreturiSpatiuPerMoneda.this, "Vă rugăm să introduceți un tip valid de spațiu.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void searchPricesBySpaceType(String tipSpatiu) throws SQLException {
        String query = """
            SELECT O.moneda, MIN(O.pret) AS pret_minim, AVG(O.pret) AS pret_mediu, MAX(O.pret) AS pret_maxim
            FROM Oferta O
            JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
            JOIN Tip T ON S.id_tip = T.id_tip
            WHERE T.denumire = ?
            GROUP BY O.moneda
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tipSpatiu);

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultTableModel model = new DefaultTableModel(new String[]{"Moneda", "Preț Minim", "Preț Mediu", "Preț Maxim"}, 0);

                while (rs.next()) {
                    String moneda = rs.getString("moneda");
                    double pretMinim = rs.getDouble("pret_minim");
                    double pretMediu = rs.getDouble("pret_mediu");
                    double pretMaxim = rs.getDouble("pret_maxim");

                    model.addRow(new Object[]{moneda, pretMinim, pretMediu, pretMaxim});
                }

                resultTable.setModel(model);

                // Set preferred widths for each column
                TableColumnModel columnModel = resultTable.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(100); // Moneda
                columnModel.getColumn(1).setPreferredWidth(100); // Preț Minim
                columnModel.getColumn(2).setPreferredWidth(100); // Preț Mediu
                columnModel.getColumn(3).setPreferredWidth(100); // Preț Maxim

            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PreturiSpatiuPerMoneda frame = new PreturiSpatiuPerMoneda();
            frame.setVisible(true);
        });
    }
}