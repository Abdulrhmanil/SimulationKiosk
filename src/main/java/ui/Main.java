package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import simulation.SimulationApp;

import java.io.IOException;


public class Main extends Application {

    final static SimulationApp app = Main.loadSimulationApp();


    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SimulationScreen.fxml"));
        Parent root = loader.load();

        //Parent root = FXMLLoader.load(getClass().getResource("SimulationScreen.fxml"));
        primaryStage.setTitle("Simulation Kiosk");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnHiding(event -> {
            Main.app.stopAppSimulation();
            saveSimulationApp();
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static SimulationApp getApp() {
        return app;
    }

    private static SimulationApp loadSimulationApp() {
        try {
            return SimulationApp.loadAppFromFile();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fail to load simulation app");
            alert.setHeaderText("Fail to load simulation app data from file");
            alert.setContentText("We fail to load the data, so we create new empty simulation app");
            alert.showAndWait();
            return new SimulationApp();
        }
    }
    private static void saveSimulationApp() {
        try {
            SimulationApp.saveAppToFile(Main.app);
            System.out.println("saved successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail to save");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fail to save simulation app");
            alert.setHeaderText("Fail to save the state of the app");
            alert.setContentText("We fail to save the data");
            alert.showAndWait();
        }

    }

}
