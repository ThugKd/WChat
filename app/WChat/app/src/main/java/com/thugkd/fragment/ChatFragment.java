package com.thugkd.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.thugkd.adapter.RecentChatAdapter;
import com.thugkd.entity.RecentChatEntity;
import com.thugkd.wchat.AddFriend;
import com.thugkd.wchat.ChatRoomActivity;
import com.thugkd.wchat.R;
import com.thugkd.wchat.ScanActivity;

import java.util.LinkedList;

public class ChatFragment extends Fragment {

    public static final String TAG = "ChatFragment";

    private Context mContext;
    private View chatView;
    private Toolbar toolbar;
    private TextView tvTitle;

    public static LinkedList<RecentChatEntity> RecentChatList = new LinkedList<>();
    public static RecentChatAdapter adapter;
    private SwipeMenuListView menuListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        chatView = inflater.inflate(R.layout.fragment_chat, null);
        Log.e(TAG, "create");
        findView();

        initView();

        return chatView;
    }

    public void findView() {
        toolbar = (Toolbar) chatView.findViewById(R.id.toolbar);
        tvTitle = (TextView) chatView.findViewById(R.id.titlebar_title);
    }

    private void initView() {

        toolbar.inflateMenu(R.menu.toolbar_navigation);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        tvTitle.setText("会话");

        menuListView = (SwipeMenuListView) chatView.findViewById(R.id.chat_listView);
        adapter = new RecentChatAdapter(mContext, RecentChatList);
        menuListView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // 创建滑动选项
                SwipeMenuItem showItem = new SwipeMenuItem(
                        mContext);
                // 设置选项背景
                showItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // 设置选项宽度
                showItem.setWidth(dp2px(90));
                // 设置选项标题
                showItem.setTitle("打开");
                // 设置选项标题
                showItem.setTitleSize(18);
                // 设置选项标题颜色
                showItem.setTitleColor(Color.WHITE);
                // 添加选项
                menu.addMenuItem(showItem);

                // 创建删除选项
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        mContext);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };

        menuListView.setMenuCreator(creator);

        menuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0: //第一个选项

                        startActivity(new Intent(getContext(), ChatRoomActivity.class));

                        return true;
                    case 1: //第二个选项
                        RecentChatList.remove(position);
                        adapter.notifyDataSetChanged();
                        return true;
                }
              return false;
            }
        });

        menuListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                RecentChatList.remove(0);
                adapter.notifyDataSetChanged();
                return true;
            }
        });


        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), ChatRoomActivity.class));
            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.add_friend:
                    startActivity(new Intent(getContext(), AddFriend.class));
                    break;
                case R.id.add_group:
                    startActivity(new Intent(getContext(), AddFriend.class));
                    break;
                case R.id.scan:
                    startActivity(new Intent(getContext(), ScanActivity.class));
                    break;
            }
            return true;
        }
    };

    /*
        // 第一个参数为我们待转的数据的单位，此处为 dp（dip）
        // 第二个参数为我们待转的数据的值的大小
        // 第三个参数为此次转换使用的显示量度（Metrics），它提供屏幕显示密度（density）和缩放信息
     */
    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
