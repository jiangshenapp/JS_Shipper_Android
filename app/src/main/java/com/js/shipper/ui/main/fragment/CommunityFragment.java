package com.js.shipper.ui.main.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.ui.main.adapter.CircleAdapter;
import com.js.shipper.ui.main.presenter.CommunityPresenter;
import com.js.shipper.ui.main.presenter.contract.CommunityContract;
import com.js.shipper.widget.adapter.Divider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/1.
 * 社区
 */
public class CommunityFragment extends BaseFragment<CommunityPresenter> implements CommunityContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private CircleAdapter mAdapter;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }


    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CircleAdapter(R.layout.item_community_circle,null);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
//        mRecycler.addItemDecoration(new Divider());
    }

    @OnClick({R.id.favorite, R.id.comment, R.id.find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.favorite://我的喜欢

                break;
            case R.id.comment://评论

                break;
            case R.id.find://找圈子

                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
