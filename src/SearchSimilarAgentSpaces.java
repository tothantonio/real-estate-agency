import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SearchSimilarAgentSpaces extends JFrame {

    private JTextField idAgentieField;
    private JTextField idSpatiuField;
    private JTable resultTable;
    private JButton searchButton;

    public SearchSimilarAgentSpaces() {
        setTitle("Search Similar Agent Spaces");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Panou pentru introducerea datelor
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel idAgentieLabel = new JLabel("Enter Agency ID:");
        idAgentieField = new JTextField();

        JLabel idSpatiuLabel = new JLabel("Enter Space ID:");
        idSpatiuField = new JTextField();

        inputPanel.add(idAgentieLabel);
        inputPanel.add(idAgentieField);
        inputPanel.add(idSpatiuLabel);
        inputPanel.add(idSpatiuField);

        searchButton = new JButton("Search");
        inputPanel.add(new JLabel()); // Spacer
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
                    int idAgentie = Integer.parseInt(idAgentieField.getText());
                    int idSpatiu = Integer.parseInt(idSpatiuField.getText());

                    if (idAgentie > 0 && idSpatiu > 0) {
                        searchSimilarSpaces(idAgentie, idSpatiu);
                    } else {
                        JOptionPane.showMessageDialog(SearchSimilarAgentSpaces.this, "Please enter valid positive numbers for IDs.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SearchSimilarAgentSpaces.this, "Please enter valid numbers for Agency ID and Space ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void searchSimilarSpaces(int idAgentie, int idSpatiu) {
        String callProcedure = "{CALL GetAgentiPretSimilar(?, ?)}";

        try (Connection conn = DataBaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(callProcedure)) {

            // Setăm parametrii pentru procedura stocată
            stmt.setInt(1, idAgentie);
            stmt.setInt(2, idSpatiu);

            ResultSet rs = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Agency Name"}, 0);

            // Procesăm rezultatele
            while (rs.next()) {
                String agencyName = rs.getString("nume");
                model.addRow(new Object[]{agencyName});
            }

            resultTable.setModel(model);

            // Set preferred widths for each column
            TableColumnModel columnModel = resultTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(200); // Agency Name

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error querying the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SearchSimilarAgentSpaces frame = new SearchSimilarAgentSpaces();
            frame.setVisible(true);
        });
    }
}