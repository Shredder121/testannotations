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

import java.util.Locale;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;

/**
 * Assert class for {@link Locale}.
 *
 * @author Shredder121
 */
public class LocaleAssert extends AbstractAssert<LocaleAssert, Locale> {

    LocaleAssert(Locale actual) {
        super(actual, LocaleAssert.class);
    }

    public LocaleAssert hasLanguage(String language) {
        isNotNull();

        if (!Objects.equals(actual.getLanguage(), language)) {
            failWithMessage("Language should be <%s> but was <%s>", language, actual.getLanguage());
        }
        return myself;
    }
    public LocaleAssert doesNotHaveLanguage(String language) {
        isNotNull();

        if (Objects.equals(actual.getLanguage(), language)) {
            failWithMessage("Language should NOT be <%s>", language);
        }
        return myself;
    }

    public LocaleAssert hasCountry(String country) {
        isNotNull();

        if (!Objects.equals(actual.getCountry(), country)) {
            failWithMessage("Country should be <%s> but was <%s>", country, actual.getCountry());
        }
        return myself;
    }

    public LocaleAssert doesNotHaveCountry(String country) {
        isNotNull();

        if (Objects.equals(actual.getCountry(), country)) {
            failWithMessage("Country should NOT be <%s>", country);
        }
        return myself;
    }
}
