package com.arcrobotics.ftclib.command.button;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

/**
 * A {@link Button} that gets its state from {@link GamepadEx} axis' and a threshold.
 */
public class GamepadAxisButton extends Button {

    private final GamepadEx gamepad;
    private final GamepadKeys.Axis[] axis;
    private final double threshold;

    /**
     * Creates a gamepad axis button for triggering commands.
     *
     * @param gamepad The gamepad with the axis'.
     * @param threshold The threshold for the axis' to trigger.
     * @param axis The axis' to listen to.
     */
    public GamepadAxisButton(GamepadEx gamepad, double threshold, @NonNull GamepadKeys.Axis... axis) {
        this.gamepad = gamepad;
        this.axis = axis;
        this.threshold = threshold;
    }

    /**
     * Gets the value of the joystick button.
     *
     * @return The value of the joystick button
     */
    @Override
    public boolean get() {
        boolean res = true;
        for (GamepadKeys.Axis axis : axis)
            res = res && (gamepad.getAxis(axis) >= threshold);
        return res;
    }

}
