import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAgencyFrame extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton addButton;

    public AddAgencyFrame() {
        setTitle("Add Agency");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 5, 5));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();

        addButton = new JButton("Add");

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String phone = phoneField.getText();
                if (AddAgency.addAgency(id, name, phone)) {
                    JOptionPane.showMessageDialog(AddAgencyFrame.this, "Agency added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddAgencyFrame.this, "Failed to add agency.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}