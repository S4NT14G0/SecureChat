/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.comm;

import android.app.Application;

import com.santiago.securechat.comm.listener.IMessageSentListener;

import java.io.OutputStream;
import java.security.KeyStore;
import java.util.concurrent.Executor;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.net.Socket;

import javax.inject.Inject;
import javax.net.ssl.SSLSocket;

/**
 * Responsible for sending SSL messages to server
 */
public class SecureChatClient {

    private final Executor executor;
    private final Application application;

    /**
     * Initializes connection to secure server as client
     */
    @Inject
    public SecureChatClient (Executor executor, Application application) {
        this.executor = executor;
        this.application = application;
    }

    /**
     * Sends message over SSL and closes socket
     * @param message - Message
     * @param ipAddress - Outgoing IP Address
     * @param port - Outgoing Port
     * @param iMessageSentListener - Listens for message send success
     */
    public void sendMessage (String message, String ipAddress, int port, IMessageSentListener iMessageSentListener) {
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

                out.write(message.getBytes());
                out.flush();

                sslSocket.close();

            } catch (Exception e) {
                e.printStackTrace();

                if (iMessageSentListener != null)
                    iMessageSentListener.onMessageSent(ipAddress, port, message, false);

                return;
            }

            if (iMessageSentListener != null)
                iMessageSentListener.onMessageSent(ipAddress, port, message, true);

        });
    }

}



