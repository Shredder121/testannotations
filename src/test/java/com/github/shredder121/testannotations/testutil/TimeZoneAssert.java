/*
 * Copyright 2016 Shredder121.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shredder121.testannotations.testutil;

import java.util.Objects;
import java.util.TimeZone;

import org.assertj.core.api.AbstractAssert;

public class TimeZoneAssert extends AbstractAssert<TimeZoneAssert, TimeZone> {

    TimeZoneAssert(TimeZone actual) {
        super(actual, TimeZoneAssert.class);
    }

    public TimeZoneAssert hasID(String ID) {
        isNotNull();

        if (!Objects.equals(actual.getID(), ID)) {
            failWithMessage("ID should be <%s> but was <%s>", ID, actual.getID());
        }
        return myself;
    }
}
