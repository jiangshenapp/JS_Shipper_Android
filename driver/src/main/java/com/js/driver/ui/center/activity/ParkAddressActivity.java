package com.js.driver.ui.center.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.base.frame.view.BaseActivity;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.global.Const;
import com.js.driver.model.bean.AuthInfo;
import com.js.driver.model.bean.LocationBean;
import com.js.driver.presenter.FilePresenter;
import com.js.driver.presenter.contract.FileContract;
import com.js.driver.ui.center.presenter.ParkAddressPresenter;
import com.js.driver.ui.center.presenter.contract.ParkAddressContract;
import com.jph.takephoto.permission.InvokeListener;
import com.js.driver.ui.user.presenter.ParkUserVerifiedPresenter;
import com.js.driver.ui.user.presenter.contract.ParkUserVerifiedContract;
import com.js.driver.util.glide.CommonGlideImageLoader;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/23
 * desc   : 园区地址
 * version: 3.0.0
 */
public class ParkAddressActivity extends BaseActivity<ParkAddressPresenter> implements ParkAddressContract.View, ParkUserVerifiedContract.View, FileContract.View, TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.iv_arrow_address)
    ImageView ivArrowAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.submit)
    TextView submit;

    @Inject
    FilePresenter mFilePresenter;
    @Inject
    ParkUserVerifiedPresenter mParkUserVerifiedPresenter;

    private int choseCode;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private String[] items = {"拍摄","从相册选择"};
    private AuthInfo mAuthInfo;
    private LocationBean mLocationBean;

    public static void action(Context context) {
        Intent intent = new Intent(context, ParkAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        mAuthInfo = new AuthInfo();
        mFilePresenter.attachView(this);
        mParkUserVerifiedPresenter.attachView(this);
        mParkUserVerifiedPresenter.getParkUserVerifiedInfo();
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
        return R.layout.activity_park_address;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("园区地址");
    }

    @Override
    public void onParkUserVerifiedInfo(AuthInfo authInfo) {
        mAuthInfo = authInfo;
        mLocationBean = new Gson().fromJson(mAuthInfo.getContactLocation(), LocationBean.class);
        etName.setText(mAuthInfo.getContactName());
        etPhone.setText(mAuthInfo.getContractPhone());
        etAddress.setText(mLocationBean.getAddress());
        etDetailAddress.setText(mAuthInfo.getContactAddress());
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mAuthInfo.getImage1(), img1);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mAuthInfo.getImage2(), img2);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mAuthInfo.getImage3(), img3);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + mAuthInfo.getImage4(), img4);
    }

    @Override
    public void onSubmitParkUserVerified() {

    }

    @OnClick({R.id.ll_address, R.id.et_address, R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
            case R.id.et_address:
                Intent startIntent = new Intent(mContext, SelectAddressActivity.class);
                startIntent.putExtra("location", mLocationBean);
                startActivityForResult(startIntent, Const.CODE_REQ);
                break;
            case R.id.img1:
                getPhoto(Const.PARK_HEAD1);
                break;
            case R.id.img2:
                getPhoto(Const.PARK_HEAD2);
                break;
            case R.id.img3:
                getPhoto(Const.PARK_HEAD3);
                break;
            case R.id.img4:
                getPhoto(Const.PARK_HEAD4);
                break;
            case R.id.submit:

                break;
        }
    }

    @Override
    public void onUploadFile(String data) {
        Log.d(getClass().getSimpleName(), data);
        switch (choseCode) {
            case Const.PARK_HEAD1:
                mAuthInfo.setImage1(data);
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + data, img1);
                break;
            case Const.PARK_HEAD2:
                mAuthInfo.setImage2(data);
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + data, img2);
                break;
            case Const.PARK_HEAD3:
                mAuthInfo.setImage3(data);
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + data, img3);
                break;
            case Const.PARK_HEAD4:
                mAuthInfo.setImage4(data);
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL + data, img4);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.CODE_REQ && resultCode == 888) {
            if (data != null) {
                mLocationBean = data.getParcelableExtra("location");
                etAddress.setText(mLocationBean.getAddress());
            }
        } else {
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        }
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
        if (mParkUserVerifiedPresenter != null) {
            mParkUserVerifiedPresenter.detachView();
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
        showDialog();
    }

    private void showDialog(){
        new MaterialDialog.Builder(mContext)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position==0){
                            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            Uri imageUri = Uri.fromFile(file);
                            getTakePhoto().onPickFromCapture(imageUri);
                        }else {
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
}
