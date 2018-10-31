package simulation;

import kiosk_problems.Problems;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static simulation.Devices.*;

public class KioskDevice implements Serializable {
    private Devices device;
    private List<Problems> problems;

    public KioskDevice(Devices device) {
        this.device = device;
        problems = Arrays.stream(Problems.values()).filter(e -> e.getDevice()==device).collect(Collectors.toList());
    }

    public Devices getDevice() {
        return device;
    }

    public List<Problems> getProblems() {
        return problems;
    }

    public static List<KioskDevice> getSystemDevices() {
        return Arrays.asList(
                new KioskDevice(SCANNER),
                new KioskDevice(CREDIT_READER),
                new KioskDevice(COIN_MACHINE),
                new KioskDevice(PAPER_MONEY_MACHINE),
                new KioskDevice(CURRENCY_CONTROL_COMPONENT),
                new KioskDevice(SCREEN),
                new KioskDevice(PRINTER),
                new KioskDevice(DISPENSER));
    }

    @Override
    public String toString() {
        return device.toReadableString();
    }


}
