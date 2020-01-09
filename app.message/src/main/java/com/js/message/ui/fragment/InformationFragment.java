package com.js.message.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.base.frame.view.BaseFragment;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.js.message.MessageApp;
import com.js.message.R;
import com.js.message.R2;
import com.js.message.di.componet.DaggerFragmentComponent;
import com.js.message.di.module.FragmentModule;
import com.js.message.model.event.MessageEvent;
import com.js.message.ui.activity.MessageActivity;
import com.js.message.ui.activity.PushActivity;
import com.js.message.ui.chat.EaseChatActivity;
import com.js.message.ui.presenter.InformationPresenter;
import com.js.message.ui.presenter.contract.InformationContract;
import com.plugin.im.IMHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by huyg on 2019/4/1.
 * 消息fragment
 */
public class InformationFragment extends BaseFragment<InformationPresenter> implements InformationContract.View {

    @BindView(R2.id.list)
    EaseConversationList mList;
    @BindView(R2.id.tv_message_count)
    TextView mTvMessageCount;
    @BindView(R2.id.tv_push_count)
    TextView mTvPushCount;

    protected boolean isConflict;
    private static final String TAG = "InformationFragment";

    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
    private final static int MSG_REFRESH = 2;

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(MessageApp.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void init() {
        if ("shipper".equals(MessageApp.getInstance().appType)) {
            mPresenter.getUnreadMessageCount(2);
            mPresenter.getUnreadPushLogCount(2);
        } else {
            mPresenter.getUnreadMessageCount(1);
            mPresenter.getUnreadPushLogCount(1);
        }
        conversationList.addAll(loadConversationList());
        mList.init(conversationList);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = mList.getItem(position);
                //点击跳转
                if (conversation != null) {
                    if ("shipper".equals(MessageApp.getInstance().appType)) {
                        if (MessageApp.getInstance().personConsignorVerified == 2
                                || MessageApp.getInstance().companyConsignorVerified == 2) {
                            EaseChatActivity.action(mContext, conversation);
                        } else {
                            toast("未认证");
                        }
                    } else {
                        if (MessageApp.getInstance().driverVerified == 2
                                || MessageApp.getInstance().parkVerified == 2) {
                            EaseChatActivity.action(mContext, conversation);
                        } else {
                            toast("未认证");
                        }
                    }
                }
            }
        });
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    @OnClick({R2.id.message_layout, R2.id.push_layout, R2.id.customer_service_layout})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.message_layout) {
            MessageActivity.action(getActivity());
        } else if (view.getId() == R.id.push_layout) {
            PushActivity.action(getActivity());
        } else if (view.getId() == R.id.customer_service_layout) {
            IMHelper.getInstance().goIm(mContext);
        }
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        switch (event.index) {
            case MessageEvent.MESSAGE_COUNT_CHANGE: //消息数量改变
                if ("shipper".equals(MessageApp.getInstance().appType)) {
                    mPresenter.getUnreadMessageCount(2);
                } else {
                    mPresenter.getUnreadMessageCount(1);
                }
                break;
            case MessageEvent.PUSH_COUNT_CHANGE: //推送数量改变
                if ("shipper".equals(MessageApp.getInstance().appType)) {
                    mPresenter.getUnreadPushLogCount(2);
                } else {
                    mPresenter.getUnreadPushLogCount(1);
                }
                break;
        }
    }

    @Override
    public void onGetUnreadMessageCount(String count) {
        new QBadgeView(getActivity()).bindTarget(mTvMessageCount).setBadgeNumber(Integer.valueOf(count));
    }

    @Override
    public void onGetUnreadPushLogCount(String count) {
        new QBadgeView(getActivity()).bindTarget(mTvPushCount).setBadgeNumber(Integer.valueOf(count));
    }

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };

    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;
                case MSG_REFRESH: {
                    conversationList.clear();
                    conversationList.addAll(loadConversationList());
                    mList.refresh();
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * connected to server
     */
    protected void onConnectionConnected() {

    }

    /**
     * disconnected with server
     */
    protected void onConnectionDisconnected() {

    }

    /**
     * refresh ui
     */
    public void refresh() {
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    /**
     * load conversation list
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(connectionListener);
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            Log.d(TAG, "收到消息");
            refresh();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            Log.d(TAG, "收到透传消息");
            refresh();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            Log.d(TAG, "收到已读回执");
            refresh();
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            Log.d(TAG, "收到已送达回执");
            refresh();
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
            Log.d(TAG, "消息被撤回");
            refresh();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            Log.d(TAG, "消息状态变动");
            refresh();
        }
    };
}