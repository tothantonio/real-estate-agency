import javax.swing.*;
import java.awt.*;

public class MainMenu {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crearea ferestrei principale
            JFrame frame = new JFrame("Real Estate Agency");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null); // Centrează fereastra pe ecran

            // Crearea barei de meniu
            JMenuBar menuBar = new JMenuBar();
            menuBar.setBackground(Color.LIGHT_GRAY);

            // Crearea meniului cu opțiuni
            JMenu menu = new JMenu("Options");
            menu.setFont(new Font("Arial", Font.BOLD, 14));
            menu.setForeground(Color.DARK_GRAY);

            // Crearea elementelor de meniu
            JMenuItem viewSpatiiMenuItem = new JMenuItem("View Spaces");
            JMenuItem searchOffersByPriceMenuItem = new JMenuItem("Sale Offers");
            JMenuItem searchRentalOffersMenuItem = new JMenuItem("Rental Offers");
            JMenuItem searchSpacePairsMenuItem = new JMenuItem("Space Pairs by Price Difference");
            JMenuItem searchSpacesByCharacteristicsMenuItem = new JMenuItem("Search Spaces");
            JMenuItem searchSimilarAgentSpacesMenuItem = new JMenuItem("Search Similar Spaces");
            JMenuItem searchPricesBySpaceTypeMenuItem = new JMenuItem("Search Sale Prices");
            JMenuItem searchRentalPricesBySpaceTypeMenuItem = new JMenuItem("Search Rental Prices");
            JMenuItem exitMenuItem = new JMenuItem("Exit");

            // Adăugarea elementelor de meniu
            menu.add(viewSpatiiMenuItem);
            menu.add(searchOffersByPriceMenuItem);
            menu.add(searchRentalOffersMenuItem);
            menu.add(searchSpacePairsMenuItem);
            menu.add(searchSpacesByCharacteristicsMenuItem);
            menu.add(searchSimilarAgentSpacesMenuItem);
            menu.add(searchPricesBySpaceTypeMenuItem);
            menu.add(searchRentalPricesBySpaceTypeMenuItem);
            menu.addSeparator();
            menu.add(exitMenuItem);

            // Adăugarea meniului în bara de meniu
            menuBar.add(menu);

            // Crearea toolbar-ului
            JToolBar toolBar = new JToolBar();
            toolBar.setRollover(true);
            toolBar.setFloatable(false);

            // Crearea butoanelor pentru toolbar
            JButton viewSpacesButton = new JButton("View Spaces");
            JButton offersByPriceButton = new JButton("Sale Offers");
            JButton rentalOffersButton = new JButton("Rental Offers");
            JButton spacePairsButton = new JButton("Space Pairs by Price Difference");
            JButton searchSpacesByCharacteristicsButton = new JButton("Search Spaces");
            JButton searchSimilarAgentSpacesButton = new JButton("Search Similar Spaces");
            JButton searchPricesBySpaceTypeButton = new JButton("Search Sale Prices");
            JButton searchRentalPricesBySpaceTypeButton = new JButton("Search Rental Prices");

            // Adăugarea butoanelor în toolbar
            toolBar.add(viewSpacesButton);
            toolBar.add(offersByPriceButton);
            toolBar.add(rentalOffersButton);
            toolBar.add(spacePairsButton);
            toolBar.add(searchSpacesByCharacteristicsButton);
            toolBar.add(searchSimilarAgentSpacesButton);
            toolBar.add(searchPricesBySpaceTypeButton);
            toolBar.add(searchRentalPricesBySpaceTypeButton);

            // Adăugarea componentei în fereastră
            frame.setJMenuBar(menuBar);
            frame.add(toolBar, BorderLayout.NORTH);

            // Crearea unui panel pentru conținut
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            frame.add(panel);

            // Crearea handler-ului pentru evenimente
            MenuHandler menuHandler = new MenuHandler(frame);

            // Asocierea evenimentelor de la meniuri cu handler-ul
            viewSpatiiMenuItem.addActionListener(menuHandler.getViewSpacesActionListener());
            searchOffersByPriceMenuItem.addActionListener(menuHandler.getSearchOffersByPriceActionListener());
            searchRentalOffersMenuItem.addActionListener(menuHandler.getSearchRentalOffersActionListener());
            searchSpacePairsMenuItem.addActionListener(menuHandler.getSearchSpacePairsActionListener());
            searchSpacesByCharacteristicsMenuItem.addActionListener(menuHandler.getSearchSpacesByCharacteristicsActionListener());
            searchSimilarAgentSpacesMenuItem.addActionListener(menuHandler.getSearchSimilarAgentSpacesActionListener());
            searchPricesBySpaceTypeMenuItem.addActionListener(menuHandler.getSearchPricesBySpaceTypeActionListener());
            searchRentalPricesBySpaceTypeMenuItem.addActionListener(menuHandler.getSearchRentalPricesBySpaceTypeActionListener());
            exitMenuItem.addActionListener(menuHandler.getExitActionListener());

            // Asocierea evenimentelor de la butoane cu handler-ul
            viewSpacesButton.addActionListener(menuHandler.getViewSpacesActionListener());
            offersByPriceButton.addActionListener(menuHandler.getSearchOffersByPriceActionListener());
            rentalOffersButton.addActionListener(menuHandler.getSearchRentalOffersActionListener());
            spacePairsButton.addActionListener(menuHandler.getSearchSpacePairsActionListener());
            searchSpacesByCharacteristicsButton.addActionListener(menuHandler.getSearchSpacesByCharacteristicsActionListener());
            searchSimilarAgentSpacesButton.addActionListener(menuHandler.getSearchSimilarAgentSpacesActionListener());
            searchPricesBySpaceTypeButton.addActionListener(menuHandler.getSearchPricesBySpaceTypeActionListener());
            searchRentalPricesBySpaceTypeButton.addActionListener(menuHandler.getSearchRentalPricesBySpaceTypeActionListener());

            // Vizualizarea ferestrei principale
            frame.setVisible(true);
        });
    }
}