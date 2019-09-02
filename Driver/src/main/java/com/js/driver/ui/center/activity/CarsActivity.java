package com.js.driver.ui.center.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.global.Const;
import com.js.driver.model.bean.CarBean;
import com.js.driver.model.response.ListResponse;
import com.js.driver.ui.center.adapter.CarsAdapter;
import com.js.driver.ui.center.presenter.CarsPresenter;
import com.js.driver.ui.center.presenter.contract.CarsContract;
import com.js.driver.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.js.frame.view.BaseActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

/**
 * Created by huyg on 2019/4/29.
 */
public class CarsActivity extends BaseActivity<CarsPresenter> implements CarsContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private CarsAdapter mAdapter;
    private List<CarBean> mCarBeans;
    private boolean isJump;

    public static void action(Context context) {
        Intent intent = new Intent(context, CarsActivity.class);
        context.startActivity(intent);
    }


    public static void action(Activity context, boolean isJump) {
        Intent intent = new Intent(context, CarsActivity.class);
        intent.putExtra("isJump", isJump);
        context.startActivityForResult(intent, Const.CODE_REQ);
    }

    private void initIntent() {
        isJump = getIntent().getBooleanExtra("isJump", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initData() {
        mPresenter.getCarList();
    }

    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCarList();
            }
        });
    }

    private void initRecycler() {
        mAdapter = new CarsAdapter(R.layout.item_mine_car, mCarBeans);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mAdapter.setEmptyView(R.layout.layout_data_empty,mRecycler);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_car;
    }


    @Override
    public void setActionBar() {
        mTitle.setText("我的车辆");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_center_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_car:
                AddCarActivity.action(mContext, 1, 0);
                break;
        }
        return true;
    }

    @Override
    public void onCarList(ListResponse<CarBean> response) {
        mCarBeans = response.getRecords();
        mAdapter.setNewData(mCarBeans);
    }

    @Override
    public void finishRefreshAndLoadMore() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<CarBean> carBeans = adapter.getData();
        CarBean carBean = carBeans.get(position);
        if (isJump) {
            AddCarActivity.action(this, 2, carBean.getId());
        } else {
            Intent intent = new Intent();
            intent.putExtra("car", carBean);
            setResult(Const.CODE_RESULT, intent);
            finish();
        }

    }
}
