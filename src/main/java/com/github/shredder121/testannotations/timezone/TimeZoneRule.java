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

import java.io.Closeable;
import java.time.ZoneId;
import java.util.TimeZone;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A {link TestRule} that switches the JVM's default time zone for the duration of a test.
 *
 * @author Shredder121
 */
public class TimeZoneRule implements TestRule {

    @Override
    public Statement apply(Statement original, Description description) {
        return maybeChangeTimezone(original, description);
    }

    private static Statement maybeChangeTimezone(Statement original, Description description) {
        TimeZoneTest annotation = description.getAnnotation(TimeZoneTest.class);
        if (annotation != null) {
            return new TimeZoneStatement(original, parseToTimeZone(annotation), new TimeZoneResetter());
        } else {
            return original;
        }
    }

    private static TimeZone parseToTimeZone(TimeZoneTest timeZone) {
        return TimeZone.getTimeZone(ZoneId.of(timeZone.value()));
    }

    static class TimeZoneStatement extends Statement {

        private final Statement statement;
        private final TimeZone timezone;
        private final Closeable resetter;

        public TimeZoneStatement(Statement statement, TimeZone timezone, Closeable resetter) {
            this.statement = statement;
            this.timezone = timezone;
            this.resetter = resetter;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                TimeZone.setDefault(timezone);
                statement.evaluate();
            } finally {
                resetter.close();
            }
        }
    }

    static class TimeZoneResetter implements Closeable {

        private final TimeZone originalTimeZone = TimeZone.getDefault();

        @Override
        public void close() {
            TimeZone.setDefault(originalTimeZone);
        }
    }
}
