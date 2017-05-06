package com.thugkd.model;

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
        return manager;
    }

    private HashMap<String, ClientConServerThread> hm = new HashMap<>();

    public void addClientConServerThread(String account, ClientConServerThread ccst) {
        hm.put(account, ccst);
    }

    public ClientConServerThread getClientConServerThread(String i) {

        return hm.get(i);
    }

    public void removeClientConServerThread(String account) {
        hm.remove(account);
    }
}
