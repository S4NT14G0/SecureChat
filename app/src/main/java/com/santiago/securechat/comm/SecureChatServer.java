package com.santiago.securechat.comm;

import android.app.Application;
import android.util.Log;

import com.santiago.securechat.comm.listener.IMessageReceveivedListener;

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

public class SecureChatServer {

    private final Executor executor;
    private final Application application;

    private IMessageReceveivedListener iMessageReceveivedListener;

    private boolean isRunning = false;

    @Inject
    public SecureChatServer (Executor executor, Application application) {
        this.executor = executor;
        this.application = application;
    }

    public void setMessageReceveivedListener (IMessageReceveivedListener iMessageReceveivedListener) {
        this.iMessageReceveivedListener = iMessageReceveivedListener;
    }

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

            String message = null;
            while((message = bufferedReader.readLine()) != null){

                Log.d(getClass().getSimpleName(), "Input: " + message);

                iMessageReceveivedListener.onIncomingMessage(sslSession.getPeerHost(), message);

                if(message.trim().isEmpty()){
                    break;
                }
            }

            sslSocket.close();
        } catch (Exception e) {

        }

    }

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

    public boolean isRunning() {
        return isRunning;
    }

    public void stop () {
        isRunning = false;
    }
}
