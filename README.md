# Android Base Components (work-in-progress 👷🔧️👷‍♀️⛏)


Base Components library are a set of base classes core and utilities used each time you do start a new android application working with the following stack:

#### Principles & Patterns

* **Clean Architecture**
* **S.O.L.I.D**
* **Model View Presenter**
* **Navigator**

#### Libraries

* **Butterknife**
* **Picasso or Glide**
* **Retrofit**
* **OkHttp**
* **Dagger 2**
* **RxJava & Rx Android**

**Note:** You can use just the components necessary that you need to solve your problems, It is not mandatory to use all.


Add it to your project
----------------------

```groovy
dependencies{
    compile 'com.github.erikcaffrey:base-components:1.0.1'
}
```

or to your ``pom.xml`` if you are using Maven

```xml
<dependency>
  <groupId>com.github.erikcaffrey</groupId>
  <artifactId>base-components</artifactId>
  <version>1.0.1</version>
  <type>aar</type>
</dependency>

```


### The project is divided in different modules 

* Data
* Domain
* Executor
* Mapper
* Navigation
* View


# Modules

## [View](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/tree/master/base-components/src/main/java/erikjhordanrey/base_components/view)

Provides classes to represent the components used on Presentation layer:

#### Android UI - Activity & Fragment

* [BaseActivity](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/blob/master/base-components/src/main/java/erikjhordanrey/base_components/view/BaseActivity.java) 
* [BaseFragment](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/blob/master/base-components/src/main/java/erikjhordanrey/base_components/view/BaseFragment.java) 
* [BaseFragmentActivity](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/blob/master/base-components/src/main/java/erikjhordanrey/base_components/view/BaseFragmentActivity.java) 

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

#### BaseFragmentActivity

```java
public class SampleFragmentActivity extends BaseFragmentActivity {

  @Override protected Fragment getFragmentInstance() {
    return SampleFragment.newInstance();
  }
  
  //Optional
  @Override protected int getLayoutResId() {
    return super.getLayoutResId();
  }
  
  //Optional 
  @Override protected Integer getContainerId() {
    return super.getContainerId();
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
  
}

```

When you needs hideLoading and showLoading methods to show some progress view on Ui you can use BasePresenterLoader.

#### BasePresenterLoader.UI

```java 

interface SampleUiLoader extends BasePresenterLoader.Ui {
}

```


#### BasePresenterLoader

```java
public class SamplePresenterLoader extends BasePresenterLoader<SampleUiLoader> {
  
}

```

To understand when the lifecycle methods should be call take a look at the following table:

| BasePresenter   | Activity       | Fragment           |
| --------------- |----------------| -------------------|
| ``start``  | ``onCreate``   | ``onViewCreated``  |
| ``resume``      | ``onResume``   | ``onResume``       |
| ``pause``       | ``onPause``    | ``onPause``        |
| ``stop``       | ``onStop``    | ``onStop``        |
| ``terminate``     | ``onDestroy``  | ``onDestroy``      |

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
