package com.js.community.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.frame.view.BaseActivity;
import com.base.util.manager.SpManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.community.CommunityApp;
import com.js.community.MainActivity;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.model.bean.CircleBean;
import com.js.community.model.bean.Member;
import com.js.community.ui.adapter.MemberAdapter;
import com.js.community.ui.presenter.MemberManagePresenter;
import com.js.community.ui.presenter.contract.MemberManageContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-09-11.
 */
public class MemberManageActivity extends BaseActivity<MemberManagePresenter> implements MemberManageContract.View, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R2.id.et_search_no)
    EditText mSearch;

    @OnClick({R2.id.member_quit})
    public void onClick(View view) {
        if (view.getId()==R.id.member_quit){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("退出圈子");
            builder.setMessage("是否退出？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.exitCircle(mCircle.getId());
                    dialog.dismiss();
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

    private CircleBean mCircle;
    private MemberAdapter mAdapter;
    private List<Member> members;
    private List<Member> searchMember = new ArrayList<>();


    public static void action(Context context, CircleBean circleBean) {
        Intent intent = new Intent(context, MemberManageActivity.class);
        intent.putExtra("circle", circleBean);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initIntent();
        initView();

    }

    private void initView() {
        mTitle.setText(mCircle.getName());
        initRecycler();
        initRefresh();
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mAdapter.setNewData(members);
                    return;
                }
                if (members != null && members.size() > 0) {
                    searchMember.clear();
                    for (Member member : members) {
                        if (member.getNickName().contains(s)) {
                            searchMember.add(member);
                        }
                    }
                    mAdapter.setNewData(searchMember);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRefresh() {
        mRefresh.setEnableLoadMore(false);
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMembers(mCircle.getId());
            }
        });
    }

    private void initRecycler() {
        mAdapter = new MemberAdapter(R.layout.item_member, members);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setAdmin(mCircle.getAdmin() == SpManager.getInstance(this).getIntSP("id"));
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
        return R.layout.activity_member_list;
    }

    @Override
    public void setActionBar() {

    }

    @Override
    public void onMembers(List<Member> members) {
        this.members = members;
        mAdapter.setNewData(members);
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }

    @Override
    public void onAuditApply(boolean b) {
        if (b) {
            mPresenter.getMembers(mCircle.getId());
        }
    }

    @Override
    public void onDeleteSubscriber(boolean b) {
        if (b) {
            mPresenter.getMembers(mCircle.getId());
        }
    }

    @Override
    public void onExitCircle(boolean b) {
        if (b){
            ARouter.getInstance().build("/app/main").navigation();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Member member = (Member) adapter.getItem(position);
        if (view.getId() == R.id.item_agree) {
            mPresenter.auditApplyCircle(member.getId(), "1");
        } else if (view.getId() == R.id.item_refuse) {
            mPresenter.auditApplyCircle(member.getId(), "2");
        } else if (view.getId() == R.id.item_delete) {
            mPresenter.deleteSubscriber(member.getId());
        }
    }
}
