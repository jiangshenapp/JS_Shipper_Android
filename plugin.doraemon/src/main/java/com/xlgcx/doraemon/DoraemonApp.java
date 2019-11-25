package com.xlgcx.doraemon;
import android.app.Application;
import android.content.Context;

import com.base.frame.module.IAppLife;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.kit.IKit;
import com.xlgcx.doraemon.kit.ApiSwitchKit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyg on 2019/2/20.
 */

public class DoraemonApp implements IAppLife {

    private List<IKit> mList = new ArrayList<>();
    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        if (BuildConfig.DEBUG){
            mList.add(new ApiSwitchKit());
            DoraemonKit.install(application,mList);
        }
    }


    @Override
    public void onTerminate(Application application) {

    }
}
