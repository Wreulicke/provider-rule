# provider-rule

This module provides powerful initialization for test class.

* Supported JUnit4
* Supported JUnit5
* integration with Spring4

# Installation

add repository and dependency.
also see [jitpack](https://jitpack.io/#Wreulicke/provider-rule/0.0.2).

* Example for gradle

```groovy
  repositories {
    maven { url 'https://jitpack.io' }
  }

  dependencies {
    compile 'com.github.Wreulicke:provider-rule:0.0.2'
  }
```

# Usage

* [junit integration](https://github.com/Wreulicke/provider-rule/blob/master/src/test/java/com/github/wreulicke/test/context/provider/junit4/ProvideRuleTest.java)
* [junit5 integration](https://github.com/wreulicke/provider-rule/blob/master/src/test/java/com/github/wreulicke/test/context/provider/junit5/ProviderExtensionTest.java)
* [spring integration](https://github.com/Wreulicke/provider-rule/blob/master/src/test/java/com/github/wreulicke/test/context/provider/spring/FieldNameProviderTest.java)

# Requirements

This module required some Spring modules.
