package com.js.shipper.ui.user.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.base.frame.view.BaseActivity;
import com.js.login.ui.activity.BindStatusActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.global.Const;
import com.js.shipper.util.glide.CommonGlideImageLoader;
import com.js.shipper.util.UserManager;
import com.js.shipper.model.event.UserStatusChangeEvent;
import com.js.shipper.presenter.FilePresenter;
import com.js.shipper.presenter.contract.FileContract;
import com.js.shipper.ui.center.activity.AboutActivity;
import com.js.shipper.ui.center.activity.FeedBackActivity;
import com.js.shipper.ui.user.presenter.UserCenterPresenter;
import com.js.shipper.ui.user.presenter.contract.UserCenterContract;
import com.js.shipper.util.DataCleanManager;
import com.js.shipper.widget.dialog.AppDialogFragment;
import com.plugin.im.IMHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/24.
 */
public class UserCenterActivity extends BaseActivity<UserCenterPresenter> implements UserCenterContract.View, FileContract.View, TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_verified)
    TextView tvVerified;

    @Inject
    FilePresenter mFilePresenter;

    private int choseCode;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private String avatar;
    private String nickname;
    private String[] items = {"拍摄", "从相册选择"};

    public static void action(Context context) {
        context.startActivity(new Intent(context, UserCenterActivity.class));
    }

    @Override
    protected void init() {
        mFilePresenter.attachView(this);
        initView();
    }

    private void initView() {
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, com.base.http.global.Const.IMG_URL + App.getInstance().avatar
                , ivHead, mContext.getResources().getDrawable(R.mipmap.ic_center_shipper_head_land));
        tvNick.setText(App.getInstance().nickName);
        try {
            tvCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {

        }
        initVersion();
        if (App.getInstance().personConsignorVerified == 3 || App.getInstance().companyConsignorVerified == 3) {
            tvVerified.setText("认证失败");
            return;
        }

        if (App.getInstance().personConsignorVerified == 2 || App.getInstance().companyConsignorVerified == 2) {
            tvVerified.setText("已认证");
            return;
        }

        if (App.getInstance().personConsignorVerified == 1 || App.getInstance().companyConsignorVerified == 1) {
            tvVerified.setText("认证中");
            return;
        }

        if (App.getInstance().personConsignorVerified == 0 && App.getInstance().companyConsignorVerified == 0) {
            tvVerified.setText("未提交");
            return;
        }
    }

    private void initVersion() {
        PackageManager manager = mContext.getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvVersion.setText(String.format("V%s", name));
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
        return R.layout.activity_user_center;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("用户中心");
    }


    @OnClick({R.id.center_avatar_layout, R.id.center_name_layout,
            R.id.center_verified_layout, R.id.center_feedback_layout,
            R.id.center_version_layout, R.id.center_about_layout,
            R.id.center_cache_layout, R.id.logout,
            R.id.bind_status_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.center_avatar_layout://头像
                getPhoto(Const.UPLOAD_HEADIMG);
                XXPermissions.with(mContext)
                        //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                        .permission(
                                Permission.CAMERA)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean isAll) {
                                if (isAll) {
                                    showDialog();
                                }
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean quick) {
                                toast("请同意权限");
                            }
                        });
                break;
            case R.id.center_name_layout://昵称
                changeNickname();
                break;
            case R.id.center_verified_layout://认证管理
                VerifiedActivity.action(mContext);
                break;
            case R.id.center_feedback_layout://意见反馈
                FeedBackActivity.action(this);
                break;
            case R.id.center_version_layout://版本号
                break;
            case R.id.center_about_layout://关于
                AboutActivity.action(this);
                break;
            case R.id.center_cache_layout://清除缓存
                clearCache();
                break;
            case R.id.logout://登出
                IMHelper.getInstance().logout();
                UserManager.getUserManager().logout(mContext);
                finish();
                break;
            case R.id.bind_status_layout:
                BindStatusActivity.action(mContext);
                break;
        }
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


    public void changeNickname() {
        final EditText inputServer = new EditText(this);
        inputServer.setText(tvNick.getText());
        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改昵称").setIcon(null).setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String nick = inputServer.getText().toString();
                if (nick != null && !nick.isEmpty()) {
                    nickname = nick;
                    mPresenter.changeNickname(nickname);
                } else {
                    toast("请输入昵称");
                }
            }
        });
        builder.show();
    }

    public void clearCache() {
        AppDialogFragment appDialogFragment = AppDialogFragment.getInstance();
        appDialogFragment.setTitle("温馨提示");
        appDialogFragment.setMessage("确定清除" + tvCache.getText() + "缓存吗？");
        appDialogFragment.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(App.getInstance());
                try {
                    tvCache.setText(DataCleanManager.getTotalCacheSize(App.getInstance()));
                } catch (Exception e) {

                }
            }
        });
        appDialogFragment.show(getSupportFragmentManager(), "appDialog");
    }

    @Override
    public void onUploadFile(String data) {
        Log.d(getClass().getSimpleName(), data);
        switch (choseCode) {
            case Const.UPLOAD_HEADIMG:
                avatar = data;
                mPresenter.changeAvatar(data);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFilePresenter != null) {
            mFilePresenter.detachView();
        }
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
        Log.d(getClass().getSimpleName(), msg);
    }

    @Override
    public void takeCancel() {
        Log.d(getClass().getSimpleName(), "takeCancel");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public void getPhoto(int choseCode) {
        this.choseCode = choseCode;
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void onChangeAvatar() {
        toast("头像修改成功");
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, com.base.http.global.Const.IMG_URL + avatar, ivHead);
        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.CHANGE_SUCCESS));
    }

    @Override
    public void onChangeNickname() {
        toast("昵称修改成功");
        tvNick.setText(nickname);
        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.CHANGE_SUCCESS));
    }
}
