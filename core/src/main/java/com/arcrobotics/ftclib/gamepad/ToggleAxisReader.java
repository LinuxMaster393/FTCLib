package com.arcrobotics.ftclib.gamepad;

import java.util.function.DoubleSupplier;

/**
 * Class gets the current state of a toggle axis.
 */
public class ToggleAxisReader extends AxisReader {

    private boolean currToggleState;

    /**
     * The constructor that uses the gamepad and an axis to refer to a certain state toggler.
     *
     * @param gamepad The gamepad object that contains the axis.
     * @param axis  The axis on the object.
     * @param threshold The threshold to trigger past.
     */
    public ToggleAxisReader(GamepadEx gamepad, GamepadKeys.Axis axis, double threshold) {
        super(gamepad, axis, threshold);

        currToggleState = false;
    }

    /**
     * The constructor that checks the values returned by a double supplier
     * object.
     *
     * @param axisValue The value supplier.
     * @param threshold The threshold to trigger past.
     */
    public ToggleAxisReader(DoubleSupplier axisValue, double threshold) {
        super(axisValue, threshold);

        currToggleState = false;
    }

    /**
     * @return The current state of the toggler.
     */
    public boolean getState() {
        if (wasJustReleased()) {
            currToggleState = !currToggleState;
        }
        return (currToggleState);
    }

}
