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

import java.lang.annotation.*;
import java.util.Locale;

/**
 * Annotation that marks a test that should be run under a different locale.
 *
 * @author Shredder121
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LocaleTest {

    /**
     * @return The {@link Locale#forLanguageTag(java.lang.String) language tag} of the Locale to switch to.
     */
    String value();

    /**
     * @return The (optional) Category
     */
    Category category() default Category.ROOT;

    public enum Category {

        ROOT(null),
        DISPLAY(Locale.Category.DISPLAY),
        FORMAT(Locale.Category.FORMAT),
        ;
        private final Locale.Category localeCategory;

        private Category(Locale.Category localeCategory) {
            this.localeCategory = localeCategory;
        }

        public Locale.Category getLocaleCategory() {
            return localeCategory;
        }
    }
}
