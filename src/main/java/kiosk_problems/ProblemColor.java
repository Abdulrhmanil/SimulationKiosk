package kiosk_problems;

import javafx.scene.paint.Color;

import java.io.Serializable;

public enum ProblemColor implements Serializable {

    RED(Color.RED),
    YELLOW(Color.YELLOW);

    private Color color;

    ProblemColor(Color color) {
        this.color=color;
    }

    public Color getColor() {
        return color;
    }
}
