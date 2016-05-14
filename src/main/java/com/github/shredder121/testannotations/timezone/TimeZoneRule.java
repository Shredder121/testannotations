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
import java.lang.annotation.Annotation;
import java.time.ZoneId;
import java.util.*;
import java.lang.annotation.*;

import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A {link TestRule} that switches the JVM's default time zone for the duration of a test.
 *
 * @author Shredder121
 */
public class TimeZoneRule implements TestRule {

    private static final Collection<Class<? extends Annotation>> SKIP;

    static {
        Collection<Class<? extends Annotation>> toSkip = new HashSet<>();
        Collections.addAll(toSkip,
                //java.lang.annotation
                Documented.class, Inherited.class, Retention.class, Target.class,
                //java.lang
                java.lang.SuppressWarnings.class,
                //org.junit
                After.class, AfterClass.class, Before.class, BeforeClass.class, Rule.class, Test.class
        );

        SKIP = Collections.unmodifiableCollection(toSkip);
    }

    @Override
    public Statement apply(Statement original, Description description) {
        return maybeChangeTimezone(original, description);
    }

    private static Statement maybeChangeTimezone(Statement original, Description description) {
        TimeZoneTest annotation = findAnnotation(description, TimeZoneTest.class);
        if (annotation != null) {
            return new TimeZoneStatement(original, parseToTimeZone(annotation), new TimeZoneResetter());
        } else {
            return original;
        }
    }

    private static <A extends Annotation> A findAnnotation(Description description, Class<A> cl) {
        Collection<Annotation> annotations = description.getAnnotations();
        return recursiveFindAnnotation(annotations.toArray(new Annotation[annotations.size()]), cl);
    }

    private static <A extends Annotation> A recursiveFindAnnotation(Annotation[] annotations, Class<A> cl) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (type.equals(cl)) {
                return cl.cast(annotation);
            } else if (!SKIP.contains(type)) {
                A found = recursiveFindAnnotation(type.getAnnotations(), cl);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
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
