# testannotations
Built-in setup/teardown rules for the masses

#Getting started

```java

public class TestClass {

    @Rule
    @ClassRule
    public static final TestRule testRule = TestAnnotations.RULES;

}

```
