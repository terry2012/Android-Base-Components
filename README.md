# Android-Toolkit-Base-Components
Utilities that are used to develop and maintain android applications following the clean principles



## [View Package](https://github.com/erikcaffrey/Android-Toolkit-Base-Components/tree/master/base-components/src/main/java/erikjhordanrey/base_components/view)

Base Components provides classes to represent the main components normally used on Presentation layer like BaseActivity, BaseFragment, BasePresenter.

Create a new **Activity**

```java 

public class SampleActivity extends BaseActivity {

  @Override protected int getLayoutId() {
    return R.layout.sample_activity;
  }
}
```

Create a new **Fragment**

```java
public class SampleFragment extends BaseFragment {

  public static SampleFragment newInstance() {
    return new SampleFragment();
  }

  @Override protected int getLayoutId() {
    return R.layout.sample_fragment;
  }
}
```

Do you want to contribute?
--------------------------
Feel free to report or add any useful feature, I will be glad to improve it with your help, before submitting your code please check the [codestyle](https://github.com/square/java-code-styles).

Developed By
------------

* Erik Jhordan Rey  - <erikcaffrey10@gmail.com> o <erik.gonzalez@schibsted.com.mx>

License
-------

    Copyright 2016 Erik Jhordan Rey

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
