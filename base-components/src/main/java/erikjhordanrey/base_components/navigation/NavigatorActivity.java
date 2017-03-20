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

package erikjhordanrey.base_components.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public abstract class NavigatorActivity implements NavigatorTarget {

  private Context context;

  public NavigatorActivity(Context context) {
    this.context = context;
  }

  @Override public void onNavigate() {
    Intent intent = onIntentNavigator(context);
    Bundle activityOptions = onIntentNavigatorOptions();
    if (activityOptions != null && isEqualsOrMajorToJellyBean()) {
      context.startActivity(intent, activityOptions);
    } else {
      context.startActivity(intent);
    }
  }

  public abstract Intent onIntentNavigator(Context context);

  protected Bundle onIntentNavigatorOptions() {
    return null;
  }

  private boolean isEqualsOrMajorToJellyBean() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }
}
