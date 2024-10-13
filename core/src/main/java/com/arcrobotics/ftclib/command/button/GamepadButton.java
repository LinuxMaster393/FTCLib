package com.arcrobotics.ftclib.command.button;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

/**
 * A {@link Button} that gets its state from a {@link GamepadEx} button.
 *
 * @author Jackson
 */
public class GamepadButton extends Button {

    private final GamepadEx gamepad;
    private final GamepadKeys.Button[] buttons;

    /**
     * Creates a gamepad button for triggering commands.
     *
     * @param gamepad The gamepad with the buttons.
     * @param buttons The specified buttons.
     */
    public GamepadButton(GamepadEx gamepad, @NonNull GamepadKeys.Button... buttons) {
        this.gamepad = gamepad;
        this.buttons = buttons;
    }

    /**
     * Gets the value of the button.
     *
     * @return The value of the button
     */
    @Override
    public boolean get() {
        boolean res = true;
        for (GamepadKeys.Button button : buttons)
            res = res && gamepad.getButton(button);
        return res;
    }

}
