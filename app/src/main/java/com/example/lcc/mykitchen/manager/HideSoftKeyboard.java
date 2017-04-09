package com.example.lcc.mykitchen.manager;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**隐藏软键盘
 * Created by lcc on 2016/12/15.
 */
public class HideSoftKeyboard {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
