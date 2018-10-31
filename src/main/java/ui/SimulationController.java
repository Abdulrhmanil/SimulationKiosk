package ui;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kiosk_problems.ProblemColor;
import kiosk_problems.Problems;
import org.controlsfx.control.ToggleSwitch;
import simulation.KioskDevice;
import simulation.KioskReportSimulator;
import simulation.LocalServer;
import simulation.SimulationApp;
import ui.reactions.AddingKioskListener;
import ui.reactions.AddingServerListener;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class SimulationController implements AddingServerListener, AddingKioskListener {

    /** */
    final private SimulationApp app = Main.getApp();
    private boolean isSendingReports = false;


    //section 1:
    @FXML
    ListView<LocalServer> serversList;
    @FXML
    Label labelServerName;
    @FXML
    Label labelServerDNS;
    @FXML
    Label labelServerIP;
    @FXML
    Label labelServerPort;
    @FXML
    Label labelServerRoute;
    @FXML
    Circle serverStatus;
    @FXML
    Button buttonAddServer;
    @FXML
    Button buttonEditServer;
    @FXML
    Button buttonRemoveServer;



    //section 2:
    @FXML
    ListView<KioskReportSimulator> kiosksList;
    @FXML
    Label labelServerID;
    @FXML
    Label labelMacAddress;
    @FXML
    Label labelHotelName;
    @FXML
    Label labelServerInterval;
    @FXML
    Circle kioskStatus;
    @FXML
    Button buttonAddKiosk;
    @FXML
    Button buttonEditKiosk;
    @FXML
    Button buttonRemoveKiosk;

    //section 3:

    @FXML
    ListView<KioskDevice> devicesList;
    @FXML
    ListView<Problems> problemsList;
    @FXML
    Label labelDevice;
    @FXML
    Label labelProblemCode;
    @FXML
    Label labelProblemDescription;
    @FXML
    Label labelProblemType;
    @FXML
    Label labelProblemSolution;
    @FXML
    Circle deviceStatus;
    @FXML
    GridPane gridPane;
    @FXML
    Button buttonClose;
    @FXML
    Button buttonStop;
    @FXML
    Button buttonStart;


    ToggleSwitch toggleSwitch;


    @FXML
    private void initialize() {



        initializeToggleSwitch();

        serverStatus.setVisible(false);
        kioskStatus.setVisible(false);
        deviceStatus.setVisible(false);
        toggleSwitch.setVisible(false);

        serversList.setItems(FXCollections.observableArrayList(app.getServers()));

        serversList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    problemsList.getItems().clear();
                    devicesList.getItems().clear();
                    kiosksList.getItems().clear();

                    if (newValue != null) {
                        buttonRemoveServer.setDisable(false);
                        buttonEditServer.setDisable(false);
                        buttonAddKiosk.setDisable(false);

                        labelServerName.setText(newValue.getName());
                        labelServerDNS.setText(newValue.getServerDNS());
                        labelServerIP.setText(newValue.getServerIP());
                        labelServerPort.setText(newValue.getServerPort());
                        labelServerRoute.setText(newValue.getServerRoute());

                        serverStatus.setVisible(true);
                        if (newValue.isAllKiosksGood()){
                            serverStatus.setFill(Color.GREEN);
                        }
                        else {
                            serverStatus.setFill(newValue.getAllKiosksColor().getColor());
                        }

                        kiosksList.setItems(FXCollections.observableArrayList(newValue.getKioskSimulators()));
                    }
                    else {
                        buttonRemoveServer.setDisable(true);
                        buttonEditServer.setDisable(true);
                        buttonAddKiosk.setDisable(true);
                        labelServerName.setText("");
                        labelServerDNS.setText("");
                        labelServerIP.setText("");
                        labelServerPort.setText("");
                        labelServerRoute.setText("");
                        serverStatus.setVisible(false);
                    }
                });


        kiosksList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    problemsList.getItems().clear();
                    devicesList.getItems().clear();

                    if (newValue != null) {
                        buttonRemoveKiosk.setDisable(false);
                        buttonEditKiosk.setDisable(false);
                        labelServerID.setText(newValue.getServerID());
                        labelMacAddress.setText(newValue.getMacAddress());
                        labelHotelName.setText(newValue.getHotelName());
                        labelServerInterval.setText(newValue.getInterval());
                        kioskStatus.setVisible(true);
                        if (newValue.isGood()){
                            kioskStatus.setFill(Color.GREEN);
                        }
                        else {
                            kioskStatus.setFill(newValue.getKioskColor().getColor());
                        }
                        devicesList.setItems(FXCollections.observableArrayList(newValue.getKioskDevices()));
                    }
                    else {
                        buttonRemoveKiosk.setDisable(true);
                        buttonEditKiosk.setDisable(true);
                        labelServerID.setText("");
                        labelMacAddress.setText("");
                        labelHotelName.setText("");
                        labelServerInterval.setText("");
                        kioskStatus.setVisible(false);
                    }
                });


        devicesList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    problemsList.getItems().clear();

                    if (newValue != null) {
                        labelDevice.setText(newValue.getDevice().toString());
                        deviceStatus.setVisible(true);

                        KioskReportSimulator selectedKiosk = kiosksList.getSelectionModel().getSelectedItem();
                        List<String> problems = newValue.getProblems().stream().map(Problems::getCode).collect(Collectors.toList());
                        if (!selectedKiosk.isProblemExist(problems)){
                            deviceStatus.setFill(Color.GREEN);
                        }
                        else {

                            ProblemColor color = ProblemColor.YELLOW;
                            for (Problems problem : newValue.getProblems()) {
                                if (selectedKiosk.isProblemExist(problem.getCode()) &&
                                        problem.getProblemColor().equals(ProblemColor.RED)) {
                                    color = ProblemColor.RED;
                                }

                            }
                            deviceStatus.setFill(color.getColor());
                        }

                        problemsList.setItems(FXCollections.observableArrayList(newValue.getProblems()));
                    }
                    else {
                        labelDevice.setText("");
                        deviceStatus.setVisible(false);
                    }
                });

        problemsList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        labelProblemCode.setText(newValue.getCode());
                        labelProblemDescription.setText(newValue.getDescription());
                        labelProblemType.setText(newValue.getProblemType().toString());
                        labelProblemSolution.setText(newValue.getSolution());

                        toggleSwitch.setVisible(true);
                        KioskReportSimulator selectedKiosk = kiosksList.getSelectionModel().getSelectedItem();
                        if (selectedKiosk.isProblemExist(newValue.getCode())){
                            toggleSwitch.setText("ON");
                            toggleSwitch.setSelected(true);
                        }
                        else {
                            toggleSwitch.setText("OFF");
                            toggleSwitch.setSelected(false);
                        }

                        toggleSwitch.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                final KioskReportSimulator selectedKiosk = kiosksList.getSelectionModel().getSelectedItem();
                                if (toggleSwitch.isSelected()) {
                                    selectedKiosk.addKioskExceptions(newValue.getCode(),newValue.getProblemColor().toString());
                                    refreshSelection();
                                }
                                else {
                                    selectedKiosk.removeKioskExceptions(newValue.getCode());
                                    refreshSelection();
                                }
                            }
                        });
                    }
                    else {
                        labelProblemCode.setText("");
                        labelProblemDescription.setText("");
                        labelProblemType.setText("");
                        labelProblemSolution.setText("");
                        toggleSwitch.setVisible(false);
                    }
                });

        buttonAddServer.setOnAction(event -> showAddServerScreen());
        buttonEditServer.setOnAction(event -> showEditServerScreen());
        buttonRemoveServer.setOnAction(event -> showRemoveServerScreen());

        buttonAddKiosk.setOnAction(event -> showAddKioskScreen());
        buttonEditKiosk.setOnAction(event -> showEditKioskScreen());
        buttonRemoveKiosk.setOnAction(event -> showRemoveKioskScreen());

        buttonClose.setOnAction(event -> closeWindow());

        this.isSendingReports = false;
        buttonStop.setDisable(true);

        buttonStart.setOnAction(event -> startSendingReports());
        buttonStop.setOnAction(event -> stopSendingReports());


    }

    private void initializeToggleSwitch() {
        this.toggleSwitch = new ToggleSwitch("OFF");
        this.gridPane.add(toggleSwitch,1,10);
    }

    private ButtonType showConfirmationDialogForRemoving(String entity) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Removing "+entity+" :");
        alert.setContentText("Are you sure you want to remove this " + entity+ ", this operation can't undo");

        Optional<ButtonType> result = alert.showAndWait();

        return result.get();
    }

    private void alterDeleteConfigurationFile() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deleting Configuration File");
        alert.setHeaderText(null);
        alert.setContentText("The Configuration file deleted successfully");
        alert.showAndWait();
    }

    @Override
    public void notifyAddingServer(LocalServer server) {
        serversList.getItems().add(server);
        if (isSendingReports) {
            server.startAllSimulation();
        }
    }


    private void showAddServerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AddEditServerScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Add new local server");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            AddEditServerController controller = loader.getController();
            controller.initAddingServer(app,this);

            stage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showEditServerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AddEditServerScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit local server");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            AddEditServerController controller = loader.getController();
            controller.initEditingServer(serversList.getSelectionModel().getSelectedItem());
            stage.showAndWait();
            refreshSelection();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showRemoveServerScreen() {
        if (showConfirmationDialogForRemoving("server") == ButtonType.OK) {
            int selectedIndex = serversList.getSelectionModel().getSelectedIndex();
            LocalServer server = app.removeServer(selectedIndex);
            server.stopAllSimulation();
            serversList.getItems().remove(selectedIndex);
            serversList.getSelectionModel().clearSelection();
            boolean status = server.deleteConfigFile();
            if (status) {
                //TODO: may delete Configuration file
                //alterDeleteConfigurationFile();
            }
        }
    }



    // KIOSKS SECTIONS

    @Override
    public void notifyAddingKiosk(KioskReportSimulator reportSimulator) {
        kiosksList.getItems().add(reportSimulator);
        if (isSendingReports) {
            reportSimulator.startSimulation();
        }
    }

    private void showAddKioskScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AddEditKioskScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Add new kiosk");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            LocalServer selectedServer = serversList.getSelectionModel().getSelectedItem();
            AddEditKioskController controller = loader.getController();
            controller.initAddingKiosk(selectedServer,this);

            stage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showEditKioskScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AddEditKioskScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit kiosk");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            AddEditKioskController controller = loader.getController();
            controller.initEditingKiosk(kiosksList.getSelectionModel().getSelectedItem());
            stage.showAndWait();
            refreshSelection();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRemoveKioskScreen() {
        if (showConfirmationDialogForRemoving("kiosk") ==  ButtonType.OK) {
            LocalServer selectedServer = serversList.getSelectionModel().getSelectedItem();
            int selectedKiosk = kiosksList.getSelectionModel().getSelectedIndex();
            KioskReportSimulator simulator = selectedServer.removeKioskSimulator(selectedKiosk);
            simulator.stopSimulation();
            kiosksList.getItems().remove(selectedKiosk);
            kiosksList.getSelectionModel().clearSelection();
            refreshSelection();
        }
    }


    private void refreshSelection() {

        int selectedServer = serversList.getSelectionModel().getSelectedIndex();
        int selectedKiosk = kiosksList.getSelectionModel().getSelectedIndex();
        int selectedDevice = devicesList.getSelectionModel().getSelectedIndex();
        int selectedProblem = problemsList.getSelectionModel().getSelectedIndex();

        serversList.getSelectionModel().clearSelection();
        kiosksList.getSelectionModel().clearSelection();
        devicesList.getSelectionModel().clearSelection();
        problemsList.getSelectionModel().clearSelection();

        serversList.getSelectionModel().clearAndSelect(selectedServer);
        kiosksList.getSelectionModel().clearAndSelect(selectedKiosk);
        devicesList.getSelectionModel().clearAndSelect(selectedDevice);
        problemsList.getSelectionModel().clearAndSelect(selectedProblem);
    }


    private void closeWindow() {
        ((Stage) buttonClose.getScene().getWindow()).close();
    }


    private void stopSendingReports() {
        isSendingReports=false;
        app.stopAppSimulation();
        buttonStart.setDisable(false);
        buttonStop.setDisable(true);
    }

    private void startSendingReports() {
        isSendingReports = true;
        app.startAppSimulation();
        buttonStop.setDisable(false);
        buttonStart.setDisable(true);
    }
}
