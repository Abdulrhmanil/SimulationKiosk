package ui.reactions;

import simulation.KioskReportSimulator;
import simulation.LocalServer;

public interface AddingKioskListener {
    void notifyAddingKiosk(KioskReportSimulator reportSimulator);
}
