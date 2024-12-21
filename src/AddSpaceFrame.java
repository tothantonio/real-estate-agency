import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSpaceFrame extends JFrame {

    private JTextField idField;
    private JTextField addressField;
    private JTextField zoneField;
    private JTextField areaField;
    private JTextField typeIdField;
    private JButton addButton;

    public AddSpaceFrame() {
        setTitle("Add Space");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        JLabel zoneLabel = new JLabel("Zone:");
        zoneField = new JTextField();
        JLabel areaLabel = new JLabel("Area:");
        areaField = new JTextField();
        JLabel typeIdLabel = new JLabel("Type ID:");
        typeIdField = new JTextField();

        addButton = new JButton("Add");

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(zoneLabel);
        inputPanel.add(zoneField);
        inputPanel.add(areaLabel);
        inputPanel.add(areaField);
        inputPanel.add(typeIdLabel);
        inputPanel.add(typeIdField);
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String address = addressField.getText();
                int zone = Integer.parseInt(zoneField.getText());
                int area = Integer.parseInt(areaField.getText());
                int typeId = Integer.parseInt(typeIdField.getText());
                if (AddSpace.addSpace(id, address, zone, area, typeId)) {
                    JOptionPane.showMessageDialog(AddSpaceFrame.this, "Space added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddSpaceFrame.this, "Failed to add space.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}