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
package com.github.shredder121.testannotations;

import java.lang.annotation.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class AbstractRule<A extends Annotation> implements TestRule {

    private static final Collection<Class<? extends Annotation>> SKIP;

    static {
        Collection<Class<? extends Annotation>> toSkip = new HashSet<>();
        Collections.addAll(toSkip,
                //java.lang.annotation
                Documented.class, Inherited.class, Retention.class, Target.class,
                //java.lang
                SuppressWarnings.class,
                //org.junit
                After.class, AfterClass.class, Before.class, BeforeClass.class, Rule.class, Test.class
        );

        SKIP = Collections.unmodifiableCollection(toSkip);
    }
    private final Class<A> annotationClass;

    protected AbstractRule(Class<A> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public final Statement apply(Statement original, Description description) {
        A annotation = findAnnotation(description, annotationClass);
        return maybeChange(original, annotation);
    }

    public abstract Statement maybeChange(Statement original, A annotation);

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
}
