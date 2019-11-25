package com.xlgcx.doraemon.kit;

import android.content.Context;
import android.content.Intent;

import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.constant.FragmentIndex;
import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.xlgcx.doraemon.R;
import com.xlgcx.doraemon.activity.ApiSwitchActivity;

/**
 * Created by huyg on 2019/2/20.
 */
public class ApiSwitchKit implements IKit {

    @Override
    public int getCategory() {
        return Category.BIZ;
    }

    @Override
    public int getName() {
        return R.string.api_switch;
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_api_switch;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, ApiSwitchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_CRASH);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {

    }
}
