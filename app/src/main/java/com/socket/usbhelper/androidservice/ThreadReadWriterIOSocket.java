package com.socket.usbhelper.androidservice;

import android.content.Context;
import android.util.Log;


import com.jeremyliao.livedatabus.LiveDataBus;
import com.socket.usbhelper.usb.LiveDataManager;
import com.socket.usbhelper.MyUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ThreadReadWriterIOSocket implements Runnable {
    private Socket client;
    private Context context;

    ThreadReadWriterIOSocket(Context context, Socket client) {
        this.client = client;
        this.context = context;
    }
    @Override
    public void run() {
        BufferedOutputStream out;
        BufferedInputStream in;
        try {
            /* PC端发来的数据msg */
            String currCMD = "";
            out = new BufferedOutputStream(client.getOutputStream());
            in = new BufferedInputStream(client.getInputStream());
            // testSocket();// 测试socket方法
            ConnectService.ioThreadFlag = true;
            while (ConnectService.ioThreadFlag) {
                try {
                    if (!client.isConnected()) {
                        break;
                    }
                    /* 接收PC发来的数据 */
                    Log.e("TAG", Thread.currentThread().getName() + "---->" + "will read......");
                    /* 读操作命令 */
                    currCMD = readCMDFromSocket(in);
                    Log.e("TAG", Thread.currentThread().getName() + "---->" + "**currCMD ==== " + currCMD);
                    LiveDataBus.get().with(LiveDataManager.USB_SOCKET_MESSAGE).postValue(currCMD);
                } catch (Exception e) {
                    Log.e("TAG", Thread.currentThread().getName() + "---->" + "read write error111111");
                }
            }
            out.close();
            in.close();
        } catch (Exception e) {
            Log.e("TAG", Thread.currentThread().getName() + "---->" + "read write error222222");
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    Log.e("TAG", Thread.currentThread().getName() + "---->" + "client.close()");
                    client.close();
                }
            } catch (IOException e) {
                Log.e("TAG", Thread.currentThread().getName() + "---->" + "read write error333333");
                e.printStackTrace();
            }
        }
    }


    /**
     * 功能：从socket流中读取完整文件数据
     *
     * InputStream in：socket输入流
     *
     * byte[] filelength: 流的前4个字节存储要转送的文件的字节数
     *
     * byte[] fileformat：流的前5-8字节存储要转送的文件的格式（如.apk）
     *
     * */
    public static byte[] receiveFileFromSocket(InputStream in,
                                               OutputStream out, byte[] filelength, byte[] fileformat) {
        byte[] filebytes = null;// 文件数据
        try {
            in.read(filelength);// 读文件长度
            int filelen = MyUtil.bytesToInt(filelength);// 文件长度从4字节byte[]转成Int
            String strtmp = "read file length ok:" + filelen;
            out.write(strtmp.getBytes("utf-8"));
            out.flush();

            filebytes = new byte[filelen];
            int pos = 0;
            int rcvLen = 0;
            while ((rcvLen = in.read(filebytes, pos, filelen - pos)) > 0) {
                pos += rcvLen;
            }
            Log.e("TAG", Thread.currentThread().getName()
                    + "---->" + "read file OK:file size=" + filebytes.length);
            out.write("read file ok".getBytes("utf-8"));
            out.flush();
        } catch (Exception e) {
            Log.e("TAG", Thread.currentThread().getName()
                    + "---->" + "receiveFileFromSocket error");
            e.printStackTrace();
        }
        return filebytes;
    }

    /* 读取命令 */
    public static String readCMDFromSocket(InputStream in) {
        int MAX_BUFFER_BYTES = 2048;
        String msg = "";
        byte[] tempbuffer = new byte[MAX_BUFFER_BYTES];
        try {
            int numReadedBytes = in.read(tempbuffer, 0, tempbuffer.length);
            msg = new String(tempbuffer, 0, numReadedBytes, "utf-8");
            tempbuffer = null;
        } catch (Exception e) {
            Log.e("TAG", Thread.currentThread().getName() + "---->" + "readFromSocket error");
            e.printStackTrace();
        }
        // Log.e(Service139.TAG, "msg=" + msg);
        return msg;
    }
}
