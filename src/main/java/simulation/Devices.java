package simulation;

import java.io.Serializable;

public enum Devices implements Serializable {
    COMPUTER, SCREEN, SCANNER,
    PRINTER, COIN_MACHINE, PAPER_MONEY_MACHINE,
    DISPENSER, PARKING_INTERFACE, DINING_ROOM_INTERFACE,
    CURRENCY_CONTROL_COMPONENT,CREDIT_READER,AC;


    public String toReadableString(){
        switch (this){
            case COMPUTER:
                return "COMPUTER";

            case SCREEN:
                return "SCREEN";

            case SCANNER:
                return "SCANNER";

            case PRINTER:
                return "PRINTER";

            case COIN_MACHINE:
                return "COIN MACHINE";

            case PAPER_MONEY_MACHINE:
                return "PAPER MONEY MACHINE";

            case DISPENSER:
                return "DISPENSER";

            case PARKING_INTERFACE:
                return "PARKING INTERFACE";

            case DINING_ROOM_INTERFACE:
                return "DINING ROOM INTERFACE";

            case CURRENCY_CONTROL_COMPONENT:
                return "CURRENCY CONTROL COMPONENT";

            case CREDIT_READER:
                return "CREDIT READER";

            case AC:
                return "AC";

        }
        return "UNKNOWN";
    }
}
