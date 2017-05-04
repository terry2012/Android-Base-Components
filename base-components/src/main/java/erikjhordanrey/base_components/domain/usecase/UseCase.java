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

  private Executor executor;
  private MainThread mainThread;
  private CompositeDisposable disposable;

  public void execute(P parameters, DisposableObserver<T> disposableObserver) {
    final Observable<T> observable = this.createObservable(parameters)
        .subscribeOn(Schedulers.from(executor))
        .observeOn(mainThread.getScheduler());

      addDisposable(observable, disposableObserver);

  }

  public void addExecutor(Executor executor) {
    this.executor = executor;
  }

  public void addUiThread(MainThread mainThread) {
    this.mainThread = mainThread;
  }

  public void addCompositeDisposable(CompositeDisposable disposable) {
    this.disposable = disposable;
  }

  public void dispose() {
    if (!disposable.isDisposed()) {
      disposable.dispose();
    }
  }

  private void addDisposable(Observable<T> observable, DisposableObserver<T> disposableObserver) {
    DisposableObserver observer = observable.subscribeWith(disposableObserver);
    disposable.add(observer);
  }

  public abstract Observable<T> createObservable(P parameters);
}
