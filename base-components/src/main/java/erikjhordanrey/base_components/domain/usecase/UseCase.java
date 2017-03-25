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

package erikjhordanrey.base_components.domain.usecase;

import erikjhordanrey.base_components.executor.Executor;
import erikjhordanrey.base_components.executor.MainThread;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, P> {

  private final Executor executor;
  private final MainThread mainThread;
  private final CompositeDisposable compositeDisposable;

  public UseCase(Executor executor, MainThread mainThread) {
    this.executor = executor;
    this.mainThread = mainThread;
    this.compositeDisposable = new CompositeDisposable();
  }

  public void execute(P requestParams, DisposableObserver<T> disposableObserver) {
    final Observable<T> observable = this.buildUseCaseObservable(requestParams)
        .subscribeOn(Schedulers.from(executor))
        .observeOn(mainThread.getScheduler());

    addDisposable(observable, disposableObserver);
  }

  public void dispose() {
    if (!compositeDisposable.isDisposed()) {
      compositeDisposable.dispose();
    }
  }

  private void addDisposable(Observable<T> observable, DisposableObserver<T> disposableObserver) {
    DisposableObserver observer = observable.subscribeWith(disposableObserver);
    compositeDisposable.add(observer);
  }

  abstract Observable<T> buildUseCaseObservable(P requestParams);
}
