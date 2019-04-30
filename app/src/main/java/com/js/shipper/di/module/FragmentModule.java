package com.js.shipper.di.module;

import android.app.Activity;

import com.js.shipper.di.FragmentScope;

import androidx.fragment.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by huyg on 2018/8/22.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity ProvideActivity(){
        return mFragment.getActivity();
    }
}
