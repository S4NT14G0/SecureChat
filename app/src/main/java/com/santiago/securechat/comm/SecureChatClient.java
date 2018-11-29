package com.santiago.securechat.comm;

import com.santiago.securechat.SecureChatApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.inject.Inject;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class SecureChatClient {


    private final SecureChatApplication secureChatApplication;

    /**
     * Initializes connection to secure websocket server as client
     * @param secureChatApplication - Application for context
     */
    @Inject
    public SecureChatClient (SecureChatApplication secureChatApplication) {
        this.secureChatApplication = secureChatApplication;

        try {
            SecureWebSocketClient secureWebSocketClient = new SecureWebSocketClient(new URI("wss://echo.websocket.org"));

            // load up the key store
            String STORETYPE = "JKS";
            String KEYSTORE = "keystore.jks";
            String STOREPASSWORD = "storepassword";
            String KEYPASSWORD = "keypassword";

            KeyStore ks = KeyStore.getInstance( STORETYPE );
            File kf = new File( KEYSTORE );
            ks.load( new FileInputStream( kf ), STOREPASSWORD.toCharArray() );

            KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SunX509" );
            kmf.init( ks, KEYPASSWORD.toCharArray() );
            TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
            tmf.init( ks );

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance( "TLS" );
            sslContext.init( kmf.getKeyManagers(), tmf.getTrustManagers(), null );
            // sslContext.init( null, null, null ); // will use java's default key and trust store which is sufficient unless you deal with self-signed certificates

            SSLSocketFactory factory = sslContext.getSocketFactory();// (SSLSocketFactory) SSLSocketFactory.getDefault();

            secureWebSocketClient.setSocketFactory( factory );

            secureWebSocketClient.connectBlocking();

            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
            while ( true ) {
                String line = reader.readLine();
                if( line.equals( "close" ) ) {
                    secureWebSocketClient.close();
                } else {
                    secureWebSocketClient.send( line );
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
