package com.xlgcx.doraemon.kit;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.xlgcx.doraemon.R;
import com.xlgcx.doraemon.global.Const;

/**
 * Created by huyg on 2019/2/20.
 */
public class WholeControlKit implements IKit {

    @Override
    public int getCategory() {
        return Category.BIZ;
    }

    @Override
    public int getName() {
        return R.string.whole_control;
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_api_switch;
    }

    @Override
    public void onClick(Context context) {
//        ARouter.getInstance().build("/user/login").navigation();
//        ARouter.getInstance().build("/harmharm/activity").navigation();
        ARouter.getInstance().build("/control/control").withInt("type", Const.BUSINESS_WHOLE).navigation();
    }

    @Override
    public void onAppInit(Context context) {

    }
}
