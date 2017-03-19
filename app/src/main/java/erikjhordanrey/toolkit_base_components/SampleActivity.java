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

package erikjhordanrey.toolkit_base_components;

import erikjhordanrey.base_components.view.BaseActivity;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class SampleActivity extends BaseActivity {

  @Override protected int getLayoutId() {
    return R.layout.sample_activity;
  }

  @Override protected void initializeActivity() {
    super.initializeActivity();

    SampleObject1 sampleObject1 = new SampleObject1();
    sampleObject1.setSampleName("Sample 1");
    sampleObject1.setSampleLastName("Sample Last Name");
    sampleObject1.setSampleAge("Sample Age");

    System.out.println(sampleObject1.getSampleName());
    System.out.println(sampleObject1.getSampleLastName());
    System.out.println(sampleObject1.getSampleAge());

    Observable.just(sampleObject1)
        .map(new SampleDataMapper(new SampleMapper()))
        .subscribeWith(new DisposableObserver<SampleObject2>() {
          @Override public void onNext(SampleObject2 value) {
            System.out.println(value.getSampleName());
            System.out.println(value.getSampleLastName());
            System.out.println(value.getSampleAge());
          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onComplete() {

          }
        });
  }
}
