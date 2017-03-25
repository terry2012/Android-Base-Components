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

  <S> S getService() {
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
