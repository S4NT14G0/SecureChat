package com.santiago.securechat.comm;

import android.app.Application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.concurrent.Executor;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.net.Socket;

import javax.inject.Inject;
import javax.net.ssl.SSLSocket;

public class SecureChatClient {


    private final Executor executor;
    private final Application application;

    /**
     * Initializes connection to secure websocket server as client
     */
    @Inject
    public SecureChatClient (Executor executor, Application application) {
        this.executor = executor;
        this.application = application;
    }

    public void sendMessage (String jsonMessage, String ipAddress, int port) {
        executor.execute(() -> {
            try {
                KeyStore keyStore = KeyStore.getInstance("BKS");
                keyStore.load(application.getApplicationContext().getAssets().open("keystore.bks"),"password".toCharArray());
                SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore);
                socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                // Build our socket with the factory and the server info
                SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(new Socket(ipAddress,port), ipAddress, port, false);
                sslSocket.startHandshake();

                OutputStream out = sslSocket.getOutputStream();

                out.write(jsonMessage.getBytes());
                out.flush();

                sslSocket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}



