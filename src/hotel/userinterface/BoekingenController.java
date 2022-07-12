package hotel.userinterface;

import hotel.model.Hotel;
import hotel.model.Kamer;
import hotel.model.KamerType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class BoekingenController {
    @FXML
    private TextField naamInput, adresInput;
    @FXML
    private DatePicker aankomstInput, vertrekInput;
    @FXML
    private ComboBox kamerTypeInput;
    @FXML
    private Label melding;

    public void resetScherm() {
        naamInput.clear();
        adresInput.clear();
        aankomstInput.getEditor().clear();
        vertrekInput.getEditor().clear();
        kamerTypeInput.getSelectionModel().clearSelection();
    }

    public void boekButton() { //hulp gekregen van Bram
        LocalDate nu = LocalDate.now();
        if (naamInput.getText() != null && adresInput.getText() != null && aankomstInput.getValue() != null && vertrekInput.getValue() != null && kamerTypeInput.getValue() != null) {
            if (aankomstInput.getValue().isAfter(nu) && vertrekInput.getValue().isAfter(nu) && vertrekInput.getValue().isAfter(aankomstInput.getValue())) {
                List<KamerType> allekamers = Hotel.getHotel().getKamerTypen();
                for (KamerType kamerType : allekamers) {
                    try {
                        if (Objects.equals(kamerType.toString(), kamerTypeInput.getValue().toString())) {
                            Hotel.getHotel().voegBoekingToe(aankomstInput.getValue(), vertrekInput.getValue(), naamInput.getText(), adresInput.getText(), kamerType);
                            System.out.println("kamer gemaakt");
                            melding.setText("Een kamer is geboekt!");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("geen kamer beschikbaar");
                        melding.setText("Er is geen kamer beschikbaar!");
                    }
                }

            } else {
                System.out.println("geen geldig invoer");
                melding.setText("Geen geldige invoer!");
            }
        }else {
            System.out.println("er mist iets");
            melding.setText("Er missen bepaalde gegevens!");
        }
    }

        public void initialize() {
            ObservableList<String> kamers = FXCollections.observableArrayList();
            List<KamerType> alleKamers = Hotel.getHotel().getKamerTypen();
            for (KamerType kamerType : alleKamers) {
                kamers.add(String.valueOf(kamerType));

            }
            kamerTypeInput.setItems(kamers);

        }

}
