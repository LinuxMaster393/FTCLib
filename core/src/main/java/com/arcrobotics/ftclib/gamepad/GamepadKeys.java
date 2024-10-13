package com.arcrobotics.ftclib.gamepad;

/**
 * An enumerator for the different keys on the gamepad, including bumpers,
 * buttons, and triggers.
 */

public class GamepadKeys {

    public enum Button {
        Y, X, A, B, LEFT_BUMPER, RIGHT_BUMPER, BACK,
        START, DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT,
        LEFT_STICK_BUTTON, RIGHT_STICK_BUTTON
    }

    public enum Axis {
        LEFT_TRIGGER, RIGHT_TRIGGER, LEFT_STICK_X, RIGHT_STICK_X, LEFT_STICK_Y, RIGHT_STICK_Y
    }

}
