package com.arcrobotics.ftclib.gamepad;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class AxisReader implements KeyReader {

    /**
     * Current threshold.
     **/
    private final double threshold;

    /**
     * Last state of the axis.
     **/
    private boolean lastState;

    /**
     * Current state of the axis.
     **/
    private boolean currState;

    /**
     * The state of the axis.
     */
    private final DoubleSupplier axisState;

    /**
     * Initializes controller variables
     *
     * @param gamepad The controller joystick
     * @param axis  The controller axis
     * @param threshold The threshold to trigger past.
     **/
    public AxisReader(GamepadEx gamepad, GamepadKeys.Axis axis, double threshold) {
        this(() -> gamepad.getAxis(axis), threshold);
    }

    public AxisReader(DoubleSupplier axisState, double threshold) {
        this.axisState = axisState;
        this.threshold = threshold;
        currState = Math.abs(axisState.getAsDouble()) > threshold;
        lastState = currState;

    }

    /**
     * Reads axis value
     **/
    public void readValue() {
        lastState = currState;
        currState = Math.abs(axisState.getAsDouble()) > threshold;
    }

    /**
     * Checks if the axis is past the threshold
     **/
    public boolean isDown() {
        return currState;
    }

    /**
     * Checks if the axis just passed the threshold
     **/
    public boolean wasJustPressed() {
        return (!lastState && currState);
    }

    /**
     * Checks if the axis just went below the threshold
     **/
    public boolean wasJustReleased() {
        return (lastState && !currState);
    }

    /**
     * Checks if the axis' state has changed
     **/
    public boolean stateJustChanged() {
        return (lastState != currState);
    }

}
