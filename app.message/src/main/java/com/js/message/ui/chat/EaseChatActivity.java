package com.js.message.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;

import com.base.frame.view.SimpleActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.js.message.R;
import com.js.message.R2;

import butterknife.BindView;

/**
 * Created by huyg on 2019-07-08.
 */
public class EaseChatActivity extends SimpleActivity {

    @BindView(R2.id.frame)
    FrameLayout frame;

    private String userId;
    private int chatType;

    public static void action(Context context, EMConversation emConversation) {
        Intent intent = new Intent(context, EaseChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, emConversation.getType());
        intent.putExtra(EaseConstant.EXTRA_USER_ID, emConversation.conversationId());
        context.startActivity(intent);
    }

    public static void action(Context context, int type, String userId) {
        Intent intent = new Intent(context, EaseChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, type);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userId);
        context.startActivity(intent);
    }

    private EaseChatFragment mEaseChatFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_ease_chat;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        userId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
    }

    private void initView() {
        mEaseChatFragment = new EaseChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EaseConstant.EXTRA_USER_ID, userId);
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        mEaseChatFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame, mEaseChatFragment).commit();
    }

    @Override
    public void setActionBar() {
        mToolbar.setVisibility(View.GONE);
    }
}