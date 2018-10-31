package dataclasses;

import java.io.Serializable;
import java.time.LocalDateTime;

public class KioskExceptions implements Serializable {

    private String exceptionDisc;
    private String exType;
    private String date;


    public KioskExceptions(String exType, String exceptionDisc, String date) {
        this.exType = exType;
        this.exceptionDisc = exceptionDisc;
        this.date = date;
    }

    public KioskExceptions(String exceptionCode,String exceptionColor ){
        this.exceptionDisc = exceptionCode;
        this.exType = exceptionColor;
        this.date = LocalDateTime.now().toString();
    }


    public String getExType() {
        return exType;
    }

    public String getExceptionDisc() {
        return exceptionDisc;
    }

    public String getDate() {
        return date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KioskExceptions)) return false;

        KioskExceptions that = (KioskExceptions) o;

        return exceptionDisc != null ? exceptionDisc.equals(that.exceptionDisc) : that.exceptionDisc == null;
    }

    @Override
    public int hashCode() {
        return exceptionDisc != null ? exceptionDisc.hashCode() : 0;
    }
}
