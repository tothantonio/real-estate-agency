import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddOfferFrame extends JFrame {

    private JTextField agencyIdField;
    private JTextField spaceIdField;
    private JTextField saleField;
    private JTextField priceField;
    private JTextField currencyField;
    private JButton addButton;

    public AddOfferFrame() {
        setTitle("Add Offer");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5));

        JLabel agencyIdLabel = new JLabel("Agency ID:");
        agencyIdField = new JTextField();
        JLabel spaceIdLabel = new JLabel("Space ID:");
        spaceIdField = new JTextField();
        JLabel saleLabel = new JLabel("Sale (D/N):");
        saleField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        JLabel currencyLabel = new JLabel("Currency:");
        currencyField = new JTextField();

        addButton = new JButton("Add");

        inputPanel.add(agencyIdLabel);
        inputPanel.add(agencyIdField);
        inputPanel.add(spaceIdLabel);
        inputPanel.add(spaceIdField);
        inputPanel.add(saleLabel);
        inputPanel.add(saleField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(currencyLabel);
        inputPanel.add(currencyField);
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int agencyId = Integer.parseInt(agencyIdField.getText());
                int spaceId = Integer.parseInt(spaceIdField.getText());
                char sale = saleField.getText().charAt(0);
                int price = Integer.parseInt(priceField.getText());
                String currency = currencyField.getText();
                if (AddOffer.addOffer(agencyId, spaceId, sale, price, currency)) {
                    JOptionPane.showMessageDialog(AddOfferFrame.this, "Offer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddOfferFrame.this, "Failed to add offer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}