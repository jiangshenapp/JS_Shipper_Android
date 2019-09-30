package com.js.community.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseActivity;
import com.base.util.manager.SpManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.model.bean.CircleBean;
import com.js.community.model.bean.PostBean;
import com.js.community.model.bean.TopicBean;
import com.js.community.ui.adapter.PostAdapter;
import com.js.community.ui.adapter.TopicAdapter;
import com.js.community.ui.presenter.CircleIndexPresenter;
import com.js.community.ui.presenter.contract.CircleIndexContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-09-04.
 */
public class CircleIndexActivity extends BaseActivity<CircleIndexPresenter> implements CircleIndexContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.topic_recycler)
    RecyclerView mTopicRecycler;
    @BindView(R2.id.recycler)
    RecyclerView mRecycler;


    @OnClick(R2.id.post)
    public void onClick() {
        int authState = SpManager.getInstance(this).getIntSP("personConsignorVerified");
        if (authState != 0 || authState != 3) { //0:未认证
            toast("未认证");
            return;
        }
        PublishPostActivity.action(mContext, mCircle);
    }

    private CircleBean mCircle;

    private TopicAdapter mTopicAdapter;
    private PostAdapter mPostAdapter;
    private List<PostBean> mPosts;
    private List<TopicBean> mTopics;


    public static void action(Context context, CircleBean circleBean) {
        Intent intent = new Intent(context, CircleIndexActivity.class);
        intent.putExtra("circle", circleBean);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_circle_more, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.more) {
            MemberManageActivity.action(mContext, mCircle);
        }
        return true;
    }

    private void initData() {
        String subject = mCircle.getSubjects();
        mTopics = new ArrayList<>();
        mTopics.add(new TopicBean(true, "全部"));
        if (!TextUtils.isEmpty(subject)) {
            String[] subjects = subject.split(",");
            for (int i = 0; i < subjects.length; i++) {
                mTopics.add(new TopicBean(false, subjects[i]));
            }
        }
        mTopicAdapter.setNewData(mTopics);
        mPresenter.getPosts(mCircle.getId(), "", false, false, false);
    }

    private void initView() {
        mTitle.setText(mCircle.getName());
        initRecycler();
    }

    private void initRecycler() {
        mTopicAdapter = new TopicAdapter(R.layout.item_index_topic, mTopics);
        mTopicRecycler.setAdapter(mTopicAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        mTopicRecycler.setLayoutManager(manager);
        mTopicAdapter.setOnItemClickListener(this);

        mPostAdapter = new PostAdapter(R.layout.item_index_circle, mPosts);
        mRecycler.setAdapter(mPostAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mPostAdapter.setOnItemClickListener(this);
    }

    private void initIntent() {
        mCircle = getIntent().getParcelableExtra("circle");
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(CommunityApp.getApp().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_index;
    }

    @Override
    public void setActionBar() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof TopicAdapter) {
            List<TopicBean> topicBeans = adapter.getData();
            for (TopicBean topicBean : topicBeans) {
                topicBean.setChecked(false);
            }
            TopicBean topicBean = (TopicBean) adapter.getItem(position);
            topicBean.setChecked(true);
            adapter.setNewData(topicBeans);
            if ("全部".equals(topicBean.getName())) {
                mPresenter.getPosts(mCircle.getId(), "", false, false, false);
            } else {
                mPresenter.getPosts(mCircle.getId(), topicBean.getName(), false, false, false);
            }
        } else if (adapter instanceof PostAdapter) {
            PostBean postBean = (PostBean) adapter.getItem(position);
            PostDetailActivity.action(mContext, postBean);
        }
    }

    @Override
    public void onPosts(List<PostBean> postBeans) {
        mPostAdapter.setNewData(postBeans);
    }
}
