package com.thugkd.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thugkd.entity.User;
import com.thugkd.wchat.AddFriend;
import com.thugkd.wchat.ChatRoomActivity;
import com.thugkd.wchat.R;
import com.thugkd.wchat.ScanActivity;

public class FriendFragment extends Fragment {

    public static String buddyStr = "";
    public static String groupStr = "";

    private Context mContext;
    private View friendView;
    private Toolbar toolbar;
    private TextView tvTitle;

    private RelativeLayout groupRelative;
    private int REQUEST_CHAT = 0;

    private MyAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        friendView = inflater.inflate(R.layout.fragment_friend, null);

        findView();
        initView();
        return friendView;
    }


    private void initView() {
        toolbar.inflateMenu(R.menu.toolbar_navigation);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        tvTitle.setText("好友");
    }

    private void findView() {
        toolbar = (Toolbar) friendView.findViewById(R.id.toolbar);
        tvTitle = (TextView) friendView.findViewById(R.id.titlebar_title);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        adapter = new MyAdapter();
        ExpandableListView ev = (ExpandableListView) getActivity().findViewById(R.id.expandlist);
        ev.setGroupIndicator(null);
        ev.setAdapter(adapter);
        ev.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                       public boolean onChildClick(ExpandableListView parent, View v,
                                                                   int groupPosition, int childPosition, long id) {

                                           String friend_name = (String) adapter.getChild(groupPosition, childPosition);
                                           Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                                           Bundle bundle = new Bundle();
                                           User user = new User();

                                           user.setPhone((String) adapter.getAccount(groupPosition, childPosition));
                                           user.setNick(friend_name);
                                           bundle.putSerializable("user", user);
                                           intent.putExtras(bundle);
                                           startActivity(intent);
                                           return true;
                                       }
                                   }
        );
    }

    //toolbar 导航
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


    /*将适配器定义为内部类*/
    class MyAdapter implements ExpandableListAdapter {

        String[] group = new String[]{"群组", "好友"};

        String[][] child = new String[2][];

        String[][] signString = new String[2][];

        String[][] account = new String[2][];

        public MyAdapter() {
            super();
            initData();
        }

        public void initData() {
            Log.e("Buddy", buddyStr);
            Log.e("group", groupStr);
            String[] buddyArr = FriendFragment.buddyStr.trim().split(" ");
            String[] groupArr = FriendFragment.groupStr.trim().split(" ");

            User[] users = jieXi(buddyArr);

            User group = jieXiGroup(groupArr);

            child[0] = new String[1];
            signString[0] = new String[1];
            account[0] = new String[1];
            child[0][0] = group.getNick();
            signString[0][0] = "我是" + group.getNick() + "群组";
            account[0][0] = group.getPhone();

            child[1] = new String[users.length];
            signString[1] = new String[users.length];
            account[1] = new String[users.length];
            for (int i = 0; i < users.length; i++) {
                child[1][i] = users[i].getNick();
                signString[1][i] = "我是" + users[i].getNick();
                account[1][i] = users[i].getPhone();
            }
        }


        public Object getAccount(int groupPosition, int childPosition) {
            return account[groupPosition][childPosition];
        }

        public boolean areAllItemsEnabled() {
            // TODO Auto-generated method stub
            return false;
        }

        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return child[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            GroupHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.fragment_constact_child, null);
                holder = new GroupHolder();
                holder.nameView = (TextView) convertView
                        .findViewById(R.id.contact_list_item_name);
                holder.feelView = (TextView) convertView
                        .findViewById(R.id.cpntact_list_item_state);
                holder.iconView = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.iconView.setImageResource(R.drawable.head);
            holder.nameView.setText(getChild(groupPosition, childPosition)
                    .toString());
            holder.feelView.setText(signString[groupPosition][childPosition]);
            return convertView;
        }

        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
            return child[groupPosition].length;
        }

        public long getCombinedChildId(long groupId, long childId) {
            // TODO Auto-generated method stub
            return 0;
        }

        public long getCombinedGroupId(long groupId) {
            // TODO Auto-generated method stub
            return 0;
        }

        public Object getGroup(int groupPosition) {
            // TODO Auto-generated method stub
            return group[groupPosition];
        }

        public int getGroupCount() {
            // TODO Auto-generated method stub
            return group.length;
        }

        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub


            ChildHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.fragment_constact_group, null);
                holder = new ChildHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.group_name);
                holder.onLineView = (TextView) convertView
                        .findViewById(R.id.online_count);
                holder.iconView = (ImageView) convertView
                        .findViewById(R.id.group_indicator);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            holder.nameView.setText(group[groupPosition]);
            holder.onLineView.setText(getChildrenCount(groupPosition) + "/"
                    + getChildrenCount(groupPosition));
            if (isExpanded) {
                holder.iconView.setImageResource(R.drawable.qb_down);
            } else {
                holder.iconView.setImageResource(R.drawable.qb_right);
            }
            return convertView;

        }

        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return true;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

        public boolean isEmpty() {
            // TODO Auto-generated method stub
            return false;
        }

        public void onGroupCollapsed(int groupPosition) {
            // TODO Auto-generated method stub

        }

        public void onGroupExpanded(int groupPosition) {
            // TODO Auto-generated method stub

        }

        public void registerDataSetObserver(DataSetObserver observer) {
            // TODO Auto-generated method stub

        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            // TODO Auto-generated method stub

        }

        private User[] jieXi(String[] str) {
            User[] user = new User[str.length];
            for (int i = 0; i < str.length; i++) {
                String s[] = str[i].split("_");
                user[i] = new User();
                user[i].setPhone(s[0]);
                user[i].setNick(s[1]);
                user[i].setAvatar(s[2]);
                user[i].setIsOnLine(Integer.parseInt(s[3]));
            }

            return user;
        }

        private User jieXiGroup(String[] str) {
            User group = new User();
            String[] groups = str[0].split("_");
            group.setPhone(groups[0]);
            group.setNick(groups[1]);
            return group;
        }
    }

    class ChildHolder {
        TextView nameView;
        TextView onLineView;
        ImageView iconView;
    }

    class GroupHolder {
        TextView nameView;
        TextView feelView;
        ImageView iconView;
    }
}


