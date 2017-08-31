package com.frozensparks.hellofriend.Tools;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Emanuel Graf on 29.08.2017.
 */

public class BaseBackPressedListener implements OnBackPressedListener {
    private final FragmentActivity activity;

    public BaseBackPressedListener(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void doBack() {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
