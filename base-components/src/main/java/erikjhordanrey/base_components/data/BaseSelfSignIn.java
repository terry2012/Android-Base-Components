/*
 * Copyright (C) 2017 Erik Jhordan Rey.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package erikjhordanrey.base_components.data;

import android.content.Context;
import android.support.annotation.RawRes;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

public class BaseSelfSignIn {

  private final static String PROTOCOL_TLS = "TLS";
  private final static String KEYSTORE_TYPE_BKS = "BKS";
  private final static String ALIAS_CA = "ca";
  private final static String CERT_TYPE_X509 = "X.509";

  private final static String TAG = BaseSelfSignIn.class.getSimpleName();

  private OkHttpClient.Builder okHttpClient;

  public BaseSelfSignIn(Builder builder) {
    this.okHttpClient = builder.okHttpBuilder;
  }

  public OkHttpClient.Builder getOkHttpSSLClientBuilder() {
    return okHttpClient;
  }

  public static class Builder {

    private Context context;
    private Integer certificate;
    private Integer serverSSLCert;
    private Integer clientSSLCert;
    private String serverPassword;
    private String clientPassword;
    private String hostName;
    private OkHttpClient.Builder okHttpBuilder;

    public Builder with(Context context) {
      if (context == null) {
        throw new IllegalArgumentException(TAG + " Context must not be null.");
      }
      this.context = context;
      return this;
    }

    public Builder certificate(Integer certificate) {
      if (certificate == null) {
        throw new IllegalArgumentException(TAG + " Certificate must not be null.");
      }
      this.certificate = certificate;
      return this;
    }

    public Builder serverCertificate(Integer serverSSLCert) {
      if (serverSSLCert == null) {
        throw new IllegalArgumentException(TAG + " ServerCertificate must not be null.");
      }
      this.serverSSLCert = serverSSLCert;
      return this;
    }

    public Builder clientCertificate(Integer clientSSLCert) {
      if (clientSSLCert == null) {
        throw new IllegalArgumentException(TAG + " ClientCertificate must not be null.");
      }
      this.clientSSLCert = clientSSLCert;
      return this;
    }

    public Builder serverPassword(String serverPassword) {
      if (serverPassword == null) {
        throw new IllegalArgumentException(TAG + " ServerPassword must not be null.");
      }
      this.serverPassword = serverPassword;
      return this;
    }

    public Builder clientPassword(String clientPassword) {
      if (clientPassword == null) {
        throw new IllegalArgumentException(TAG + " ClientPassword must not be null.");
      }
      this.clientPassword = clientPassword;
      return this;
    }

    private void hostName(String baseUrl) {
      try {
        this.hostName = getDomainName(baseUrl);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }

    private String getDomainName(String url) throws URISyntaxException {
      final String WWW = "www.";
      final int AFTER_WWW = 4;
      URI uri = new URI(url);
      String domain = uri.getHost();
      return domain.startsWith(WWW) ? domain.substring(AFTER_WWW) : domain;
    }

    public BaseSelfSignIn buildSSLClient() {
      if (context != null && certificate != null && hostName != null) {
        okHttpBuilder = builderOkHttpSSLClientRoot();
      } else {
        okHttpBuilder = builderOkHttpSSLClient();
      }
      return new BaseSelfSignIn(this);
    }

    private OkHttpClient.Builder builderOkHttpSSLClient() {
      OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
      KeyStore trustStore = createKeyStore(serverSSLCert, serverPassword);
      TrustManagerFactory trustManagerFactory = createTrustManagerFactory(trustStore);
      KeyStore keyStore = createKeyStore(clientSSLCert, clientPassword);
      KeyManagerFactory keyManagerFactory = createKeyManagerFactory(keyStore, clientPassword);
      SSLContext sslContext = getSSLContext(keyManagerFactory, trustManagerFactory);
      SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      X509TrustManager trustManager = getX509TrustManager(trustManagerFactory);
      okHttpClient.sslSocketFactory(sslSocketFactory, trustManager);
      okHttpClient.hostnameVerifier(new HostnameVerifier() {
        @Override public boolean verify(String host, SSLSession session) {
          return host.equals(hostName);
        }
      });

      return okHttpClient;
    }

    private OkHttpClient.Builder builderOkHttpSSLClientRoot() {
      OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
      KeyStore keyStore = createKeyStore(certificate, null);
      TrustManagerFactory trustManagerFactory = createTrustManagerFactory(keyStore);
      SSLContext sslContext = getSSLContext(null, trustManagerFactory);
      X509TrustManager trustManager = getX509TrustManager(trustManagerFactory);
      okHttpClient.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
      okHttpClient.hostnameVerifier(new HostnameVerifier() {
        @Override public boolean verify(String host, SSLSession session) {
          return host.equals(hostName);
        }
      });

      return okHttpClient;
    }

    private KeyStore createKeyStore(@RawRes int certificateId, String password) {
      KeyStore keyStore = null;
      InputStream certStream = null;
      try {
        final String keystoreDefault = KeyStore.getDefaultType();
        keyStore = (password != null) ? KeyStore.getInstance(KEYSTORE_TYPE_BKS)
            : KeyStore.getInstance(keystoreDefault);

        if (password != null) {
          certStream = getSSLCertificateRaw(certificateId);
        } else {
          try {
            Certificate sslCertificate = getCertificate(certificateId);
            keyStore.setCertificateEntry(ALIAS_CA, sslCertificate);
          } catch (KeyStoreException e) {
            e.printStackTrace();
          }
        }

        try {
          if (password != null) {
            keyStore.load(certStream, password.toCharArray());
          } else {
            keyStore.load(null, null);
          }
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
          e.printStackTrace();
        } finally {
          try {
            if (certStream != null) {
              certStream.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } catch (KeyStoreException e) {
        e.printStackTrace();
      }

      return keyStore;
    }

    private TrustManagerFactory createTrustManagerFactory(KeyStore keyStore) {
      String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
      TrustManagerFactory managerFactory = null;
      try {
        managerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
        managerFactory.init(keyStore);
      } catch (NoSuchAlgorithmException | KeyStoreException e) {
        e.printStackTrace();
      }
      return managerFactory;
    }

    private KeyManagerFactory createKeyManagerFactory(KeyStore keyStore, String password) {
      String tmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
      KeyManagerFactory keyManagerFactory = null;
      try {
        keyManagerFactory = KeyManagerFactory.getInstance(tmfAlgorithm);
        keyManagerFactory.init(keyStore, password.toCharArray());
      } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e) {
        e.printStackTrace();
      }
      return keyManagerFactory;
    }

    private SSLContext getSSLContext(KeyManagerFactory keyManagerFactory,
        TrustManagerFactory trustManagerFactory) {
      SSLContext sslContext = null;
      try {
        sslContext = SSLContext.getInstance(PROTOCOL_TLS);

        if (keyManagerFactory != null) {
          sslContext.init(keyManagerFactory.getKeyManagers(),
              trustManagerFactory.getTrustManagers(), new java.security.SecureRandom());
        } else {
          sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        }
      } catch (KeyManagementException | NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
      return sslContext;
    }

    private X509TrustManager getX509TrustManager(TrustManagerFactory trustManagerFactory) {

      X509TrustManager trustManager;
      TrustManager[] trustManagers;

      trustManagers = trustManagerFactory.getTrustManagers();
      if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
        throw new IllegalStateException(
            "Unexpected default trust managers:" + Arrays.toString(trustManagers));
      }
      trustManager = (X509TrustManager) trustManagers[0];

      return trustManager;
    }

    private Certificate getCertificate(@RawRes int cert) {
      Certificate certificate = null;
      CertificateFactory certificateFactory = createAuthority();
      InputStream inputStream = getSSLCertificateRaw(cert);
      try {
        certificate = certificateFactory.generateCertificate(inputStream);
      } catch (CertificateException e) {
        e.printStackTrace();
      } finally {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return certificate;
    }

    private CertificateFactory createAuthority() {
      CertificateFactory certificateFactory = null;
      try {
        certificateFactory = CertificateFactory.getInstance(CERT_TYPE_X509);
      } catch (CertificateException e) {
        e.printStackTrace();
      }
      return certificateFactory;
    }

    private InputStream getSSLCertificateRaw(@RawRes int certificate) {
      return context.getResources().openRawResource(certificate);
    }
  }
}
