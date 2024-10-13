package com.arcrobotics.ftclib.gamepad;

import android.util.Log;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys.Button;
import com.arcrobotics.ftclib.gamepad.GamepadKeys.Axis;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;

/**
 * An extended gamepad for more advanced toggles, key events,
 * and other control processors.
 */
public class GamepadEx {
    private static final String TAG = "com.arcrobotics.ftclib.gamepad.GamepadEx";

    /**
     * FTC's {@link Gamepad} object.
     */
    @NonNull
    public final Gamepad gamepad;

    private final HashMap<Button, ButtonReader> buttonReaders;
    private final HashMap<Axis, AxisReader> axisReaders;
    private final HashMap<Button, GamepadButton> gamepadButtons;
    
    @IntRange(from = -1, to = 1)
    private int negateYAxis;


    /**
     * Creates a {@link GamepadEx} from a {@link Gamepad} object, with an axis threshold of 0.05
     *
     * @param gamepad The Gamepad object from the {@link com.qualcomm.robotcore.eventloop.opmode.OpMode OpMode}
     */
    public GamepadEx(@NonNull Gamepad gamepad) {
        this(gamepad, 0.05);
    }

    /**
     * Creates a {@link GamepadEx} from a {@link Gamepad} object
     *
     * @param gamepad The Gamepad object from the {@link com.qualcomm.robotcore.eventloop.opmode.OpMode OpMode}
     * @param axisThreshold The threshold to set the {@link AxisReader} objects to.
     */
    public GamepadEx(@NonNull Gamepad gamepad, @FloatRange(from = -1.0, to = 1.0) double axisThreshold) {
        this.gamepad = gamepad;
        buttonReaders = new HashMap<>();
        axisReaders = new HashMap<>();
        gamepadButtons = new HashMap<>();
        for (Button button : Button.values()) {
            buttonReaders.put(button, new ButtonReader(this, button));
            gamepadButtons.put(button, new GamepadButton(this, button));
        }
        for (Axis axis : Axis.values()) {
            axisReaders.put(axis, new AxisReader(this, axis, axisThreshold));
        }
        negateYAxis = 1;
    }

    /**
     * Returns the button value from the raw {@link Gamepad} object corresponding to the {@link Button} value passed.
     * 
     * @param button The {@link Button} enum value.
     * @return The boolean value returned from the gamepad button in question.
     */
    public boolean getButton(@NonNull Button button) {
        boolean buttonValue = false;
        switch (button) {
            case A:
                buttonValue = gamepad.a;
                break;
            case B:
                buttonValue = gamepad.b;
                break;
            case X:
                buttonValue = gamepad.x;
                break;
            case Y:
                buttonValue = gamepad.y;
                break;
            case LEFT_BUMPER:
                buttonValue = gamepad.left_bumper;
                break;
            case RIGHT_BUMPER:
                buttonValue = gamepad.right_bumper;
                break;
            case DPAD_UP:
                buttonValue = gamepad.dpad_up;
                break;
            case DPAD_DOWN:
                buttonValue = gamepad.dpad_down;
                break;
            case DPAD_LEFT:
                buttonValue = gamepad.dpad_left;
                break;
            case DPAD_RIGHT:
                buttonValue = gamepad.dpad_right;
                break;
            case BACK:
                buttonValue = gamepad.back;
                break;
            case START:
                buttonValue = gamepad.start;
                break;
            case LEFT_STICK_BUTTON:
                buttonValue = gamepad.left_stick_button;
                break;
            case RIGHT_STICK_BUTTON:
                buttonValue = gamepad.right_stick_button;
                break;
        }
        return buttonValue;
    }

    /**
     * Returns the axis value from the raw {@link Gamepad} object corresponding to the {@link Axis} value passed.
     * 
     * @param axis The {@link Axis} enum value.
     * @return The double value returned from the gamepad axis in question.
     */
    public double getAxis(@NonNull Axis axis) {
        double axisValue = 0;
        switch (axis) {
            case LEFT_TRIGGER:
                axisValue = gamepad.left_trigger;
                break;
            case RIGHT_TRIGGER:
                axisValue = gamepad.right_trigger;
                break;
            case LEFT_STICK_X:
                axisValue = gamepad.left_stick_x;
                break;
            case LEFT_STICK_Y:
                axisValue = gamepad.left_stick_y;
                break;
            case RIGHT_STICK_X:
                axisValue = gamepad.right_stick_x;
                break;
            case RIGHT_STICK_Y:
                axisValue = gamepad.right_stick_y;
                break;
        }
        return axisValue;
    }

    /**
     * Convenience method for {@link GamepadEx#getAxis(Axis) GamepadEx.getAxis(GamepadKeys.Axis.LEFT_TRIGGER) }
     *
     * @return The value returned by the left trigger.
     */
    public double getLeftTrigger() {
        return getAxis(Axis.LEFT_TRIGGER);
    }

    /**
     * Convenience method for {@link GamepadEx#getAxis(Axis) GamepadEx.getAxis(GamepadKeys.Axis.RIGHT_TRIGGER) }
     *
     * @return The value returned by the right trigger.
     */
    public double getRightTrigger() {
        return getAxis(Axis.RIGHT_TRIGGER);
    }

    /**
     * Convenience method for {@link GamepadEx#getAxis(Axis) GamepadEx.getAxis(GamepadKeys.Axis.LEFT_STICK_Y) },
     * with negation if configured to using {@link GamepadEx#setNegateYAxis(boolean)}
     *
     * @return The Y value on the left joystick.
     * @see GamepadEx#setNegateYAxis(boolean)
     */
    public double getLeftY() {
        return getAxis(Axis.LEFT_STICK_Y) * negateYAxis;
    }

    /**
     * Convenience method for {@link GamepadEx#getAxis(Axis) GamepadEx.getAxis(GamepadKeys.Axis.RIGHT_STICK_Y) },
     * with negation if configured to using {@link GamepadEx#setNegateYAxis(boolean)}
     *
     * @return The Y value on the right joystick.
     * @see GamepadEx#setNegateYAxis(boolean)
     */
    public double getRightY() {
        return getAxis(Axis.LEFT_STICK_Y) * negateYAxis;
    }

    /**
     * Negate the joystick Y axis, as the hardware convention is joystick down is -Y.
     *
     * @param negate Whether we should negate the joystick Y axis or not.
     * @see GamepadEx#isNegatingYAxis()
     */
    public void setNegateYAxis(boolean negate) {
        negateYAxis = negate ? -1 : 1;
    }

    /**
     * Returns whether we are negating the joystick Y axis
     *
     * @return Whether we are negating the joystick Y axis
     * @see GamepadEx#setNegateYAxis(boolean)
     */
    public boolean isNegatingYAxis() {
        return negateYAxis == -1;
    }

    /**
     * Convenience method for {@link GamepadEx#getAxis(Axis) GamepadEx.getAxis(GamepadKeys.Axis.LEFT_STICK_X) }.
     *
     * @return The X value on the left joystick.
     */
    public double getLeftX() {
        return getAxis(Axis.LEFT_STICK_X);
    }

    /**
     * Convenience method for {@link GamepadEx#getAxis(Axis) GamepadEx.getAxis(GamepadKeys.Axis.RIGHT_STICK_X) }
     *
     * @return The X value on the right joystick.
     */
    public double getRightX() {
        return getAxis(Axis.RIGHT_STICK_X);
    }

    /**
     * Returns true if this is the first loop that the button was pressed, i.e, it was just pressed.
     *
     * @param button The desired button to read from.
     * @return If the button was just pressed.
     * @see GamepadEx#readButtons()
     */
    public boolean wasJustPressed(Button button) {
        //noinspection DataFlowIssue
        return buttonReaders.get(button).wasJustPressed();
    }

    /**
     * Returns true if this is the first loop that the axis is past the threshold, i.e, it was just pressed.
     *
     * @param axis The desired axis to read from.
     * @return If the axis was just pressed.
     * @see GamepadEx#readAxis()
     */
    public boolean wasJustPressed(Axis axis) {
        //noinspection DataFlowIssue
        return axisReaders.get(axis).wasJustPressed();
    }

    /**
     * Returns true if this is the first loop that the button was not pressed, i.e, it was just released.
     *
     * @param button The desired button to read from.
     * @return If the button was just released.
     * @see GamepadEx#readButtons()
     */
    public boolean wasJustReleased(Button button) {
        //noinspection DataFlowIssue
        return buttonReaders.get(button).wasJustReleased();
    }

    /**
     * Returns true if this is the first loop that the axis is not past the threshold, i.e, it was just released.
     *
     * @param axis The desired axis to read from.
     * @return If the axis was just released.
     * @see GamepadEx#readAxis()
     */
    public boolean wasJustReleased(Axis axis) {
        //noinspection DataFlowIssue
        return axisReaders.get(axis).wasJustReleased();
    }

    /**
     * Returns if the button is pressed. Same as {@link GamepadEx#getButton(Button)},
     * except it uses the {@link ButtonReader}
     *
     * @param button The desired button to read from.
     * @return If the button is down.
     * @see GamepadEx#readButtons()
     */
    public boolean isDown(Button button) {
        //noinspection DataFlowIssue
        return buttonReaders.get(button).isDown();
    }

    /**
     * Returns if the axis is past the threshold.
     *
     * @param axis The desired axis to read from.
     * @return If the axis is down.
     * @see GamepadEx#readAxis()
     */
    public boolean isDown(Axis axis) {
        //noinspection DataFlowIssue
        return axisReaders.get(axis).isDown();
    }

    /**
     * Returns true if the button's state has just changed, i.e, it has either just been pressed, or just been released.
     *
     * @param button The desired button to read from.
     * @return If the button's state has just changed.
     * @see GamepadEx#readButtons()
     */
    public boolean stateJustChanged(Button button) {
        //noinspection DataFlowIssue
        return buttonReaders.get(button).stateJustChanged();
    }

    /**
     * Returns true if the axis' state has just changed, i.e, it has either just been pressed,
     * or just been released.
     *
     * @param axis The desired axis to read from.
     * @return If the axis' state has just changed.
     * @see GamepadEx#readAxis()
     */
    public boolean stateJustChanged(Axis axis) {
        //noinspection DataFlowIssue
        return axisReaders.get(axis).stateJustChanged();
    }

    /**
     * Fetches the current gamepad button values, updating the {@link ButtonReader} objects.
     * Call this once in your loop for
     * {@link GamepadEx#wasJustPressed(Button) wasJustPressed(Button)},
     * {@link GamepadEx#wasJustReleased(Button) wasJustReleased(Button)},
     * {@link GamepadEx#isDown(Button) isDown(Button)},
     * and {@link GamepadEx#stateJustChanged(Button) stateJustChanged(Button)} to work correctly.
     *
     * @see GamepadEx#readAxisAndButtons()
     */
    public void readButtons() {
        for (Button button : Button.values()) {
            //noinspection DataFlowIssue
            buttonReaders.get(button).readValue();
        }
    }

    /**
     * Fetches the current gamepad axis values, updating the {@link AxisReader} objects.
     * Call this once in your loop for
     * {@link GamepadEx#wasJustPressed(Axis) wasJustPressed(Axis)},
     * {@link GamepadEx#wasJustReleased(Axis) wasJustReleased(Axis)},
     * {@link GamepadEx#isDown(Axis) isDown(Axis)},
     * and {@link GamepadEx#stateJustChanged(Axis) stateJustChanged(Axis)} to work correctly.
     *
     * @see GamepadEx#readAxisAndButtons()
     */
    public void readAxis() {
        for (Axis axis : Axis.values()) {
            //noinspection DataFlowIssue
            axisReaders.get(axis).readValue();
        }
    }

    /**
     * Fetches the current gamepad axis values and button values, updating the
     * {@link AxisReader} and {@link ButtonReader} objects.
     * Shorthand for calling {@link GamepadEx#readAxis()} and {@link GamepadEx#readButtons()}
     *
     * @see GamepadEx#readButtons()
     * @see GamepadEx#readAxis()
     */
    public void readAxisAndButtons() {
        readAxis();
        readButtons();
    }

    /**
     * Returns the {@link ButtonReader} corresponding to the passed enum value.
     * @param button The {@link Button} enum value.
     * @return The {@link ButtonReader} corresponding to the passed enum value.
     */
    public ButtonReader getButtonReader(Button button) {
        return buttonReaders.get(button);
    }

    /**
     * Returns the {@link AxisReader} corresponding to the passed enum value.
     * @param axis The {@link Axis} enum value.
     * @return The {@link AxisReader} corresponding to the passed enum value.
     */
    public AxisReader getAxisReader(Axis axis) {
        return axisReaders.get(axis);
    }

    /**
     * Get a {@link GamepadButton} corresponding to the Button enum value passed.
     * Can be used to execute {@link com.arcrobotics.ftclib.command.Command Command} objects or
     * {@link Runnable} objects on a button press.
     *
     * @param button The Button enum value.
     * @return The button as a {@link GamepadButton}, which extends {@link com.arcrobotics.ftclib.command.button.Trigger Trigger}.
     */
    public GamepadButton getGamepadButton(Button button) {
        return gamepadButtons.get(button);
    }

}