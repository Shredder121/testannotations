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

import java.io.Closeable;
import java.util.Locale;
import java.util.Locale.Category;

import org.junit.runners.model.Statement;

import com.github.shredder121.testannotations.AbstractRule;

/**
 * A {link TestRule} that switches the JVM's default locale for the duration of a test.
 *
 * @author Shredder121
 */
public class LocaleRule extends AbstractRule<LocaleTest> {

    public LocaleRule() {
        super(LocaleTest.class);
    }

    @Override
    public Statement maybeChange(Statement original, LocaleTest annotation) {
        if (annotation != null) {
            Category category = annotation.category().getLocaleCategory();
            return new LocaleStatement(original, parseToLocale(annotation), category, new LocaleResetter(category));
        } else {
            return original;
        }
    }

    private static Locale parseToLocale(LocaleTest locale) {
        return Locale.forLanguageTag(locale.value());
    }

    static class LocaleStatement extends Statement {

        private final Statement statement;
        private final Locale locale;
        private final Category category;
        private final Closeable resetter;

        public LocaleStatement(Statement statement, Locale locale, Category category, Closeable resetter) {
            this.statement = statement;
            this.locale = locale;
            this.category = category;
            this.resetter = resetter;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                if (category == null) {
                    Locale.setDefault(locale);
                } else {
                    Locale.setDefault(category, locale);
                }
                statement.evaluate();
            } finally {
                resetter.close();
            }
        }
    }

    static class LocaleResetter implements Closeable {

        private final Locale.Category category;
        private final Locale originalLocale;

        public LocaleResetter(Locale.Category category) {
            this.category = category;
            this.originalLocale = category != null ? Locale.getDefault(category) : Locale.getDefault();
        }

        @Override
        public void close() {
            if (category == null) {
                Locale.setDefault(originalLocale);
            } else {
                Locale.setDefault(category, originalLocale);
            }
        }
    }
}
