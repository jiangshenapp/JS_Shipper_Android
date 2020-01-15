package com.js.community.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.frame.global.Const;
import com.base.frame.view.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.model.bean.CircleBean;
import com.js.community.model.bean.Member;
import com.js.community.ui.adapter.AllCircleAdapter;
import com.js.community.ui.adapter.PostAdapter;
import com.js.community.ui.presenter.FindCirclePresenter;
import com.js.community.ui.presenter.contract.FindCircleContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019-09-09.
 */
public class FindCircleActivity extends BaseActivity<FindCirclePresenter> implements FindCircleContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R2.id.et_search_no)
    EditText mSearch;

    private AllCircleAdapter mAdapter;
    private List<CircleBean> mCircles;
    private int showSide;
    private List<CircleBean> searchCircle = new ArrayList<>();
    private String selectCode = "330200";
    private String selectCity = "宁波市";
    private MenuItem moreItem;

    public static void action(Context context, int showSide) {
        Intent intent = new Intent(context, FindCircleActivity.class);
        intent.putExtra("showSide", showSide);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        showSide = getIntent().getIntExtra("showSide", 1);
    }

    private void initView() {
        initRecycler();
        initRefresh();
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mAdapter.setNewData(mCircles);
                    return;
                }
                if (mCircles != null && mCircles.size() > 0) {
                    searchCircle.clear();
                    for (CircleBean circleBean : mCircles) {
                        if (circleBean.getName().contains(s)) {
                            searchCircle.add(circleBean);
                        }
                    }
                    mAdapter.setNewData(searchCircle);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRefresh() {
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getAllCircle(selectCode, showSide);
            }
        });
    }

    private void initRecycler() {
        mAdapter = new AllCircleAdapter(R.layout.item_find_circle, mCircles);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemClickListener(this);
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
        return R.layout.activity_find_circle;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("找圈子");
    }

    @Override
    public void onAllCircle(List<CircleBean> circleBeans) {
        this.mCircles = circleBeans;
        mAdapter.setNewData(circleBeans);
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }

    @Override
    public void onApplayCircle(Boolean b) {
        if (b) {
            toast("申请成功");
            finish();
        } else {
            toast("申请失败");
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CircleBean circleBean = (CircleBean) adapter.getItem(position);
        if (TextUtils.isEmpty(circleBean.getApplyStatus()) || "3".equals(circleBean.getApplyStatus())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("加入圈子");
            builder.setMessage("是否加入？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mPresenter.applyCircle(circleBean.getId());
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 999:
                if (data != null) {
                    selectCode = data.getStringExtra("code");
                    selectCity = data.getStringExtra("city");
                    moreItem.setTitle(selectCity);
                    mPresenter.getAllCircle(selectCode, showSide);
                }
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ARouter.getInstance().build("/city/select").navigation(mContext, 999);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (CommunityApp.getApp().mLocation != null) {
            selectCity = CommunityApp.getApp().mLocation.getCity();
            selectCode = CommunityApp.getApp().mLocation.getAdCode();
            selectCode = selectCode.substring(0, 4) + "00";
            mPresenter.getAllCircle(selectCode,showSide);
        }
        moreItem = menu.add(Menu.NONE, R.id.city, Menu.FIRST, selectCity);
        moreItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
}
