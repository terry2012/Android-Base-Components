# Android Base Components

Base Components library are a set of utilities used on current android development.

These base components are the core used normally when you start to develop an android application working with the following stack:

#### Principles

* **Model View Presenter**
* **Clean Architecture**
* **S.O.L.I.D**

#### Libraries

* **Butterknife**
* **Picasso or Glide**
* **Retrofit**
* **OkHttp**
* **Dagger 2**
* **RxJava & Rx Android**

### The project is divided in different modules 

* Data
* Domain
* Executor
* Mapper
* Navigation
* View

**Note:** You can use just the components necessary that you need to solve your problems, It is not mandatory to use all.

# Modules

## [View](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/tree/master/base-components/src/main/java/erikjhordanrey/base_components/view)

Provides classes to represent the components used on Presentation layer:

#### Android UI - Activity & Fragment

* [BaseActivity](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/blob/master/base-components/src/main/java/erikjhordanrey/base_components/view/BaseActivity.java) 
* [BaseFragment](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/blob/master/base-components/src/main/java/erikjhordanrey/base_components/view/BaseFragment.java) 

##### BaseActivity

```java 

public class SampleActivity extends BaseActivity {

  @Override protected int getLayoutResId() {
    return R.layout.sample_activity;
  }
}
```

##### BaseFragment

```java
public class SampleFragment extends BaseFragment {

  public static SampleFragment newInstance() {
    return new SampleFragment();
  }

  @Override protected int getLayoutResId() {
    return R.layout.sample_fragment;
  }
}
```

#### Model View Presenter

* [BasePresenter.Ui](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/blob/master/base-components/src/main/java/erikjhordanrey/base_components/view/Presenter.java)
* [BasePresenter](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/blob/master/base-components/src/main/java/erikjhordanrey/base_components/view/BasePresenter.java)

##### BasePresenter.Ui

```java 
interface SampleUI extends BasePresenter.Ui {
    // UI methods
}

```

##### BasePresenter

```java
public class SamplePresenter extends BasePresenter<SampleUi> {

  @Override public void initialize() {
    super.initialize();
  }

  @Override public void resume() {
    super.resume();
  }

  @Override public void pause() {
    super.pause();
  }

  @Override public void stop() {
    super.stop();
  }

  @Override public void terminate() {
    super.terminate();
  } 
}

```

## [Domain](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/tree/master/base-components/src/main/java/erikjhordanrey/base_components/domain)


Do you want to contribute?
--------------------------
Feel free to report or add any useful feature, I will be glad to improve it with your help, before submitting your code please check the [codestyle](https://github.com/square/java-code-styles).

Developed By
------------

* Erik Jhordan Rey  - <erikcaffrey10@gmail.com> o <erik.gonzalez@schibsted.com.mx>

License
-------

    Copyright 2017 Erik Jhordan Rey

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
