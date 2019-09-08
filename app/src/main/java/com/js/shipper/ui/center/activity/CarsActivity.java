package com.js.shipper.ui.center.activity;

import android.view.Menu;
import android.view.MenuItem;

import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.ui.center.adapter.CarsAdapter;
import com.js.shipper.ui.center.presenter.CarsPresenter;
import com.js.shipper.ui.center.presenter.contract.CarsContract;
import com.js.shipper.widget.adapter.Divider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.base.frame.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/29.
 */
public class CarsActivity extends BaseActivity<CarsPresenter> implements CarsContract.View {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private CarsAdapter mAdapter;
    private List<CarBean> mCarBeans;


    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mCarBeans = new ArrayList<>();
        mCarBeans.add(new CarBean());
        mCarBeans.add(new CarBean());
        mAdapter.setNewData(mCarBeans);
    }

    private void initView() {
        initRecycler();
        initRefresh();
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    private void initRecycler() {
        mAdapter = new CarsAdapter(R.layout.item_mine_car, mCarBeans);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
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
                AddCarActivity.action(mContext);
                break;
        }
        return true;
    }

}
