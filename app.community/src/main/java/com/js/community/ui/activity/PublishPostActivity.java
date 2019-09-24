package com.js.community.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.base.frame.view.BaseActivity;
import com.base.util.manager.CommonGlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.js.community.CommunityApp;
import com.js.community.R;
import com.js.community.R2;
import com.js.community.di.componet.DaggerActivityComponent;
import com.js.community.di.module.ActivityModule;
import com.js.community.model.bean.CircleBean;
import com.js.community.model.bean.TopicBean;
import com.js.community.presenter.FilePresenter;
import com.js.community.presenter.contract.FileContract;
import com.js.community.ui.adapter.TopicAdapter;
import com.js.community.ui.presenter.PublishPostPresenter;
import com.js.community.ui.presenter.contract.PublishPostContract;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-09-11.
 * 发布帖子
 */
public class PublishPostActivity extends BaseActivity<PublishPostPresenter> implements PublishPostContract.View, BaseQuickAdapter.OnItemClickListener, FileContract.View, InvokeListener, TakePhoto.TakeResultListener {


    @BindView(R2.id.post_img)
    ImageView mPostImg;
    @BindView(R2.id.recycler)
    RecyclerView mRecycler;
    @BindView(R2.id.post_content)
    TextView mPostContent;

    private CircleBean mCircle;
    private List<TopicBean> mTopics;
    private TopicAdapter mTopicAdapter;
    private String selectTopic;
    private String imgUrl;
    private String[] items = {"拍摄", "从相册选择"};
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Inject
    FilePresenter mFilePresenter;

    public static void action(Context context, CircleBean circleBean) {
        Intent intent = new Intent(context, PublishPostActivity.class);
        intent.putExtra("circle", circleBean);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        mFilePresenter.attachView(this);
        initIntent();
        initView();
        initData();
    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        mTopicAdapter = new TopicAdapter(R.layout.item_index_topic, mTopics);
        mRecycler.setAdapter(mTopicAdapter);
        mRecycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        mTopicAdapter.setOnItemClickListener(this);
    }

    private void initData() {
        String subject = mCircle.getSubjects();
        mTopics = new ArrayList<>();
        if (!TextUtils.isEmpty(subject)) {
            String[] subjects = subject.split(",");
            for (int i = 0; i < subjects.length; i++) {
                mTopics.add(new TopicBean(false, subjects[i]));
            }
            mTopicAdapter.setNewData(mTopics);
        }
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
        return R.layout.activity_publist_post;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("发帖");
    }


    @OnClick({R2.id.post, R2.id.image_layout})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.post) {
            String content = mPostContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                toast("请输入内容");
                return;
            }

            if (TextUtils.isEmpty(this.selectTopic)) {
                toast("请选择话题");
                return;
            }
            mPresenter.addPost(mCircle.getId(), content, imgUrl, selectTopic);
        } else if (view.getId() == R.id.image_layout) {
            showDialog();
        }

    }

    @Override
    public void onAddPost() {
        toast("发帖成功");
        finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<TopicBean> topicBeans = adapter.getData();
        for (TopicBean topicBean : topicBeans) {
            topicBean.setChecked(false);
        }
        TopicBean topicBean = (TopicBean) adapter.getItem(position);
        topicBean.setChecked(true);
        adapter.setNewData(topicBeans);
        this.selectTopic = topicBean.getName();
    }

    private void showDialog() {
        new MaterialDialog.Builder(mContext)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            Uri imageUri = Uri.fromFile(file);
                            getTakePhoto().onPickFromCapture(imageUri);
                        } else {
                            getTakePhoto().onPickFromGallery();
                        }
                    }
                }).show();
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(mContext, type, invokeParam, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFilePresenter != null) {
            mFilePresenter.detachView();
        }
    }


    @Override
    public void onUploadFile(String data) {
        imgUrl = data;
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + data, mPostImg);
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result.getImage() == null) {
            return;
        }
        mFilePresenter.uploadFile(new File(result.getImage().getOriginalPath()));
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
