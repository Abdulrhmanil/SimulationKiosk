package simulation;

import com.google.gson.Gson;
import dataclasses.Kiosk;
import dataclasses.KioskExceptions;
import kiosk_problems.ProblemColor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import serverconfiguration.ServerConfiguration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KioskReportSimulator implements Serializable {

    private static final String CONTENT_TYPE = "application/json";

    private ServerConfiguration serverConfig;
    private long period;
    private TimeUnit unit;

    private final Kiosk kiosk;
    private final List<KioskDevice> kioskDevices = KioskDevice.getSystemDevices();
    transient private ScheduledExecutorService service;
    transient private HttpPost request;

    //private final String configurationPath;


    public void setServerConfig(ServerConfiguration serverConfig) {
        this.serverConfig = serverConfig;
        kiosk.setServerID(serverConfig.getServerIP());
    }

    public KioskReportSimulator(ServerConfiguration serverConfig, long period, TimeUnit unit,
                                String serverID, String macAddress, String hotelName) {
        this.serverConfig = serverConfig;
        this.period=period;
        this.unit=unit;
        this.kiosk=new Kiosk(serverID,macAddress,hotelName);
    }

    public KioskReportSimulator(ServerConfiguration serverConfig, long period, TimeUnit unit, Kiosk kiosk) {
        this.serverConfig = serverConfig;
        this.period=period;
        this.unit=unit;
        this.kiosk=kiosk;
    }

    public Kiosk getKiosk() {
        return kiosk;
    }

    public List<KioskDevice> getKioskDevices() {
        return kioskDevices;
    }

    public String getMacAddress() {
        return getKiosk().getMacAddress();
    }

    public String getHotelName() {
        return getKiosk().getHotelName();
    }

    public boolean isGood() {
        return getKiosk().isGood();
    }

    public ProblemColor getKioskColor() {
        return kiosk.getKioskColor();
    }

    public String getServerID() {
        return getKiosk().getServerID();
    }

    public long getPeriod() {
        return period;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public String getInterval() {
        return getPeriod() + " " + getUnit();
    }

    @Override
    public String toString() {
        return getHotelName();
    }

    public void addKioskExceptions(KioskExceptions exception) {
        kiosk.addKioskExceptions(exception);
    }

    public void addKioskExceptions(String exceptionCode, String exceptionColor) {
        kiosk.addKioskExceptions(exceptionCode, exceptionColor);
    }

    public boolean removeKioskExceptions(String exceptionCode) {
        return kiosk.removeKioskExceptions(exceptionCode);
    }

    public boolean isProblemExist(String exceptionCode) {
        return kiosk.isProblemExist(exceptionCode);
    }


    public void setServerID(String serverID) {
        kiosk.setServerID(serverID);
    }

    public boolean isProblemExist(List<String> exceptionCodes) {
        return kiosk.isProblemExist(exceptionCodes);
    }



    public String getKioskStatusAsJsonString() {
        final Gson gson = new Gson();
        return gson.toJson(kiosk);
    }



    private static CloseableHttpClient createAcceptSelfSignedCertificateClient()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        // we can optionally disable hostname verification.
        // if you don't want to further weaken the security, you don't have to include this.
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

        // create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
        // and allow all hosts verifier.
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
        //SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext);

        // finally create the HttpClient using HttpClient factory methods and assign the ssl socket factory
        return HttpClients
                .custom()
                .setSSLSocketFactory(connectionFactory)
                .build();
    }

    private void commandWithSecurity() {
        String payload = getKioskStatusAsJsonString();
        System.out.println(payload);

        StringEntity entity = new StringEntity(payload, ContentType.create(CONTENT_TYPE));
        final String uri = "https://" + serverConfig.getServerIP() + ":" + serverConfig.getServerPort()+ "/" + serverConfig.getServerRoute();
        System.out.println(uri);
        this.request = new HttpPost(uri);
        request.setEntity(entity);

        try {
            final HttpClient httpClient = createAcceptSelfSignedCertificateClient();
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());

            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);
            System.out.println("\n");
        }
        catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            System.err.println("1");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("2");
        }

    }

    private void newCommandWithSecurity() {
        String payload = getKioskStatusAsJsonString();
        System.out.println(payload);

        StringEntity entity = new StringEntity(payload, ContentType.create(CONTENT_TYPE));


        final String uri = "https://" + serverConfig.getServerIP() + ":" + serverConfig.getServerPort()+ "/registerKiosk" ;
        System.out.println(uri);
        this.request = new HttpPost(uri);
        request.setEntity(entity);
        request.addHeader("username","kiosk");
        request.addHeader("password","pass");

        try {
            HttpClient httpClient = createAcceptSelfSignedCertificateClient();
            HttpResponse response = httpClient.execute(request);
            String content = EntityUtils.toString(response.getEntity());


            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(content);
            System.out.println("\n");
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            System.err.println("1");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("2");
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }

    }



    private void command() {
        String payload = getKioskStatusAsJsonString();
        System.out.println(payload);
        StringEntity entity = new StringEntity(payload, ContentType.create(CONTENT_TYPE));
        HttpClient httpClient = HttpClientBuilder.create().build();
        final String uri = "http://" + serverConfig.getServerIP() + ":" + serverConfig.getServerPort()+ "/" + serverConfig.getServerRoute();
        System.out.println(uri);
        this.request = new HttpPost(uri);
        request.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());

            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);
            System.out.println("\n");
        }
        catch (IOException e) {
            System.out.println("NO CONNECTION !!!");
        }

    }



    public synchronized void startSimulation() {
        if (service != null && !service.isShutdown()) {
            System.err.println("The Simulation already started");
            throw new RuntimeException("You try to start the Simulation, when already started");
        }
        else {
            service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(this::commandWithSecurity, 0, this.period, this.unit);
        }
    }

    public synchronized void stopSimulation() {
        if (this.service != null && !service.isShutdown()) {
            this.request.abort();
            service.shutdown();
        }
    }

    private void sendReports() {
        boolean interrupted = false;
        long sleepTimeInMillis=unit.toMillis(period);
        try {
            while (!interrupted) {
                if (Thread.interrupted()) {
                    interrupted=true;
                    System.out.println("interrupted while sendReports");
                }
                else {
                    command();
                    Thread.sleep(sleepTimeInMillis);
                }
            }
        }
        catch (InterruptedException e) {
            System.out.println("we have been Interrupted while sleep");
            interrupted = true;
        }

    }



    public void setPeriod(long period) {
        this.period = period;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public void setMacAddress(String macAddress) {
        kiosk.setMacAddress(macAddress);
    }

    public void setHotelName(String hotelName) {
        kiosk.setHotelName(hotelName);
    }
}
