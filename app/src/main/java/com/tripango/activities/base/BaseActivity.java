package com.tripango.activities.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tripango.R;
import com.tripango.fragments.base.BaseFragment;
import com.tripango.utils.LocalStorage;
import com.tripango.utils.UIUtils;

import java.util.UUID;

/**
 * Base class for all activities.
 * <p/>
 * Created by Toni Saarela on 11/16/2015.
 */
public class BaseActivity extends AppCompatActivity {

    protected BaseFragment rootFragment;
    protected BaseFragment activeFragment;
    protected LocalStorage localStorage;
    protected UIUtils utils;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        localStorage = LocalStorage.getInstance();
        utils = UIUtils.getInstance();
    }

    private CUSTOM_ANIMATIONS custom_animation = CUSTOM_ANIMATIONS.FADE_IN;

    public enum CUSTOM_ANIMATIONS {
        FADE_IN, SLIDE_FROM_LEFT, SLIDE_FROM_RIGHT, SLIDE_FROM_TOP, SLIDE_FROM_BOTTOM
    }

    public void showFragment(int contentFrame, BaseFragment fragment) {
        showFragment(contentFrame, fragment, false);
    }

    public void showFragment(int contentFrame, BaseFragment fragment, boolean addToBackStack) {
        this.activeFragment = fragment;
        String tag = UUID.randomUUID().toString();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (custom_animation) {
            case FADE_IN:
                transaction.setCustomAnimations(R.anim.anim_fade_in,
                        R.anim.anim_fade_out, R.anim.anim_fade_in,
                        R.anim.anim_fade_out);
                break;
            case SLIDE_FROM_LEFT:
                transaction.setCustomAnimations(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_right, R.anim.anim_fade_in,
                        R.anim.anim_fade_out);
                break;
            case SLIDE_FROM_RIGHT:
                transaction.setCustomAnimations(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_left, R.anim.anim_fade_in,
                        R.anim.anim_fade_out);
                break;
            case SLIDE_FROM_BOTTOM:
                transaction.setCustomAnimations(R.anim.anim_slide_in_bottom,
                        R.anim.anim_slide_out_top, R.anim.anim_fade_in,
                        R.anim.anim_fade_out);
                break;
            case SLIDE_FROM_TOP:
                transaction.setCustomAnimations(R.anim.anim_slide_in_top,
                        R.anim.anim_slide_out_bottom, R.anim.anim_fade_in,
                        R.anim.anim_fade_out);
                break;
            default:
                transaction.setCustomAnimations(R.anim.anim_fade_in,
                        R.anim.anim_fade_out, R.anim.anim_fade_in,
                        R.anim.anim_fade_out);
                break;
        }

        transaction.replace(contentFrame, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            rootFragment = fragment;
    }

    /**
     * Set custom_animation for fragment transaction
     */
    public void setCustomAnimation(CUSTOM_ANIMATIONS custom_animation) {
        this.custom_animation = custom_animation;
    }

    /**
     * Enables the cleanup of all stack before adding this fragment. Can be
     * useful to make the Dash-board or other fragment the base fragment in
     * terms of order
     *
     * @param contentFrame   Layout Resource ID to replace fragment
     * @param fragment       BaseFragment object to replace
     * @param addToBackStack Determines to add to fragment back-stack
     * @param remove         If true, cleanup all stack
     */
    public void showFragment(int contentFrame, BaseFragment fragment,
                             boolean addToBackStack, boolean remove) {

        if (remove) {
            this.popToRoot();
        }
        showFragment(contentFrame, fragment, addToBackStack);
    }

    public void popToRoot() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = getSupportFragmentManager().getBackStackEntryAt(i).getId();
            getSupportFragmentManager().popBackStack(backStackId,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        activeFragment = null;
    }

    @Override
    public void onBackPressed() {
        if (activeFragment != null) {
            if (activeFragment.backButtonPressed()) {
                super.onBackPressed();
                int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backStackCount > 0) {
                    String tag = getSupportFragmentManager()
                            .getBackStackEntryAt(backStackCount - 1).getName();
                    activeFragment = (BaseFragment) getSupportFragmentManager()
                            .findFragmentByTag(tag);
                } else {
                    if (activeFragment.equals(rootFragment))
                        activeFragment = null;
                    else
                        activeFragment = rootFragment;
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * * Hide soft keyboard ***
     */
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = getCurrentFocus();
        if (view == null)
            return;

        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

