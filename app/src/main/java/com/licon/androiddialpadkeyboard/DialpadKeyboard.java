package com.licon.androiddialpadkeyboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * Created by FRAMGIA\khairul.alam.licon on 25/10/17.
 */
public class DialpadKeyboard extends InputMethodService
    implements KeyboardView.OnKeyboardActionListener {
    private static final int KEYCODE_CALL = -999;
    private static final int KEYCODE_PLUS = -998;
    private static final String KEY_VALUE_PLUS = "+";
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;

    @Override
    public View onCreateInputView() {
        mKeyboardView =
            (KeyboardView) getLayoutInflater().inflate(R.layout.layout_keyboard_view, null);
        mKeyboard = new Keyboard(this, R.xml.qwerty);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(this);
        return mKeyboardView;
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                inputConnection.deleteSurroundingText(1, 0);
                break;
            case KEYCODE_CALL:
                if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED)
                    || (TextUtils.isEmpty(inputConnection.getTextBeforeCursor(20, 0).toString())))
                    return;
                Intent intentCall = new Intent(Intent.ACTION_CALL);
                intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String number = "tel:" + inputConnection.getTextBeforeCursor(20, 0).toString();
                intentCall.setData(Uri.parse(number));
                startActivity(intentCall);
                break;
            case KEYCODE_PLUS:
                inputConnection.commitText(KEY_VALUE_PLUS, 1);
                break;
            default:
                char code = (char) primaryCode;
                inputConnection.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeUp() {
    }
}
