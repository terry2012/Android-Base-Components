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

import android.support.v4.app.Fragment;
import erikjhordanrey.base_components.view.BaseFragmentActivity;

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
