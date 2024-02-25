package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
@RequiredArgsConstructor
public class ApiManager {
    @Value("${demo.external.base-url}")
    private String baseUrl;
    @Value("${demo.pfx.path}")
    private String pfxPath;
    @Value("${demo.pfx.password}")
    private String pfxPassword;

    private Api externalApi;

    public Object test1(String id) {
        try {
            return externalApi.test1(id).execute().body();
        } catch (IOException e) {
            // TODO: Error Handle
            throw new RuntimeException(e);
        }
    }

    public Object test2(Object body) {
        try {
            return externalApi.test2(body).execute().body();
        } catch (IOException e) {
            // TODO: Error Handle
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void configure() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getCertificatedClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.externalApi = retrofit.create(Api.class);
    }

    private OkHttpClient getCertificatedClient() {
        X509TrustManager[] trustManagers = new X509TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                // TODO: Implement
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                // TODO: Implement
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        SSLContext sslContext = this.getSslContext(trustManagers);
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), trustManagers[0])
                .build();
    }


    private SSLContext getSslContext(TrustManager[] trustManagers) {
        SSLContext result = null;
        try(InputStream inputStream = new ClassPathResource(pfxPath).getInputStream()) {
            char[] password = pfxPassword.toCharArray();
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, password);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            result = SSLContext.getInstance("SSL");
            result.init(keyManagers, trustManagers, null);
        } catch (Exception e) {
            // TODO: Error Handle
        }
        return result;
    }
}
