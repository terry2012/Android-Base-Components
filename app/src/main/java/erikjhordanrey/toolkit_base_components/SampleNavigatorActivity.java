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

import android.content.Context;
import android.content.Intent;
import erikjhordanrey.base_components.navigation.NavigatorActivity;

public class SampleNavigatorActivity extends NavigatorActivity {

  public SampleNavigatorActivity(Context context) {
    super(context);
  }

  @Override public Intent onIntentNavigator(Context context) {
    return new Intent(context, SampleActivity.class);
  }
}
