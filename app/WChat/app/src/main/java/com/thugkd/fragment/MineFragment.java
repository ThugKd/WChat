package com.thugkd.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thugkd.entity.User;
import com.thugkd.model.SendMessage;
import com.thugkd.view.CircleImageView;
import com.thugkd.wchat.FriendInfoActivity;
import com.thugkd.wchat.LoginActivity;
import com.thugkd.wchat.MainActivity;
import com.thugkd.wchat.R;

public class MineFragment extends Fragment implements View.OnClickListener {

    public static User me = null;
    public static boolean isFinishApp = false;
    private Context mContext;
    private View mineView;
    private Toolbar toolbar;
    private TextView tvTitle;
    private CircleImageView ivAvator;
    private TextView tvNick;

    private RelativeLayout myInfoLayout;
    private RelativeLayout mySignLayout;
    private RelativeLayout myWalletLayout;
    private RelativeLayout myCollectionLayout;
    private RelativeLayout myAlbumLayout;
    private RelativeLayout myDynamicLayout;
    private Button btnExit;

    static {
        me = jieXi(MainActivity.myInfo);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mineView = inflater.inflate(R.layout.fragment_mine, null);

        findView();
        isFinishApp = false;
        return mineView;
    }

    private void findView() {

        toolbar = (Toolbar) mineView.findViewById(R.id.toolbar);
        tvTitle = (TextView) mineView.findViewById(R.id.titlebar_title);
        tvTitle.setText("我的");
        ivAvator = (CircleImageView) mineView.findViewById(R.id.mine_avator);
        tvNick = (TextView) mineView.findViewById(R.id.mine_nick);
        Log.e("MineFragment", me.getNick());
        tvNick.setText(me.getNick());

        myInfoLayout = (RelativeLayout) mineView.findViewById(R.id.my_avator_relative);
        mySignLayout = (RelativeLayout) mineView.findViewById(R.id.my_sign_relative);
        myWalletLayout = (RelativeLayout) mineView.findViewById(R.id.my_wallet_relative);
        myCollectionLayout = (RelativeLayout) mineView.findViewById(R.id.my_collection_relative);
        myAlbumLayout = (RelativeLayout) mineView.findViewById(R.id.my_album_relative);
        myDynamicLayout = (RelativeLayout) mineView.findViewById(R.id.about_me_relative);
        btnExit = (Button) mineView.findViewById(R.id.activity_mine_exit_button);

        myInfoLayout.setOnClickListener(this);
        mySignLayout.setOnClickListener(this);
        myWalletLayout.setOnClickListener(this);
        myAlbumLayout.setOnClickListener(this);
        myCollectionLayout.setOnClickListener(this);
        myDynamicLayout.setOnClickListener(this);
        btnExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.my_avator_relative:
                intent = new Intent(getContext(), FriendInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", me);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.my_sign_relative:
                Toast.makeText(getContext(), "我的签名", Toast.LENGTH_SHORT).show();

                break;
            case R.id.my_wallet_relative:
                Toast.makeText(getContext(), "我的钱包", Toast.LENGTH_SHORT).show();

                break;
            case R.id.my_collection_relative:
                Toast.makeText(getContext(), "我的收藏", Toast.LENGTH_SHORT).show();

                break;
            case R.id.my_album_relative:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
                break;
            case R.id.about_me_relative:
                Toast.makeText(getContext(), "我的动态", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_mine_exit_button:
                AlertDialog ad = new AlertDialog.Builder(getActivity()).setTitle("确认退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveSP(false);

                                SendMessage.logout();

                                //sign out
                                startActivity(new Intent(getContext(), LoginActivity.class));

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //nothing to do
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }

    //save login state
    public void saveSP(boolean isLogin) {
        SharedPreferences sp = getContext().getSharedPreferences("loginState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
    }

    private static User jieXi(String str) {
        User user = new User();
        String s[] = str.split("_");
        if (s != null) {
            user.setPhone(s[0]);
            user.setNick(s[1]);
            user.setAvatar(s[2]);
            user.setSex(Integer.parseInt(s[3]));
            user.setAge(Integer.parseInt(s[4]));
        }
        return user;
    }
}
