package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import serverconfiguration.ServerConfiguration;
import simulation.LocalServer;
import simulation.SimulationApp;
import ui.reactions.AddingServerListener;

public class AddEditServerController {

    @FXML
    TextField textServerName;
    @FXML
    TextField textServerDNS;
    @FXML
    TextField textServerIP;
    @FXML
    TextField textServerPort;
    @FXML
    TextField textServerRoute;
    @FXML
    Button buttonClose;
    @FXML
    Button buttonAddEditServer;


    @FXML
    private void initialize() {
        buttonClose.setOnAction(event -> closeWindow());

        setValidationMessages(textServerName);
        setValidationMessages(textServerDNS);
        setValidationMessages(textServerIP);
        setValidationMessages(textServerPort);
        setValidationMessages(textServerRoute);
    }

    private void setValidationMessages(final TextField textField) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                textField.setPromptText("No Input, please inter valid text");
            }
        });
    }

    private boolean isInputValid() {
        return !textServerName.getText().isEmpty() &&
                !textServerDNS.getText().isEmpty() &&
                !textServerIP.getText().isEmpty() &&
                !textServerPort.getText().isEmpty() &&
                !textServerRoute.getText().isEmpty();
    }


    private void alterTextNOTValid() {
        AddEditServerController.alterInputNOTValid();
    }


    private void closeWindow() {
        ((Stage) buttonClose.getScene().getWindow()).close();
    }

    public void initAddingServer(final SimulationApp app, final AddingServerListener listener) {
        buttonAddEditServer.setOnAction(event -> applyAddServer(app,listener));
    }

    private void applyAddServer(final SimulationApp app, final AddingServerListener listener) {

        if (isInputValid()){
            String name = textServerName.getText();
            String DNS = textServerDNS.getText();
            String IP = textServerIP.getText();
            String port = textServerPort.getText();
            String route = textServerRoute.getText();

            if (app.isValiadName(name)){
                ServerConfiguration config = new ServerConfiguration(IP, DNS, port, route);
                LocalServer localServer = new LocalServer(name,config);
                app.addServer(localServer);
                listener.notifyAddingServer(localServer);
                closeWindow();
            }
            else {
                alterNameAlreadyUsed();
            }

        }
        else {
            alterTextNOTValid();
        }
    }


    public void initEditingServer(final LocalServer server) {
        buttonAddEditServer.setText("Save Changes");
        textServerName.setText(server.getName());
        textServerDNS.setText(server.getServerDNS());
        textServerIP.setText(server.getServerIP());
        textServerPort.setText(server.getServerPort());
        textServerRoute.setText(server.getServerRoute());
        textServerName.setDisable(true);
        buttonAddEditServer.setOnAction(event -> applyEditServer(server));
    }

    private void applyEditServer(final LocalServer server) {

        if (isInputValid()) {
            String DNS = textServerDNS.getText();
            String IP = textServerIP.getText();
            String port = textServerPort.getText();
            String route = textServerRoute.getText();
            ServerConfiguration config = new ServerConfiguration(IP, DNS, port, route);
            server.setServerConfig(config);
            closeWindow();
        }
        else {
            alterTextNOTValid();
        }
    }


    static void alterInputNOTValid() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Messing Fields");
        alert.setHeaderText("There are messing fields");
        alert.setContentText("Please make sure that all the fields are filled!");
        alert.showAndWait();
    }
    private static void alterNameAlreadyUsed() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Used name");
        alert.setHeaderText("This server name is already used");
        alert.setContentText("Please choose another server name");
        alert.showAndWait();
    }
}
