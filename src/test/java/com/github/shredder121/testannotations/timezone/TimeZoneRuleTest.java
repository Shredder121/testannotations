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
package com.github.shredder121.testannotations.timezone;

import static com.github.shredder121.testannotations.testutil.TestAnnotationsAssertions.assertThat;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.util.TimeZone;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

@TimeZoneTest("UTC")
public class TimeZoneRuleTest {

    @Rule
    @ClassRule
    public static final TestRule rule = new TimeZoneRule();

    @Test
    public void testGlobalTZ() {
        assertThat(TimeZone.getDefault()).hasID("UTC");
    }

    @Test
    @TimeZoneTest("CET")
    public void testLocalTZ() {
        assertThat(TimeZone.getDefault()).hasID("CET");
    }

    @Test
    @CetTest
    public void testMetaAnnotation() {
        assertThat(TimeZone.getDefault()).hasID("CET");
    }

    @Retention(RUNTIME)
    @TimeZoneTest("CET")
    public @interface CetTest {
    }
}
