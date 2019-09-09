package com.base.frame.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.base.frame.R;
import com.base.frame.R2;

import butterknife.BindView;

/**
 * Created by huyg on 2018/9/17.
 */
public class SimpleWebActivity extends SimpleActivity {

    @BindView(R2.id.webview)
    protected WebView mWebView;
    private String url;
    private String title;

    public static void action(Context context, String url, String title) {
        Intent intent = new Intent(context, SimpleWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_base_webview;
    }

    @Override
    protected void init() {
        initSetting();
        initIntent();
        mTitle.setText(title);
        mWebView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持javaScript
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");//设置网页默认编码
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.clearCache(true);
    }

    @Override
    public void setActionBar() {

    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            closeProgress();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
