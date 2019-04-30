package com.js.shipper.ui.center.activity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.DriverBean;
import com.js.shipper.model.event.AddDriverEvent;
import com.js.shipper.ui.center.adapter.DriversAdapter;
import com.js.shipper.ui.center.presenter.DriversPresenter;
import com.js.shipper.ui.center.presenter.contract.DriversContract;
import com.js.shipper.widget.dialog.AddDriverFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.js.frame.view.BaseActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/29.
 */
public class DriversActivity extends BaseActivity<DriversPresenter> implements DriversContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;


    private DriversAdapter mAdapter;
    private List<DriverBean> mDrivers;


    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mDrivers = new ArrayList<>();
        mDrivers.add(new DriverBean());
        mDrivers.add(new DriverBean());
        mDrivers.add(new DriverBean());
        mAdapter.setNewData(mDrivers);
    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new DriversAdapter(R.layout.item_mine_driver, mDrivers);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
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
        return R.layout.activity_center_drivers;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("我的司机");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_center_driver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_driver:
                showDialog();
                break;
        }
        return true;
    }

    private void showDialog() {
        AddDriverFragment driverFragment = new AddDriverFragment();
        driverFragment.show(getSupportFragmentManager(),"Add");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<DriverBean> driverBean = adapter.getData();
        switch (view.getId()){
            case R.id.item_driver_delete:

                break;
        }
    }


    @Subscribe
    public void onEvent(AddDriverEvent event){

    }
}
