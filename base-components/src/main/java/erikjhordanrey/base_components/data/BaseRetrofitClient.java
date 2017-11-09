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

import android.support.annotation.NonNull;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings("unchecked") public abstract class BaseRetrofitClient<S> {

  private BaseRetrofitConfig baseRetrofitConfig;

  public BaseRetrofitClient(@NonNull BaseRetrofitConfig baseRetrofitConfig) {
    this.baseRetrofitConfig = baseRetrofitConfig;
  }

  public <S> S getService() {
    return (S) baseRetrofitConfig.getRetrofit().create(getServiceClass());
  }

  public <S> S synchronousCall(Call<S> call) {
    Response<S> response = null;
    try {
      response = call.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (response != null && response.isSuccessful()) {
      return response.body();
    } else {

    }
    return null;
  }

  private Class<S> getServiceClass() {
    return (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }
}
