package erikjhordanrey.toolkit_base_components;

import erikjhordanrey.base_components.domain.usecase.UseCase;
import io.reactivex.Observable;

public class SampleUseCase extends UseCase<String, Void> {

  @Override public Observable<String> createObservable(Void parameters) {
    return null;
  }

}

