/*
  Santiago Roig
  Faten Haji
  Thien Nguyen

  SecureCh@t
 */

package com.santiago.securechat.comm;

import android.app.Application;
import android.util.Log;

import com.santiago.securechat.comm.listener.IMessageReceivedListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * Responsible for receiving SSL messages from clients
 */
@SuppressWarnings("unused")
public class SecureChatServer {

    private final Executor executor;
    private final Application application;

    private IMessageReceivedListener iMessageReceivedListener;

    private boolean isRunning = false;

    /**
     * Constructs SecureChatServer
     * @param executor - Used for thread management
     * @param application - Used for application context
     */
    @Inject
    public SecureChatServer (Executor executor, Application application) {
        this.executor = executor;
        this.application = application;
    }

    /**
     * Set's listener for incoming messages
     * @param iMessageReceivedListener -
     */
    public void setMessageReceivedListener(IMessageReceivedListener iMessageReceivedListener) {
        this.iMessageReceivedListener = iMessageReceivedListener;
    }

    /**
     * Listens for incoming client connections and spawns new thread to handle client's message
     */
    public void run () {

        executor.execute(() -> {
            isRunning = true;

            try {

                SSLContext sslContext = createSSLContext();
                SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
                SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(9999);

                Log.i(getClass().getSimpleName(), "Server Started");

                while (isRunning) {
                    SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

                    // Start the server thread
                    clientHandleThread(sslSocket);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * Thread for handling incoming message form peer
     * @param sslSocket - SSLSocket connected to client
     */
    private void clientHandleThread (SSLSocket sslSocket) {
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());

        try {
            // Start handshake
            sslSocket.startHandshake();

            // Get session after the connection is established
            SSLSession sslSession = sslSocket.getSession();

            Log.d(getClass().getSimpleName(),"SSLSession :");
            Log.d(getClass().getSimpleName(),"\tProtocol : " + sslSession.getProtocol());
            Log.d(getClass().getSimpleName(),"\tCipher suite : " + sslSession.getCipherSuite());
            Log.d(getClass().getSimpleName(), "\tIP Address : " + sslSession.getPeerHost());


            // Start handling application content
            InputStream inputStream = sslSocket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String message;
            while((message = bufferedReader.readLine()) != null){

                Log.d(getClass().getSimpleName(), "Input: " + message);

                iMessageReceivedListener.onIncomingMessage(sslSession.getPeerHost(), message);

                if(message.trim().isEmpty()){
                    break;
                }
            }

            sslSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Create's SSL context from key located in assets
     * @return SSLContext
     */
    private SSLContext createSSLContext () {

        SSLContext sslContext = null;

        try {

            KeyStore keyStore = KeyStore.getInstance("BKS");
            keyStore.load(application.getApplicationContext().getAssets().open("keystore.bks"),"password".toCharArray());

            String test = KeyManagerFactory.getDefaultAlgorithm();
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(test);
            keyManagerFactory.init(keyStore, "password".toCharArray());
            KeyManager[] km = keyManagerFactory.getKeyManagers();

            sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(km, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslContext;
    }

    /**
     * Check if server is running
     * @return Server Running Status
     */
    @SuppressWarnings("unused")
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Stop the server
     */
    public void stop () {
        isRunning = false;
    }
}
