package com.santiago.securechat.comm;

import android.net.wifi.WifiManager;
import android.util.Log;

import com.santiago.securechat.SecureChatApplication;
import com.santiago.securechat.comm.listener.IMessageReceveivedListener;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.net.ssl.SSLContext;

import static android.content.Context.WIFI_SERVICE;

public class SecureChatServer {

    private final Executor executor;
    private final SecureChatApplication secureChatApplication;
    private IMessageReceveivedListener iMessageReceveivedListener;

    @Inject
    public SecureChatServer (Executor executor, SecureChatApplication secureChatApplication) {
        this.executor = executor;
        this.secureChatApplication = secureChatApplication;
    }

    public void setMessageReceveivedListener (IMessageReceveivedListener iMessageReceveivedListener) {
        this.iMessageReceveivedListener = iMessageReceveivedListener;
    }

    public void startServer () {
        executor.execute(() -> {

            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                Socket listeningSocket = sslContext.getServerSocketFactory().createServerSocket(getLocalIp(), 1234);

                for (;;) {

                }
            } catch (Exception e) {

            }


        });
    }

    private final String getLocalIp () {

        WifiManager wifiManager = (WifiManager) secureChatApplication.getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e(getClass().getSimpleName(), "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }
}
