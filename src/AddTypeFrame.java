import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTypeFrame extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField characteristicsField;
    private JButton addButton;

    public AddTypeFrame() {
        setTitle("Add Type");
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
        JLabel characteristicsLabel = new JLabel("Characteristics:");
        characteristicsField = new JTextField();

        addButton = new JButton("Add");

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(characteristicsLabel);
        inputPanel.add(characteristicsField);
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String characteristics = characteristicsField.getText();
                if (AddType.addType(id, name, characteristics)) {
                    JOptionPane.showMessageDialog(AddTypeFrame.this, "Type added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddTypeFrame.this, "Failed to add type.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}