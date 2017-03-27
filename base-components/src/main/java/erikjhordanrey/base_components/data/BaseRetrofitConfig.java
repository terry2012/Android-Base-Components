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

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofitConfig {

  private final Retrofit retrofit;

  public BaseRetrofitConfig(Builder builder) {
    this.retrofit = builder.retrofit;
  }

  public Retrofit getRetrofit() {
    return retrofit;
  }

  public static class Builder {

    private Retrofit retrofit;
    private String baseUrl;
    private boolean debug;
    private Integer timeout = 150000;
    private Converter.Factory converter;
    private BaseSelfSignIn sslClient;

    private Builder() {

    }

    public static Builder create() {
      return new Builder();
    }

    public Builder retrofit(Retrofit retrofit) {
      if (retrofit == null) {
        throw new IllegalArgumentException("retrofit must not be null.");
      }
      this.retrofit = retrofit;
      return this;
    }

    public Builder baseUrl(String baseUrl) {
      if (baseUrl == null) {
        throw new IllegalArgumentException("baseUrl must not be null.");
      }
      this.baseUrl = baseUrl;
      return this;
    }

    public Builder timeOut(Integer timeout) {
      if (timeout == null) {
        throw new IllegalArgumentException("timeout must not be null.");
      }
      this.timeout = timeout;
      return this;
    }

    public Builder converter(Converter.Factory converter) {
      if (timeout == null) {
        throw new IllegalArgumentException("Converter must not be null.");
      }
      this.converter = converter;
      return this;
    }

    public Builder sslClient(BaseSelfSignIn sslClient) {
      this.sslClient = sslClient;
      return this;
    }

    public Builder debug() {
      this.debug = true;
      return this;
    }

    public BaseRetrofitConfig build() {
      if (retrofit == null) {
        retrofit = buildRetrofit();
      }

      return new BaseRetrofitConfig(this);
    }

    private Retrofit buildRetrofit() {
      OkHttpClient.Builder okHttpBuilder;
      Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

      if (sslClient != null) {
        okHttpBuilder = sslClient.getOkHttpSSLClientBuilder();
      } else {
        okHttpBuilder = new OkHttpClient.Builder();
      }

      if (timeout != null) {
        okHttpBuilder.readTimeout(timeout, TimeUnit.MILLISECONDS)
            .connectTimeout(timeout, TimeUnit.MILLISECONDS);
      }

      if (converter != null) {
        retrofitBuilder.addConverterFactory(converter);
      }

      if (debug) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.interceptors().add(interceptor);
      }

      retrofitBuilder.baseUrl(baseUrl)
          .client(okHttpBuilder.build())
          .addConverterFactory(GsonConverterFactory.create());

      return retrofitBuilder.build();
    }
  }
}
