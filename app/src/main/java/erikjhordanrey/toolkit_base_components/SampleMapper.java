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

import erikjhordanrey.base_components.mapper.Mapper;

public class SampleMapper extends Mapper<SampleObject1, SampleObject2> {

  @Override public SampleObject2 transform(SampleObject1 value) {
    return map(value);
  }

  @Override public SampleObject1 inverseTransform(SampleObject2 value) {
    throw new UnsupportedOperationException();
  }

  private SampleObject2 map(final SampleObject1 sampleObject1) {
    SampleObject2 sampleObject2 = new SampleObject2();
    sampleObject2.setSampleName("Mapper " + sampleObject1.getSampleName());
    sampleObject2.setSampleLastName("Mapper " + sampleObject1.getSampleLastName());
    sampleObject2.setSampleAge("Mapper " + sampleObject1.getSampleAge());
    return sampleObject2;
  }
}
