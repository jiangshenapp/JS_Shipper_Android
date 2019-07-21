package com.js.shipper.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by huyg on 2018/8/6.
 */
public class GlideImageLoader extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        CommonGlideImageLoader.getInstance().displayNetImage(context, (String)path, imageView);
    }


}
