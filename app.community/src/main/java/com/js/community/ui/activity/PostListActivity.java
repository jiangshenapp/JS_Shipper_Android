package com.js.community.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.base.frame.view.SimpleActivity;
import com.google.android.material.tabs.TabLayout;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.ui.fragment.PostFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019-09-14.
 */
public class PostListActivity extends SimpleActivity {
    @BindView(R2.id.tablayout)
    TabLayout mTablayout;
    @BindView(R2.id.viewpager)
    ViewPager mViewpager;

    private List<Fragment> mFragments;
    private String[] titles = {"发布", "点赞", "评论"};
    private int type;

    public static void action(Context context, int type) {
        Intent intent = new Intent(context, PostListActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_post_list;
    }

    @Override
    protected void init() {
        initIntent();
        initFragment();
        initView();
        mViewpager.setCurrentItem(type);
    }

    private void initIntent() {
        type = getIntent().getIntExtra("type",0);
    }

    private void initView() {
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        mTablayout.setupWithViewPager(mViewpager,true);

    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(PostFragment.newInstance(false, false, true));
        mFragments.add(PostFragment.newInstance(false, true, false));
        mFragments.add(PostFragment.newInstance(true, false, false));
    }

    @Override
    public void setActionBar() {
        mTitle.setText("帖子列表");
    }

}
