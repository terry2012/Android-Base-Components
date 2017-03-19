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

package erikjhordanrey.base_components.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<Q, V> {

  public abstract V transform(Q value);

  public abstract Q inverseTransform(V value);

  public List<V> transform(List<Q> values) {
    List<V> returnValues = new ArrayList<>(values.size());
    for (Q value : values) {
      returnValues.add(transform(value));
    }
    return returnValues;
  }

  public List<Q> inverseTransform(List<V> values) {
    List<Q> returnValues = new ArrayList<>(values.size());
    for (V value : values) {
      returnValues.add(inverseTransform(value));
    }
    return returnValues;
  }
}