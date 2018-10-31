package serverconfiguration;

import com.google.gson.Gson;

import java.io.*;

public class ServerConfiguration implements Serializable {
    private final String serverDNS;
    private final String serverIP;
    private final String serverPort;
    private final String serverRoute;

    public ServerConfiguration(String serverIP, String serverDNS, String serverPort, String serverRoute) {
        this.serverIP = serverIP;
        this.serverDNS = serverDNS;
        this.serverPort = serverPort;
        this.serverRoute = serverRoute;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getServerDNS() {
        return serverDNS;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getServerRoute() {
        return serverRoute;
    }


    public boolean writeToJson(String configurationPath) {
        try {
            ServerConfiguration.writeToJson(configurationPath,this);
            return true;
        }
        catch (IOException e) {
            System.err.println("Could NOT write on disk");
            e.printStackTrace();
            return false;
        }

    }



    //TODO: delete the file.
    public static void writeToJson(String configurationPath,ServerConfiguration configuration)
            throws IOException {
        Writer writer = new FileWriter(configurationPath);
        Gson gson = new Gson();
        gson.toJson(configuration, writer);
        writer.close();
    }


    public static ServerConfiguration readFromJson(String configurationPath)
            throws IOException {
        Reader reader =  new FileReader(configurationPath);
        Gson gson = new Gson();
        ServerConfiguration configuration = gson.fromJson(reader, ServerConfiguration.class);
        reader.close();
        return configuration;
    }

    @Override
    public String toString() {
        return "ServerConfiguration{" +
                "serverIP='" + serverIP + '\'' +
                ", serverDNS='" + serverDNS + '\'' +
                ", serverPort='" + serverPort + '\'' +
                '}';
    }
}
