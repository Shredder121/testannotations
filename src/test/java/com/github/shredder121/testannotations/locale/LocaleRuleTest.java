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
package com.github.shredder121.testannotations.locale;

import static com.github.shredder121.testannotations.locale.LocaleTest.Category.DISPLAY;
import static com.github.shredder121.testannotations.testutil.TestAnnotationsAssertions.assertThat;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.util.Locale;
import java.util.Locale.Category;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

@LocaleTest("nl")
public class LocaleRuleTest {

    @Rule
    @ClassRule
    public static final TestRule rule = new LocaleRule();

    @Test
    public void testGlobalLocale() {
        assertThat(Locale.getDefault()).hasLanguage("nl");
        assertThat(Locale.getDefault(Category.DISPLAY))
                .isSameAs(Locale.getDefault(Category.FORMAT));
    }

    @Test
    @LocaleTest("en")
    public void testLocalLocale() {
        assertThat(Locale.getDefault()).hasLanguage("en");
        assertThat(Locale.getDefault(Category.DISPLAY))
                .isSameAs(Locale.getDefault(Category.FORMAT));
    }

    @Test
    @LocaleTest(value = "en-US", category = DISPLAY)
    public void testLocalDisplayLocale() {
        assertThat(Locale.getDefault())
                .doesNotHaveCountry("en")
                .doesNotHaveLanguage("US");
        assertThat(Locale.getDefault(Category.DISPLAY))
                .hasLanguage("en").hasCountry("US");
        assertThat(Locale.getDefault(Category.DISPLAY))
                .isNotSameAs(Locale.getDefault(Category.FORMAT));
    }

    @Test
    @TurkeyTest
    public void testMetaAnnotation() {
        assertThat(Locale.getDefault())
                .hasLanguage("tr").hasCountry("TR");
    }

    @Retention(RUNTIME)
    @LocaleTest("tr-TR")
    public @interface TurkeyTest {
    }
}
