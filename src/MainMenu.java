import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    public static void main(String[] args) {
        // Apelarea metodei pentru a lansa fereastra principală
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Real Estate Agency");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null); // Center the window

            // Crearea unui meniu mai detaliat
            JMenuBar menuBar = new JMenuBar();
            menuBar.setBackground(Color.LIGHT_GRAY); // Schimbăm culoarea de fundal a meniului

            // Meniul principal "Options"
            JMenu menu = new JMenu("Options");
            menu.setFont(new Font("Arial", Font.BOLD, 14));
            menu.setForeground(Color.DARK_GRAY);

            // Submeniuri
            JMenuItem viewSpatiiMenuItem = new JMenuItem("View spaces"); // Căutare spații
            JMenuItem searchOffersByPriceMenuItem = new JMenuItem("Search Offers by Price");
            JMenuItem searchRentalOffersMenuItem = new JMenuItem("Search Rental Offers by Price");
            JMenuItem exitMenuItem = new JMenuItem("Exit");

            // Adăugăm opțiunile în meniu
            menu.add(viewSpatiiMenuItem);
            menu.add(searchOffersByPriceMenuItem);
            menu.add(searchRentalOffersMenuItem);  // Noua opțiune pentru căutarea ofertelor de închiriere
            menu.addSeparator(); // Linie separatoare
            menu.add(exitMenuItem); // Meniu de ieșire

            // Adăugăm meniul în bara de meniu
            menuBar.add(menu);

            // Toolbar cu butoane
            JToolBar toolBar = new JToolBar();
            toolBar.setRollover(true); // Adăugăm un efect de hover pentru toolbar
            toolBar.setFloatable(false); // Toolbar-ul nu poate fi deplasat

            JButton viewSpacesButton = new JButton("View Spaces");
            JButton offersByPriceButton = new JButton("Search Offers by Price");
            JButton rentalOffersButton = new JButton("Search Rental Offers");

            toolBar.add(viewSpacesButton);
            toolBar.add(offersByPriceButton);
            toolBar.add(rentalOffersButton);  // Butonul pentru căutarea ofertelor de închiriere

            // Setăm meniul și toolbar-ul pe fereastra principală
            frame.setJMenuBar(menuBar);
            frame.add(toolBar, BorderLayout.NORTH);

            // Panou principal pentru conținut
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            frame.add(panel);

            // Adăugăm un handler pentru meniuri
            MenuHandler menuHandler = new MenuHandler(frame);

            // Setăm acțiunile pentru opțiunile din meniu
            viewSpatiiMenuItem.addActionListener(menuHandler.getViewSpacesActionListener());
            searchOffersByPriceMenuItem.addActionListener(menuHandler.getSearchOffersByPriceActionListener());
            searchRentalOffersMenuItem.addActionListener(menuHandler.getSearchRentalOffersActionListener());  // Handler pentru căutarea ofertelor de închiriere
            exitMenuItem.addActionListener(menuHandler.getExitActionListener());

            // Setăm acțiunile pentru butoanele din toolbar
            viewSpacesButton.addActionListener(menuHandler.getViewSpacesActionListener());
            offersByPriceButton.addActionListener(menuHandler.getSearchOffersByPriceActionListener());
            rentalOffersButton.addActionListener(menuHandler.getSearchRentalOffersActionListener());  // Handler pentru butonul de căutare oferte de închiriere

            // Vizualizarea ferestrei
            frame.setVisible(true);
        });
    }
}
