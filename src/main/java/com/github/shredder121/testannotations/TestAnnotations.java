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

import org.junit.rules.RuleChain;

import com.github.shredder121.testannotations.locale.LocaleRule;
import com.github.shredder121.testannotations.timezone.TimeZoneRule;

/**
 * Test annotations.
 *
 * Built-in setup/teardown rules for the masses.
 *
 * @author Shredder121
 */
public class TestAnnotations {

    public static final RuleChain RULES
            = RuleChain.emptyRuleChain()
            .around(new LocaleRule())
            .around(new TimeZoneRule())
            ;

    private TestAnnotations() {
    }
}
