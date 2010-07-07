/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.utils.nlp.collocations.llr;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapreduce.Reducer;

/** Combiner for pass1 of the CollocationDriver. Combines frequencies for values for the same key */
public class CollocCombiner extends Reducer<GramKey, Gram, GramKey, Gram> {

  /* (non-Javadoc)
   * @see org.apache.hadoop.mapreduce.Reducer#reduce(java.lang.Object, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
   */
  @Override
  protected void reduce(GramKey key, Iterable<Gram> values, Context context) throws IOException, InterruptedException {

    int freq = 0;
    Gram value = null;

    // accumulate frequencies from values.
    Iterator<Gram> it = values.iterator();
    while (it.hasNext()) {
      value = it.next();
      freq += value.getFrequency();
    }

    value.setFrequency(freq);

    context.write(key, value);
  }

}
