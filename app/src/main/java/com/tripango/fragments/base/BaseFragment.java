package com.tripango.fragments.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripango.activities.base.BaseActivity;
import com.tripango.utils.LocalStorage;
import com.tripango.utils.UIUtils;

/**
 * Base class for all fragments.
 * <p/>
 * Created by Toni Saarela on 11/16/2015.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;
    protected LocalStorage localStorage;
    protected UIUtils utils;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            baseActivity = (BaseActivity) context;
            localStorage = LocalStorage.getInstance();
            utils = UIUtils.getInstance();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must extend BaseActivity");
        }
    }

    /**
     * Initialize data by derived class.
     */
    protected abstract void initData();

    /**
     * Initialize all components by derived class.
     *
     * @param container Container View object.
     */
    protected abstract void initComponents(View container);

    @Override
    public void onDestroyView() {
        baseActivity.hideKeyboard();

        super.onDestroyView();
    }

    public boolean backButtonPressed() {
        return true;
    }

    /**
     * @param inflater  Inflater object
     * @param container ViewGroup container
     * @param resource  Resource id for inflation
     * @return true if view was inflated
     */
    protected boolean inflateViewIfNull(LayoutInflater inflater, ViewGroup container, int resource) {
        if (rootView == null) {
            rootView = inflater.inflate(resource, container, false);
            return true;
        } else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
            return false;
        }
    }
}

