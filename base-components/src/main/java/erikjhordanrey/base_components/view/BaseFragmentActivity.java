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

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import erikjhordanrey.base_components.R;

public abstract class BaseFragmentActivity extends BaseActivity {

  private Fragment fragment;

  @Override protected void initializeActivity() {
    super.initializeActivity();
    initializeFragment();
  }

  /**
   * Initialize a fragment in an Activity
   */

  private void initializeFragment() {
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Integer containerId = R.id.container;
    if (getContainerId() != null) {
      containerId = getContainerId();
    }
    fragment = fragmentManager.findFragmentById(containerId);
    if (fragment == null) {
      fragment = getFragmentInstance();
      fragmentManager.beginTransaction().add(containerId, fragment).commit();
    }
  }

  /**
   * @return the layout id associated to the layout used in the fragment.
   */

  @Override @LayoutRes protected int getLayoutResId() {
    return R.layout.default_container;
  }

  /**
   * @return containerViewId Optional identifier of the container this fragment is
   * to be placed in.
   */

  @IdRes protected Integer getContainerId() {
    return R.id.container;
  }

  /**
   * @return The new fragment to place in the container
   */

  protected abstract Fragment getFragmentInstance();

  /**
   * @return nested target fragment
   */
  public Fragment getNestedTargetFragment() {
    return fragment;
  }
}