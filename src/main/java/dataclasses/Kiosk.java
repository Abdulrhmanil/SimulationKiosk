package dataclasses;

import kiosk_problems.ProblemColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Kiosk implements Serializable {

    private String serverID;
    private String macAddress;
    private String hotelName;
    private boolean Good;
    final private List<KioskExceptions> exceptions;

    public Kiosk(String serverID, String macAddress, String hotelName) {
        this.serverID = serverID;
        this.macAddress = macAddress;
        this.hotelName = hotelName;
        this.exceptions = new ArrayList<>();
        this.Good =true;
    }



    public Kiosk(String serverID, String macAddress, String hotelName,List<KioskExceptions> exceptions) {
        this.serverID = serverID;
        this.macAddress = macAddress;
        this.hotelName = hotelName;
        this.exceptions =exceptions;
        if(exceptions.isEmpty())
            this.Good = true;
        else
            this.Good =false;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getHotelName() {
        return hotelName;
    }


    public boolean isGood() {
        return Good;
    }

    public ProblemColor getKioskColor() {
        for (KioskExceptions exception : exceptions) {
            if (exception.getExType().equals(ProblemColor.RED.toString())){
                return ProblemColor.RED;
            }
        }
        return ProblemColor.YELLOW;
    }


    public String getServerID() {
        return serverID;
    }


    public void addKioskExceptions(KioskExceptions exception) {
        exceptions.add(exception);
        if (!exceptions.isEmpty()){
            Good = false;
        }
    }
    public void addKioskExceptions(String exceptionCode,String exceptionColor) {
        exceptions.add(new KioskExceptions(exceptionCode,exceptionColor));
        if (!exceptions.isEmpty()){
            Good =false;
        }
    }
    public boolean removeKioskExceptions(String exceptionCode) {
        boolean removeStatus = exceptions.removeIf(e -> e.getExceptionDisc().equals(exceptionCode));
        if (exceptions.isEmpty()){
            Good =true;
        }
        return removeStatus;
    }

    public boolean isProblemExist(String exceptionCode) {
        return exceptions.stream().anyMatch(ex->ex.getExceptionDisc().equals(exceptionCode));
    }

    public boolean isProblemExist(List<String> exceptionCodes) {
        for (String exceptionCode : exceptionCodes) {
            if (isProblemExist(exceptionCode)) {
                return true;
            }
        }
        return false;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
