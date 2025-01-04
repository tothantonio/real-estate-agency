import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class RentalPrices extends JFrame {

    private JTextField tipSpatiuField;
    private JTable resultTable;
    private JButton searchButton;

    public RentalPrices() {
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

        searchButton = new JButton("Caută");
        inputPanel.add(tipSpatiuLabel);
        inputPanel.add(tipSpatiuField);
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Zonă pentru afișarea rezultatelor
        resultTable = new JTable();
        resultTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Setarea fontului și dimensiunii textului
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        // Acțiunea la apăsarea butonului
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipSpatiu = tipSpatiuField.getText().trim();
                if (!tipSpatiu.isEmpty()) {
                    searchPricesBySpaceType(tipSpatiu);
                } else {
                    JOptionPane.showMessageDialog(RentalPrices.this, "Vă rugăm să introduceți un tip valid de spațiu.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void searchPricesBySpaceType(String tipSpatiu) {
        String query = """
        SELECT S.zona, O.moneda, MIN(O.pret) AS pret_minim, AVG(O.pret) AS pret_mediu, MAX(O.pret) AS pret_maxim
        FROM Oferta O
        JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
        JOIN Tip T ON S.id_tip = T.id_tip
        WHERE T.denumire = ? AND O.vanzare = 'N'
        GROUP BY S.zona, O.moneda
    """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tipSpatiu);

            try (ResultSet rs = stmt.executeQuery()) {
                DefaultTableModel model = new DefaultTableModel(new String[]{"Zonă", "Moneda", "Preț Minim", "Preț Mediu", "Preț Maxim"}, 0);

                while (rs.next()) {
                    String zona = rs.getString("zona");
                    String moneda = rs.getString("moneda");
                    double pretMinim = rs.getDouble("pret_minim");
                    double pretMediu = rs.getDouble("pret_mediu");
                    double pretMaxim = rs.getDouble("pret_maxim");

                    model.addRow(new Object[]{zona, moneda, pretMinim, pretMediu, pretMaxim});
                }

                resultTable.setModel(model);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la interogarea bazei de date: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RentalPrices frame = new RentalPrices();
            frame.setVisible(true);
        });
    }
}