package com.thugkd.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thugkd.wchat.R;

public class MineFragment extends Fragment implements View.OnClickListener{

    private Context mContext;
    private View mineView;

    private RelativeLayout myInfoLayout;
    private RelativeLayout mySignLayout;
    private RelativeLayout myWalletLayout;
    private RelativeLayout myCollectionLayout;
    private RelativeLayout myAlbumLayout;
    private RelativeLayout myDynamicLayout;
    private Button btnExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mineView = inflater.inflate(R.layout.fragment_mine, null);

        findView();

        return mineView;
    }

    private void findView() {
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
                Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();

                break;
            case R.id.my_sign_relative:
                Toast.makeText(getActivity(),"dsdsdsds",Toast.LENGTH_SHORT).show();

                break;
            case R.id.my_wallet_relative:
                Toast.makeText(getActivity(),"我的钱包",Toast.LENGTH_SHORT).show();

                break;
            case R.id.my_collection_relative:
                Toast.makeText(getActivity(),"我的收藏",Toast.LENGTH_SHORT).show();

                break;
            case R.id.my_album_relative:
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
                break;
            case R.id.about_me_relative:
                Toast.makeText(getActivity(),"我的动态",Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_mine_exit_button:
                AlertDialog ad = new AlertDialog.Builder(getActivity()).setTitle("确认退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveSP(false);

                                //sign out
                                getActivity().finish();

                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

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
}
