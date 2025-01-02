import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchSimilarAgentSpaces extends JFrame {

    private JTextField idAgentieField;
    private JTextField idSpatiuField;
    private JTextArea resultArea;
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
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
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
                        resultArea.setText("Please enter valid positive numbers for IDs.");
                    }
                } catch (NumberFormatException ex) {
                    resultArea.setText("Please enter valid numbers for Agency ID and Space ID.");
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
            StringBuilder result = new StringBuilder();

            // Procesăm rezultatele
            while (rs.next()) {
                result.append("Agency Name: ").append(rs.getString("nume")).append("\n");
            }

            if (result.length() == 0) {
                result.append("No matching agencies found.");
            }

            resultArea.setText(result.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error querying the database: " + e.getMessage());
        }
    }

}
