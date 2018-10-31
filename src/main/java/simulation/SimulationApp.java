package simulation;

import kiosk_problems.ProblemColor;
import serverconfiguration.ServerConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that represent the Simulation, contains all the local servers/
 */
public class SimulationApp implements Serializable {


    private static final String SERIALIZATION_PATH = "res/ser/SimulationApp.ser";



    /** All the local server that we have in the app */
    private List<LocalServer> servers = new ArrayList<>();
    private Set<String> serversNames = new HashSet<>();



    public boolean isValiadName(String serverName) {
        return !serversNames.contains(serverName);
    }


    /**
     * Add Local server to the app
     * @param server is the server to add
     */
    public void addServer(LocalServer server) {
        if (isValiadName(server.getName())) {
            servers.add(server);
            serversNames.add(server.getName());
        }
    }


    /**
     *  Add Local server to the app, with his config settings
     * @param name is the name of the server
     * @param serverConfig is the configuration of the server.
     */
    public void addServer(String name, ServerConfiguration serverConfig) {
        //LocalServer localServer = new LocalServer(name, period, unit, serverConfig);
        LocalServer localServer = new LocalServer(name,serverConfig);
        addServer(localServer);
    }


    /**
     *
     *  Add Local server to the app, with his config settings
     * @param name is the name of the server
     * @param serverIP is the ip of the server
     * @param serverDNS is the DNS of the server
     * @param serverPort is the port of the server
     * @param serverRoute is the route of the server
     */
    public void addServer(String name, String serverIP, String serverDNS, String serverPort, String serverRoute) {
        ServerConfiguration serverConfiguration = new ServerConfiguration(serverIP, serverDNS, serverPort, serverRoute);
        addServer(name,serverConfiguration);
    }


    /**
     * Get the local server from the list with position index
     * @param index is the position of the local server to get
     * @return the local server in the position index
     */
    public LocalServer getServer(int index) {
        return servers.get(index);
    }


    /**
     * Remove the local server in the position index
     * @param index is the position of the local server to remove
     * @return the removed local server
     */
    public LocalServer removeServer(int index) {
        LocalServer server = servers.remove(index);
        serversNames.remove(server.getName());
        return server;
    }


    /**
     * Start the Simulation Process
     */
    public void startAppSimulation () {
        for (LocalServer server : servers) {
            server.startAllSimulation();
        }
    }


    /**
     * Stop the Simulation process
     */
    public void stopAppSimulation () {
        for (LocalServer server : servers) {
            server.stopAllSimulation();
        }
    }


    /**
     * Check if all the servers in the app is good (NOT reporting a problem)
     * @return true if all server with good status (NOT reporting a problem), false otherwise.
     */
    public boolean isAllServersGood() {
        for (LocalServer localServer : servers) {
            if (!localServer.isAllKiosksGood()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Get the status color of the app, if 1 at least 1 server is reporting a RED problem,
     * then the status color is RED.
     * @return the color of the app status (all local servers)
     */
    public ProblemColor getAllServersColor() {
        for (LocalServer server : servers) {
            if (server.getAllKiosksColor().equals(ProblemColor.RED)) {
                return ProblemColor.RED;
            }
        }
        return ProblemColor.YELLOW;
    }


    /**
     * Get the list of the local servers in the app
     * @return the list of the servers.
     */
    public List<LocalServer> getServers() {
        return servers;
    }


    public static void saveAppToFile(SimulationApp app) throws IOException {
        File parent= new File(new File(SERIALIZATION_PATH).getParent());
        parent.mkdirs();

        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_PATH));
        stream.writeObject(app);
        stream.close();
    }

    public static SimulationApp loadAppFromFile() throws Exception {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(SERIALIZATION_PATH));
        SimulationApp app =(SimulationApp) stream.readObject();
        stream.close();
        return app;
    }
}
