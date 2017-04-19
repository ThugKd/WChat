package com.thugkd.model;

import android.util.Log;

import java.util.HashMap;

public class ManageClientConServer {

    private volatile static ManageClientConServer manager = new ManageClientConServer();

    private ManageClientConServer() {

    }

    public static ManageClientConServer getManager() {
        if (manager == null) {
            synchronized (ManageClientConServer.class) {
                if (manager == null) {
                    manager = new ManageClientConServer();
                }
            }
        }
        Log.e("Manager", "get====");
        return manager;
    }

    private HashMap<String, ClientConServerThread> hm = new HashMap<>();

    public void addClientConServerThread(String account, ClientConServerThread ccst) {
        hm.put(account, ccst);
        Log.e("Manager", "add====");
    }

    public ClientConServerThread getClientConServerThread(String i) {
        Log.e("Manager", "getCli====");

        return hm.get(i);
    }

    public void removeClientConServerThread(String account) {
        hm.remove(account);
    }
}
