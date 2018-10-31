package ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import simulation.KioskReportSimulator;
import simulation.LocalServer;
import ui.reactions.AddingKioskListener;

import java.util.concurrent.TimeUnit;

import static javafx.scene.control.SpinnerValueFactory.*;

public class AddEditKioskController {

    @FXML
    TextField textServerID;
    @FXML
    TextField textMacAddress;
    @FXML
    TextField textHotelName;
    @FXML
    Button buttonClose;
    @FXML
    Button buttonAddEditKiosk;
    @FXML
    Spinner<Integer> spinnerPeriod;
    @FXML
    ComboBox<TimeUnit> comboUnits;


    @FXML
    private void initialize() {
        buttonClose.setOnAction(event -> closeWindow());
        setValidationMessages(textMacAddress);
        setValidationMessages(textHotelName);
        comboUnits.setItems(FXCollections.observableArrayList(TimeUnit.values()));
        comboUnits.getSelectionModel().select(3);
        SpinnerValueFactory<Integer> valueFactory = new IntegerSpinnerValueFactory(0, Integer.MAX_VALUE-1, 10);
        spinnerPeriod.setValueFactory(valueFactory);

    }

    private void setValidationMessages(final TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                textField.setPromptText("No Input, please inter valid text");
            }
        });
    }

    private boolean isInputValid() {
        return !textMacAddress.getText().isEmpty() &&
                !textHotelName.getText().isEmpty() &&
                !comboUnits.getSelectionModel().isEmpty();

    }

    private void alterTextNOTValid() {
        AddEditServerController.alterInputNOTValid();
    }



    public void initAddingKiosk(LocalServer selectedServer, AddingKioskListener listener) {
        textServerID.setText(selectedServer.getServerIP());
        buttonAddEditKiosk.setOnAction(event -> applyAddKiosk(selectedServer,listener));
    }

    private void applyAddKiosk(LocalServer selectedServer, AddingKioskListener listener) {
        if (isInputValid()) {
            String macAddress = textMacAddress.getText();
            String hotelName = textHotelName.getText();
            long period = spinnerPeriod.getValue();
            TimeUnit unit = comboUnits.getSelectionModel().getSelectedItem();

            KioskReportSimulator KioskSimulator = selectedServer.addKioskSimulator(macAddress, hotelName, period, unit);
            listener.notifyAddingKiosk(KioskSimulator);
            closeWindow();
        }
        else {
            alterTextNOTValid();
        }
    }

    public void initEditingKiosk(final KioskReportSimulator simulator) {
        buttonAddEditKiosk.setText("Save Changes");
        textServerID.setText(simulator.getServerID());
        textMacAddress.setText(simulator.getMacAddress());
        textHotelName.setText(simulator.getHotelName());
        int period = (int) simulator.getPeriod();
        spinnerPeriod.setValueFactory(new IntegerSpinnerValueFactory(0, Integer.MAX_VALUE-1, period));
        comboUnits.getSelectionModel().select(simulator.getUnit());
        buttonAddEditKiosk.setOnAction(event -> applyEditKiosk(simulator));
    }

    private void applyEditKiosk(KioskReportSimulator simulator) {
        if (isInputValid()) {
            String macAddress = textMacAddress.getText();
            String hotelName = textHotelName.getText();
            long period = spinnerPeriod.getValue();
            TimeUnit unit = comboUnits.getSelectionModel().getSelectedItem();
            simulator.setMacAddress(macAddress);
            simulator.setHotelName(hotelName);
            simulator.setPeriod(period);
            simulator.setUnit(unit);
            closeWindow();
        }
        else {
            alterTextNOTValid();
        }

    }


    private void closeWindow() {
        ((Stage) buttonClose.getScene().getWindow()).close();
    }
}
