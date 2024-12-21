import javax.swing.*;
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
            OferteInchiriere app = new OferteInchiriere();
            app.setVisible(true);
        };
    }

    public ActionListener getSearchSpacePairsActionListener() {
        return e -> {
            SpacePairsSearch app = new SpacePairsSearch();
            app.setVisible(true);
        };
    }

    public ActionListener getSearchSpacesByCharacteristicsActionListener() {
        return e -> {
            SearchSpacesByCharacteristics app = new SearchSpacesByCharacteristics();
            app.setVisible(true);
        };
    }

    public ActionListener getSearchSimilarAgentSpacesActionListener() {
        return e -> {
            SearchSimilarAgentSpaces app = new SearchSimilarAgentSpaces();
            app.setVisible(true);
        };
    }

    public ActionListener getSearchPricesBySpaceTypeActionListener() {
        return e -> {
            PreturiSpatiuPerMoneda app = new PreturiSpatiuPerMoneda();
            app.setVisible(true);
        };
    }

    public ActionListener getSearchRentalPricesBySpaceTypeActionListener() {
        return e -> {
            RentalPrices app = new RentalPrices();
            app.setVisible(true);
        };
    }

    public ActionListener getAddAgencyActionListener() {
        return e -> {
            AddAgencyFrame addAgencyFrame = new AddAgencyFrame();
            addAgencyFrame.setVisible(true);
        };
    }

    public ActionListener getAddTypeActionListener() {
        return e -> {
            AddTypeFrame addTypeFrame = new AddTypeFrame();
            addTypeFrame.setVisible(true);
        };
    }

    public ActionListener getAddSpaceActionListener() {
        return e -> {
            AddSpaceFrame addSpaceFrame = new AddSpaceFrame();
            addSpaceFrame.setVisible(true);
        };
    }

    public ActionListener getAddOfferActionListener() {
        return e -> {
            AddOfferFrame addOfferFrame = new AddOfferFrame();
            addOfferFrame.setVisible(true);
        };
    }

    public ActionListener getExitActionListener() {
        return e -> System.exit(0);
    }
}