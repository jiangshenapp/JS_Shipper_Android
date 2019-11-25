package com.xlgcx.doraemon.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.base.frame.view.SimpleActivity;
import com.base.util.manager.SpManager;
import com.xlgcx.doraemon.R;
import com.xlgcx.doraemon.R2;
import com.xlgcx.doraemon.adapter.ServerAdapter;
import com.xlgcx.doraemon.bean.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019/2/21.
 */
public class ApiSwitchActivity extends SimpleActivity {


    @BindView(R2.id.ll_server)
    ListView mListView;

    private ServerAdapter listAdapter;
    private List<Server> list;
    private String defaultHost;


    Server[] servers = new Server[]{
            new Server("https://gateway.jiangshen56.com/logistic-biz/", "线上环境")
            , new Server("http://testway.jiangshen56.com/logistic-biz/", "测试环境")
    };


    @Override
    protected int getLayout() {
        return R.layout.activity_api_switch;
    }

    @Override
    protected void init() {
        initData();
        initView();
    }

    private void initData() {
        defaultHost = SpManager.getInstance(mContext).getSP("host");
        if (TextUtils.isEmpty(defaultHost)){
            defaultHost = "http://testway.jiangshen56.com/logistic-biz/";
        }
        list = new ArrayList<Server>();
        Collections.addAll(list, servers);
    }

    private void initView() {
        listAdapter = new ServerAdapter(mContext, defaultHost);
        listAdapter.select(defaultHost);
        listAdapter.setList(list);
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String host = list.get(i).host;
                listAdapter.select(host);
                SpManager.getInstance(mContext).putSP("host",host);
            }
        });
    }

    @Override
    public void setActionBar() {
        mTitle.setText("环境切换");
    }
}
