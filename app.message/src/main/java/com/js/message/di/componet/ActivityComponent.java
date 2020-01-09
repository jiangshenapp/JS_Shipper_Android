package com.js.message.di.componet;

import android.app.Activity;
import com.js.message.di.ActivityScope;
import com.js.message.di.module.ActivityModule;
import com.js.message.ui.activity.MessageActivity;
import com.js.message.ui.activity.MessageDetailActivity;
import com.js.message.ui.activity.PushActivity;
import com.js.message.ui.activity.PushDetailActivity;
import com.js.message.ui.chat.EaseChatActivity;

import dagger.Component;

/**
 * Created by huyg on 2018/8/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MessageActivity messageActivity);

    void inject(MessageDetailActivity messageDetailActivity);

    void inject(PushActivity pushActivity);

    void inject(PushDetailActivity pushDetailActivity);

    void inject(EaseChatActivity easeChatActivity);
}

