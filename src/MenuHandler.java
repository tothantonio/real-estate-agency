import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuHandler {

    private JFrame frame;

    public MenuHandler(JFrame frame) {
        this.frame = frame;
    }

    public ActionListener getViewSpacesActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschiderea ferestrei pentru căutarea spațiilor
                SpatiiByAdresa spatiiFrame = new SpatiiByAdresa();
                spatiiFrame.setVisible(true);
            }
        };
    }

    public ActionListener getSearchOffersByPriceActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschide fereastra pentru căutarea ofertelor după preț
                OferteVanzare app = new OferteVanzare();
                app.setVisible(true);
            }
        };
    }

    public ActionListener getSearchRentalOffersActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschide fereastra pentru căutarea ofertelor de închiriere
                RentalOffersSearchFrame app = new RentalOffersSearchFrame();
                app.setVisible(true);
            }
        };
    }

    public ActionListener getExitActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Iesim din aplicatie
                System.exit(0);
            }
        };
    }
}
