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

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements Presenter.Ui {

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(hasMenu());
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(getLayoutId(), container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initializePresenter();
    initializeFragment(view);
  }

  /**
   * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned
   * Override this method to configure your fragment or initialize views.
   *
   * @param view The Ui returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
   */

  protected void initializeFragment(@NonNull View view) {

  }

  /**
   * Called before to initialize all the presenter instances linked to the component lifecycle.
   * Override this method to configure your presenter with extra data if needed.
   */
  protected void initializePresenter() {

  }

  /**
   * @return the layout id associated to the layout used in the fragment.
   */

  @LayoutRes protected abstract int getLayoutId();

  /**
   * @return hasMenu If true, the fragment has menu items to contribute.
   */

  protected boolean hasMenu() {
    return true;
  }

  /**
   * @return a Toolbar of activity
   */

  @Nullable protected Toolbar getBaseToolbar() {
    return ((BaseActivity) getActivity()).getBaseToolbar();
  }

  /**
   * @return set ActionBar in a Fragment
   */

  protected void setSupportActionBarOnFragment(@NonNull Toolbar toolbar) {
    ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
  }

  /**
   * @return the {@link BaseActivity} if you need the activity context.
   */

  protected BaseActivity getBaseActivity() {
    return ((BaseActivity) getActivity());
  }

  /**
   * @return the {@link ActionBar} for you can configure it.
   */

  protected ActionBar getSupportActionBar() {
    return ((BaseActivity) getActivity()).getSupportActionBar();
  }

  /**
   * Use this method if you need call onDestroy method of Activity
   */

  protected void finishActivity() {
    getActivity().finish();
  }

  /**
   * @return the FragmentManager for interacting with fragments associated
   * with this activity.
   */

  protected FragmentManager getActivitySupportFragmentManager() {
    return getActivity().getSupportFragmentManager();
  }

  /**
   * Use this method if you need replace an existing fragment that was added to a container
   *
   * @param containerViewId Identifier of the container whose fragment(s) are to be replaced
   * @param fragment The new fragment to place in the container
   */

  protected void onReplaceTransaction(@IdRes int containerViewId, @NonNull Fragment fragment) {
    getActivitySupportFragmentManager().beginTransaction()
        .replace(containerViewId, fragment)
        .commit();
  }

  /**
   * Use this method if you need replace an existing fragment that was added to a container
   * and you need add to BackStack
   *
   * @param containerViewId Identifier of the container whose fragment(s) are to be replaced
   * @param fragment The new fragment to place in the container
   */

  protected void onReplaceTransactionWithBackStack(@IdRes int containerViewId,
      @NonNull Fragment fragment) {
    getActivitySupportFragmentManager().beginTransaction()
        .replace(containerViewId, fragment)
        .addToBackStack(Fragment.class.getCanonicalName())
        .commit();
  }
}