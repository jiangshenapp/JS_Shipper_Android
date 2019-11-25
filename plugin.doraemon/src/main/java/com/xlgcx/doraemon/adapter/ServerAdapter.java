package com.xlgcx.doraemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xlgcx.doraemon.R;
import com.xlgcx.doraemon.bean.Server;

import java.util.List;


/**
 * @Author: jason_hzb
 * @Time: 2018/7/16 下午5:34
 * @Company：小灵狗出行
 * @Description:服务环境adapter
 */
public class ServerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private ViewHolder holder;
    private List<Server> Server;
    private Server server;
    private String defaultHost;

    public ServerAdapter(Context context, String host) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.defaultHost = host;
    }

    public void setList(List<Server> server) {
        this.Server = server;
    }

    // 选中当前选项时，让其他选项不被选中
    public void select(String host) {
        defaultHost = host;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Server.size();
    }

    @Override
    public Object getItem(int position) {
        return Server.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_api_switch, null);
            holder.radioBtn = (RadioButton) convertView
                    .findViewById(R.id.radioButton);
            holder.radioBtn.setClickable(false);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        server = (Server) getItem(position);
        holder.radioBtn.setChecked(server.host.equals(defaultHost) );
        holder.textView.setText(server.name);
        return convertView;
    }

    class ViewHolder {
        RadioButton radioBtn;
        TextView textView;
    }
}
