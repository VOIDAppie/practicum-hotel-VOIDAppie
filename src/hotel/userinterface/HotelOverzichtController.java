package hotel.userinterface;

import hotel.model.Boeking;
import hotel.model.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HotelOverzichtController {
    @FXML private Label hotelnaamLabel;
    @FXML private ListView boekingenListView;
    @FXML private DatePicker overzichtDatePicker;

    private Hotel hotel = Hotel.getHotel();

    public void initialize() {
        hotelnaamLabel.setText("Boekingen hotel " + hotel.getNaam());
        overzichtDatePicker.setValue(LocalDate.now());
        toonBoekingen();
    }

    public void toonVorigeDag(ActionEvent actionEvent) {
        LocalDate dagEerder = overzichtDatePicker.getValue().minusDays(1);
        overzichtDatePicker.setValue(dagEerder);
    }

    public void toonVolgendeDag(ActionEvent actionEvent) {
        LocalDate dagLater = overzichtDatePicker.getValue().plusDays(1);
        overzichtDatePicker.setValue(dagLater);
    }

    public void nieuweBoeking(ActionEvent actionEvent) throws IOException {
        // Maak in je project een nieuwe FXML-pagina om boekingen te kunnen invoeren
        // Open de nieuwe pagina in deze methode
        // Zorg dat de gebruiker ondertussen geen gebruik kan maken van de HotelOverzicht-pagina
        // Update na sluiten van de nieuwe pagina het boekingen-overzicht
        FXMLLoader nieuweBoeking = new FXMLLoader(getClass().getResource("Boekingen.fxml"));
        Parent root = nieuweBoeking.load();
        Stage stageTweede = new Stage();
        stageTweede.initModality(Modality.APPLICATION_MODAL);
        stageTweede.setTitle("Boeking");
        stageTweede.setScene(new Scene(root));
        stageTweede.show();

    }

    public void toonBoekingen() {
        ObservableList<String> boekingen = FXCollections.observableArrayList();

        for(Boeking perBoeking: hotel.getBoekingen()) {
            if(overzichtDatePicker.getValue().plusDays(1).isAfter(perBoeking.getAankomstDatum()) && overzichtDatePicker.getValue().minusDays(1).isBefore(perBoeking.getVertrekDatum())) {
                boekingen.add(String.format("klantnaam %s kamernummer %s aankomstdatum %s vertrekdatum %s", perBoeking.getBoeker(), perBoeking.getKamer(), perBoeking.getAankomstDatum(), perBoeking.getVertrekDatum()));

            }
        }

        // Vraag de boekingen op bij het Hotel-object.
        // Voeg voor elke boeking in nette tekst (string) toe aan de boekingen-lijst.

        boekingenListView.setItems(boekingen);
    }
}