package com.lancommunicationclient;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CommunicationClientUtil {

    private Socket mSocket;
    private OutputStream mOutStream;
    private InputStream mInStream;
    private SocketConnectThread mReceiveThread;
    private String mIpAddress;
    private final int mClientPort = 8888;

    public void connect(String text) {
        this.mIpAddress = text;
        if (null == mReceiveThread) {
            this.mReceiveThread = new SocketConnectThread();
            this.mReceiveThread.start();
        }
    }

    public void sendMsg(final String mess) {
        if (mess.length() == 0) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    new DataOutputStream(mSocket.getOutputStream()).writeUTF(mess);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void closeConnection() {
        try {
            if (mOutStream != null) {
                mOutStream.close();
                mOutStream = null;
            }
            if (mInStream != null) {
                mInStream.close();
                mInStream = null;
            }
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mReceiveThread != null) {
            mReceiveThread = null;
        }
    }

    private class SocketConnectThread extends Thread {
        public void run() {
            try {
                mSocket = new Socket(mIpAddress, mClientPort);
                mOutStream = mSocket.getOutputStream();
                mInStream = mSocket.getInputStream();
            } catch (Exception e) {
                closeConnection();
                Log.e("tag", "connect fail");
                return;
            }
            Log.e("tag", "connect success");
        }
    }
}
