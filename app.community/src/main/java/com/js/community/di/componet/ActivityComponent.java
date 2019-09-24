package com.js.community.di.componet;

import android.app.Activity;

import com.js.community.di.ActivityScope;
import com.js.community.di.module.ActivityModule;
import com.js.community.ui.activity.CircleIndexActivity;
import com.js.community.ui.activity.CommentActivity;
import com.js.community.ui.activity.FindCircleActivity;
import com.js.community.ui.activity.MemberManageActivity;
import com.js.community.ui.activity.PostDetailActivity;
import com.js.community.ui.activity.PublishPostActivity;

import dagger.Component;


/**
 * Created by huyg on 2018/8/22.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();


    void inject(FindCircleActivity findCircleActivity);

    void inject(CircleIndexActivity circleIndexActivity);

    void inject(PostDetailActivity postDetailActivity);

    void inject(PublishPostActivity publishPostActivity);

    void inject(MemberManageActivity memberManageActivity);

    void inject(CommentActivity commentActivity);


}

