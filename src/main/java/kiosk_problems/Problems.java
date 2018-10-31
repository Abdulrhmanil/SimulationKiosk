package kiosk_problems;

import simulation.Devices;

import java.io.Serializable;

public enum Problems implements Serializable {

    PROBLEM1(1,"0x9001",Devices.SCANNER,"Disconnect and connect",ProblemType.LOCAL,ProblemColor.RED,"Strengthen the connection"),
    PROBLEM2(2,"0x9002",Devices.SCANNER,"ID not recognized",ProblemType.GENERAL,ProblemColor.RED,"Replace and take to the lab for repair"),
    PROBLEM3(3,"0x9003",Devices.SCANNER,"A dark image",ProblemType.GENERAL,ProblemColor.RED,"Replace and take to the lab for repair"),
    PROBLEM4(4,"0x9004",Devices.SCANNER,"Not getting stream",ProblemType.GENERAL,ProblemColor.RED,"Replace cable"),

    PROBLEM5(5,"0x9005",Devices.CREDIT_READER,"Does not recognize a credit card",ProblemType.GENERAL,ProblemColor.RED,"To change lips"),
    PROBLEM6(6,"0x9006",Devices.CREDIT_READER,"Disconnect",ProblemType.LOCAL,ProblemColor.RED,"Strengthen connections"),

    PROBLEM7(7,"0x9007",Devices.COIN_MACHINE,"The coins were over",ProblemType.LOCAL,ProblemColor.RED,"Fill in coins"),
    PROBLEM8(8,"0x9008",Devices.COIN_MACHINE,"Machine is full",ProblemType.LOCAL,ProblemColor.RED,"Empty coins"),
    PROBLEM9(9,"0x9009",Devices.COIN_MACHINE,"Not receive coins",ProblemType.LOCAL,ProblemColor.RED,"Reboot machine"),
    PROBLEM10(10,"0x9010",Devices.COIN_MACHINE,"Not receive coins, and have been rebooted",ProblemType.GENERAL,ProblemColor.RED,"Replace machine"),

    PROBLEM11(11,"0x9011",Devices.PAPER_MONEY_MACHINE,"The paper money were over",ProblemType.LOCAL,ProblemColor.RED,"Fill in paper money"),
    PROBLEM12(12,"0x9012",Devices.PAPER_MONEY_MACHINE,"Machine is full",ProblemType.LOCAL,ProblemColor.RED,"Empty paper money"),
    PROBLEM13(13,"0x9013",Devices.PAPER_MONEY_MACHINE,"Not receive paper money",ProblemType.LOCAL,ProblemColor.RED,"Reboot machine"),
    PROBLEM14(14,"0x9014",Devices.PAPER_MONEY_MACHINE,"Not receive paper money, and have been rebooted",ProblemType.GENERAL,ProblemColor.RED,"Replace machine"),

    PROBLEM15(15,"0x9015",Devices.PAPER_MONEY_MACHINE,"Reached the minimum quantity",ProblemType.NOTIFICATION,ProblemColor.YELLOW,""),
    PROBLEM16(16,"0x9016",Devices.PAPER_MONEY_MACHINE,"Reached the maximum quantity",ProblemType.NOTIFICATION,ProblemColor.YELLOW,""),

    PROBLEM17(17,"0x9017",Devices.COIN_MACHINE,"Reached the minimum quantity",ProblemType.NOTIFICATION,ProblemColor.YELLOW,""),
    PROBLEM18(18,"0x9018",Devices.COIN_MACHINE,"Reached the maximum quantity",ProblemType.NOTIFICATION,ProblemColor.YELLOW,""),

    PROBLEM19(19,"0x9019",Devices.CURRENCY_CONTROL_COMPONENT,"Apparently PAYLINK was hity",ProblemType.GENERAL,ProblemColor.RED,"Send technician with coin machine, paper money machine and PAYLINK"),

    PROBLEM20(20,"0x9020",Devices.SCREEN,"Screen not working",ProblemType.LOCAL,ProblemColor.RED,"Strengthen the connection"),
    PROBLEM21(21,"0x9021",Devices.SCREEN,"Screen not working, after checking the connection",ProblemType.GENERAL,ProblemColor.RED,"Replace screen"),

    PROBLEM22(22,"0x9022",Devices.PRINTER,"Pages reached a minimum quantity",ProblemType.NOTIFICATION,ProblemColor.YELLOW,""),
    PROBLEM23(23,"0x9023",Devices.PRINTER,"Paper is over",ProblemType.LOCAL,ProblemColor.RED,"Add paper"),
    PROBLEM24(24,"0x9024",Devices.PRINTER,"Printer does not print pages",ProblemType.LOCAL,ProblemColor.RED,"Check the paper if it's stuck"),
    PROBLEM25(25,"0x9025",Devices.PRINTER,"Printer does not print pages, and local check have been done",ProblemType.GENERAL,ProblemColor.RED,"Send a technician with a printer for replacement if necessary"),

    PROBLEM26(26,"0x9026",Devices.DISPENSER,"Dispenser does not extract a key",ProblemType.GENERAL,ProblemColor.RED,"Check and replace if necessary"),
    PROBLEM27(27,"0x9027",Devices.DISPENSER,"Dispenser extract unburned key ",ProblemType.GENERAL,ProblemColor.RED,"Check and replace if necessary"),
    PROBLEM28(28,"0x9028",Devices.DISPENSER,"Dispenser does not receive a key",ProblemType.GENERAL,ProblemColor.RED,"Check and replace if necessary")
    ;


    private int num;
    private String code;
    private Devices device;
    private String description;
    private ProblemType problemType;
    private ProblemColor problemColor;
    private String solution;

    Problems(int num, String code, Devices device, String description, ProblemType problemType, ProblemColor problemColor, String solution) {
        this.num = num;
        this.code = code;
        this.device = device;
        this.description = description;
        this.problemType = problemType;
        this.problemColor = problemColor;
        this.solution = solution;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + this.code + " - " + this.problemType;
    }

    public int getNum() {
        return num;
    }

    public String getCode() {
        return code;
    }

    public Devices getDevice() {
        return device;
    }

    public String getDescription() {
        return description;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public ProblemColor getProblemColor() {
        return problemColor;
    }

    public String getSolution() {
        return solution;
    }
}
