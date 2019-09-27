package com.js.login.di.module;

import android.app.Activity;

import androidx.fragment.app.Fragment;


import com.js.login.di.FragmentScope;

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
