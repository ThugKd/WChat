package com.thugkd.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.thugkd.adapter.RecentChatAdapter;
import com.thugkd.entity.RecentChatEntity;
import com.thugkd.entity.User;
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
    private ListView listView;

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

        listView = (ListView) chatView.findViewById(R.id.chat_listView);
        adapter = new RecentChatAdapter(mContext, RecentChatList);
        listView.setAdapter(adapter);

//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                // 创建滑动选项
//                SwipeMenuItem showItem = new SwipeMenuItem(
//                        mContext);
//                // 设置选项背景
//                showItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // 设置选项宽度
//                showItem.setWidth(dp2px(90));
//                // 设置选项标题
//                showItem.setTitle("打开");
//                // 设置选项标题
//                showItem.setTitleSize(18);
//                // 设置选项标题颜色
//                showItem.setTitleColor(Color.WHITE);
//                // 添加选项
//                menu.addMenuItem(showItem);
//
//                // 创建删除选项
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        mContext);
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                deleteItem.setWidth(dp2px(90));
//                deleteItem.setIcon(R.drawable.ic_delete);
//                menu.addMenuItem(deleteItem);
//            }
//        };

//        menuListView.setMenuCreator(creator);
//
//        menuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0: //第一个选项
//
//                        startActivity(new Intent(getContext(), ChatRoomActivity.class));
//
//                        return true;
//                    case 1: //第二个选项
//                        RecentChatList.remove(position);
//                        adapter.notifyDataSetChanged();
//                        return true;
//                }
//              return false;
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecentChatEntity entity = RecentChatList.get(position);
                // 下面是切换到聊天界面处理
                User user = new User();
                user.setNick(entity.getName());
                user.setPhone(entity.getId());
                user.setAvatar(entity.getImg());
                Intent intent = new Intent(getContext(), ChatRoomActivity.class);
                intent.putExtra("user", user);
                //这一句一定不能去掉否则会报错，Calling startActivity() from outside of an Activity  context
                //requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
                //即当不是在一个activity中启动一个activity时必须加上上面那个语句
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

                entity.setCount(0);
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog ad = new AlertDialog.Builder(getActivity()).setTitle("确认删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RecentChatList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //nothing to do
                            }
                        })
                        .show();
                return true;
            }
        });


//        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("ddd","sasasasa");
//                startActivity(new Intent(getContext(), ChatRoomActivity.class));
//            }
//        });
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
