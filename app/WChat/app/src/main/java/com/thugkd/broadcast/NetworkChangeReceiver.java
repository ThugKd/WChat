package com.thugkd.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.thugkd.util.HttpUtil;

/**
 * Created by thugkd on 06/04/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    public NetworkChangeReceiver() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO Auto-generated method stub
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            new AlertDialog.Builder(context)
                    .setTitle("温馨提示")
                    .setMessage("当前网络不可用，请检查网络设置")
                    .setPositiveButton("前往打开",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent = new Intent(
                                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                    context.startActivity(intent);
                                }
                            }).setNegativeButton("取消", null).create().show();
            //	Toast.makeText(context, "当前网络不可用，请检查网络设置",Toast.LENGTH_SHORT).show();
        } else {
            if (HttpUtil.getNetTypeName(context).equals("WIFI")) {
                Toast.makeText(context, HttpUtil.getIp(context), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, HttpUtil.getMobileIP(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
