package com.js.shipper.ui.main.fragment;

import com.js.frame.view.SimpleFragment;
import com.js.shipper.R;

/**
 * Created by huyg on 2019/4/30.
 */
public class ParkFragment extends SimpleFragment {

    public static ParkFragment newInstance() {
        return new ParkFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_park;
    }

    @Override
    protected void init() {

    }
}
