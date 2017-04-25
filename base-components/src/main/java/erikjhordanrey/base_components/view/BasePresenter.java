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

package erikjhordanrey.base_components.view;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends Presenter.Ui> implements Presenter<V> {

  private WeakReference<V> view;

  @Override public void setUi(V view) {
    this.view = new WeakReference<>(view);
  }

  @Override public V getUi() {
    return view == null ? null : view.get();
  }

  @Override public boolean isViewAttached() {
    return view != null && view.get() != null;
  }

  @Override public void start() {

  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void stop() {
    this.view.clear();
  }

  @Override public void terminate() {
    view = null;
  }
}
