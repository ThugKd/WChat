package com.thugkd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thugkd.entity.ChatMsgEntity;
import com.thugkd.fragment.MineFragment;
import com.thugkd.wchat.R;

import java.util.List;

/**
 * Created by thugkd on 06/04/2017.
 */

public class ChatMsgViewAdapter extends BaseAdapter {

    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
    }

    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();

    private List<ChatMsgEntity> coll;

    private Context ctx;

    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        ChatMsgEntity entity = coll.get(position);

        if (!entity.getSender().equals(MineFragment.me.getPhone())) {
            return IMsgViewType.IMVT_COM_MSG;
        } else {
            return IMsgViewType.IMVT_TO_MSG;
        }

    }

    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMsgEntity entity = coll.get(position);
        boolean isComMsg = false;
        if (!entity.getSender().equals(MineFragment.me.getPhone())) {
            isComMsg = true;
        }

        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (isComMsg) {
                convertView = mInflater.inflate(R.layout.chatting_item_msg_left, null);
            } else {
                convertView = mInflater.inflate(R.layout.chatting_item_msg_right, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.tvNick = (TextView) convertView.findViewById(R.id.group_member_nick);
            viewHolder.isComMsg = isComMsg;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvSendTime.setText(entity.getSendTime());
        viewHolder.tvContent.setText(entity.getContent());

        if (isComMsg) {
            viewHolder.tvNick.setText(entity.getSender());
        }
        return convertView;
    }


    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvContent;
        public TextView tvNick;
        public boolean isComMsg = true;
    }
}
