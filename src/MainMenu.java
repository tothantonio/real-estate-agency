import javax.swing.*;
import java.awt.*;

public class MainMenu {

    public void showMainMenu() {
        // Create the main window
        JFrame frame = new JFrame("Real Estate Agency");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the window on the screen

        // Create a panel for content
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        // Create the main options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(5, 2, 5, 5));

        JButton viewSpacesButton = new JButton("View Spaces");
        JButton saleOffersButton = new JButton("Sale Offers");
        JButton rentalOffersButton = new JButton("Rental Offers");
        JButton spacePairsButton = new JButton("View Space Pairs by Price Difference");
        JButton searchSpacesButton = new JButton("Search Spaces");
        JButton searchSimilarAgentSpacesButton = new JButton("Search Similar Agent Spaces");
        JButton searchPricesBySpaceTypeButton = new JButton("View Spaces by Space Type");
        JButton searchRentalPricesBySpaceTypeButton = new JButton("View Rental Prices by Space Type");
        JButton addAgencyButton = new JButton("Add Agency");
        JButton addTypeButton = new JButton("Add Type");
        JButton addSpaceButton = new JButton("Add Space");
        JButton addOfferButton = new JButton("Add Offer");

        optionsPanel.add(viewSpacesButton);
        optionsPanel.add(saleOffersButton);
        optionsPanel.add(rentalOffersButton);
        optionsPanel.add(spacePairsButton);
        optionsPanel.add(searchSpacesButton);
        optionsPanel.add(searchSimilarAgentSpacesButton);
        optionsPanel.add(searchPricesBySpaceTypeButton);
        optionsPanel.add(searchRentalPricesBySpaceTypeButton);
        optionsPanel.add(addAgencyButton);
        optionsPanel.add(addTypeButton);
        optionsPanel.add(addSpaceButton);
        optionsPanel.add(addOfferButton);

        panel.add(optionsPanel, BorderLayout.CENTER);

        // Create MenuHandler instance
        MenuHandler menuHandler = new MenuHandler(frame);

        // Add action listeners to buttons using MenuHandler
        viewSpacesButton.addActionListener(menuHandler.getViewSpacesActionListener());
        saleOffersButton.addActionListener(menuHandler.getSearchOffersByPriceActionListener());
        rentalOffersButton.addActionListener(menuHandler.getSearchRentalOffersActionListener());
        spacePairsButton.addActionListener(menuHandler.getSearchSpacePairsActionListener());
        searchSpacesButton.addActionListener(menuHandler.getSearchSpacesByCharacteristicsActionListener());
        searchSimilarAgentSpacesButton.addActionListener(menuHandler.getSearchSimilarAgentSpacesActionListener());
        searchPricesBySpaceTypeButton.addActionListener(menuHandler.getSearchPricesBySpaceTypeActionListener());
        searchRentalPricesBySpaceTypeButton.addActionListener(menuHandler.getSearchRentalPricesBySpaceTypeActionListener());
        addAgencyButton.addActionListener(menuHandler.getAddAgencyActionListener());
        addTypeButton.addActionListener(menuHandler.getAddTypeActionListener());
        addSpaceButton.addActionListener(menuHandler.getAddSpaceActionListener());
        addOfferButton.addActionListener(menuHandler.getAddOfferActionListener());

        // Display the main window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.showMainMenu();
        });
    }
}