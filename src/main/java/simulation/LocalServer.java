package simulation;

import dataclasses.Kiosk;
import kiosk_problems.ProblemColor;
import serverconfiguration.ServerConfiguration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocalServer implements Serializable {

    private static final String SUB_PATH = "res/";
    private static final String NAME_TEMPLATE = "_configuration.json";


    /** The name of the local server*/
    private final String name;

    /** The list of the Simulators that represent the kiosk the report his status, in every period time */
    private final List<KioskReportSimulator> kioskSimulators;

    /** The path of the configuration file*/
    private final String configPath;

    /** ServerConfiguration that represent the Configuration of the local server*/
    private ServerConfiguration serverConfig;


    /**
     * Create new Simulators to simulate a kiosk and report on your status.
     * @param name is the name of the kiosk
     * @param serverConfig is the Configuration setting of the server to send data to him.
     */
    public LocalServer(String name, ServerConfiguration serverConfig) {
        this.name = name;
        this.serverConfig = serverConfig;
        this.configPath = SUB_PATH + name + NAME_TEMPLATE;
        //ServerConfiguration.writeToJson(this.configPath, this.serverConfig);

        this.kioskSimulators = new ArrayList<>();
    }

    public boolean deleteConfigFile() {
        File deletePath = new File(configPath);
        return deletePath.delete();
    }


    /**
     * Set the Configuration of the server.
     * @param serverConfig is the Configuration of the server, that send data to him.
     */
    public void setServerConfig(ServerConfiguration serverConfig) {
        this.serverConfig = serverConfig;
        for (KioskReportSimulator simulator : kioskSimulators) {
            simulator.setServerConfig(serverConfig);
        }
    }


    /**
     * Add a new kiosk Simulator to the current local server.
     * @param macAddress is the mac address of the Kiosk.
     * @param hotelName is the hotel name of the Kiosk.
     */
    public KioskReportSimulator addKioskSimulator (String macAddress, String hotelName, long period, TimeUnit unit) {

        KioskReportSimulator simulator = new KioskReportSimulator(this.serverConfig, period, unit,
                this.serverConfig.getServerIP(), macAddress, hotelName);
        kioskSimulators.add(simulator);
        return simulator;
    }

    public KioskReportSimulator addKioskSimulator (Kiosk kiosk, long period, TimeUnit unit) {
        kiosk.setServerID(this.serverConfig.getServerIP());
        KioskReportSimulator simulator = new KioskReportSimulator(this.serverConfig, period, unit,kiosk);
        kioskSimulators.add(simulator);
        return simulator;
    }


    /**
     * Get the Kiosk Simulator in the position index
     * @param index is the index of the KioskReportSimulator
     * @return the KioskReportSimulator that exist in the position index
     */
    public KioskReportSimulator getKioskSimulator(int index) {
        return kioskSimulators.get(index);
    }


    /**
     * Remove KioskSimulator from the list with the position index
     * @param index is the index of the KioskReportSimulator
     * @return KioskReportSimulator with position index from the kiosks list
     */
    public KioskReportSimulator removeKioskSimulator(int index) {
        return kioskSimulators.remove(index);
    }


    /**
     * Start the simulation of all kiosk in the current local server.
     */
    public void startAllSimulation () {
        for (KioskReportSimulator simulator : kioskSimulators) {
            simulator.startSimulation();
        }
    }

    /**
     * Stop the simulation of all kiosk in the current local server.
     */
    public void stopAllSimulation () {
        for (KioskReportSimulator simulator : kioskSimulators) {
            simulator.stopSimulation();
        }
    }

    /**
     * Get kiosk Simulators list of the current local server.
     * @return the list of Simulators that exist in the current local server.
     */
    public List<KioskReportSimulator> getKioskSimulators() {
        return kioskSimulators;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the server name
     * @return the name of the server.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Configuration of the current local server.
     * @return the ServerConfiguration that represent the configurations of the server.
     */
    public ServerConfiguration getServerConfig() {
        return serverConfig;
    }

    /**
     * Get the IP of the server
     * @return IP OF the server
     */
    public String getServerIP() {
        return serverConfig.getServerIP();
    }

    /**
     * Get the DNS of the server
     * @return the DNS of the server.
     */
    public String getServerDNS() {
        return serverConfig.getServerDNS();
    }

    /**
     * Get the port of the server
     * @return the port of the server
     */
    public String getServerPort() {
        return serverConfig.getServerPort();
    }

    /**
     * Get the route of the server
     * @return the route of the server
     */
    public String getServerRoute() {
        return serverConfig.getServerRoute();
    }

    /**
     * Check if all kiosk in the current local servers is good (Good status mean that NOT reporting a problem).
     * @return true if the local server is good, false otherwise.
     */
    public boolean isAllKiosksGood() {
        for (KioskReportSimulator kioskSimulator : kioskSimulators) {
            if (!kioskSimulator.isGood()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the status color of the current local server
     * @return the status color of the current local server (could be RED/YELLOW)
     */
    public ProblemColor getAllKiosksColor() {

        for (KioskReportSimulator kioskSimulator : kioskSimulators) {
            for (KioskReportSimulator simulator : kioskSimulators) {
                if (simulator.getKioskColor().equals(ProblemColor.RED)){
                    return ProblemColor.RED;
                }
            }
        }
        return ProblemColor.YELLOW;
    }



}
