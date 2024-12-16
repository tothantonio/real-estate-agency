import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuHandler {

    private JFrame frame;

    public MenuHandler(JFrame frame) {
        this.frame = frame;
    }

    public ActionListener getViewSpacesActionListener() {
        return e -> {
            SpatiiByAdresa spatiiFrame = new SpatiiByAdresa();
            spatiiFrame.setVisible(true);
        };
    }

    public ActionListener getSearchOffersByPriceActionListener() {
        return e -> {
            OferteVanzare app = new OferteVanzare();
            app.setVisible(true);
        };
    }

    public ActionListener getSearchRentalOffersActionListener() {
        return e -> {
            RentalOffersSearchFrame app = new RentalOffersSearchFrame();
            app.setVisible(true);
        };
    }

    public ActionListener getSearchSpacePairsActionListener() {
        return e -> {
            // Deschiderea ferestrei pentru căutarea perechilor de spații
            SpacePairsSearchFrame app = new SpacePairsSearchFrame();
            app.setVisible(true);
        };
    }

    // Adăugarea unui ActionListener pentru căutarea spațiilor cu caracteristici
    public ActionListener getSearchSpacesByCharacteristicsActionListener() {
        return e -> {
            // Aici vei deschide o fereastră pentru a căuta spații cu anumite caracteristici
            SearchSpacesByCharacteristicsFrame app = new SearchSpacesByCharacteristicsFrame();
            app.setVisible(true);
        };
    }

    public ActionListener getExitActionListener() {
        return e -> System.exit(0);
    }
}
