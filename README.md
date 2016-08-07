Assert-Rx
=========

A fluent assertion wrapper to simplify and improve usability of [RxJava's TestSubscriber](http://reactivex.io/RxJava/javadoc/rx/observers/TestSubscriber.html) assertions, built upon the [AssertJ](https://joel-costigliola.github.io/assertj/) framework.

[![Build Status](https://travis-ci.org/peter-tackage/assert-rx.svg?branch=master)](https://travis-ci.org/peter-tackage/assert-rx)
# Purpose

Although `TestSubscriber` provides some very handy assertions to test your Observables, it  can be a bit tedious and verbose to use. For example, to test the last value in a sequence you would typically write:

  ```java
     TestSubscriber<String> ts = ...... // subscribe to your Observable with TestSubscriber

     ts.assertNoErrors();
     List<String> values = ts.getOnNextEvents();
     assertThat(values.get(values.size() - 1)).isEqualTo("expectedValue");
  ```

This library makes this more readable by allowing you to express assertions in a fluent style:

 ```java
    TestSubscriber<String> ts = ...... // subscribe to your Observable with TestSubscriber

    assertThat(ts).hasNoErrors()
                  .hasReceivedLastValue("expectedValue");
 ```

In addition to this wrapping, it provides some higher order assertions to allow for testing of specific conditions.

 ```java
     assertThat(ts).hasReceivedFirstValueWhich()
                   .is(notEmptyOrNull());
 ```
Where `notEmptyOrNull` is a reusable, [AssertJ Condition](https://joel-costigliola.github.io/assertj/assertj-core-conditions.html).

# Usage

Here are a few examples of the assertions performed using Assert-Rx.

## OnNext Assertions

Assert that the subscriber received any single onNext value:

```java
    assertThat(ts).hasReceivedAnyValue();
```

Assert that the subscriber has received one or more onNext values:

```java
    assertThat(ts).hasReceivedAnyValues();
```

Multiple, in-order values:

```java
    assertThat(ts).hasReceivedValues("a", "b", "c");
```

Assert conditions for single onNext events (currently only as `Object` instances):

```java
    assertThat(ts).hasReceivedValueWhich()
                  .is(notEmptyOrNull());
```

Assert conditions for the entire onNext value sequence:

```java
    assertThat(ts).hasReceivedValuesWhich()
                  .doesNotContain(someObject);
```

Assert the first or last received onNext values:

```java
    assertThat(ts).hasReceivedFirstValue("the first value");
```

```java
    assertThat(ts).hasReceivedLastValue("the last value");
```

```java
    assertThat(ts).hasReceivedFirstValueWhich()
                  .is(notEmptyOrNull());
```

```java
    assertThat(ts).hasReceivedLastValueWhich()
                  .is(notEmptyOrNull());
```

## OnError Assertions

Received an `IOException` instance in `onError`:

```java
    assertThat(ts).hasError(IOException.class);
```


Assert conditions for onError events (currently only as `Throwable` instances):

```java
    assertThat(ts).hasErrorWhich()
                  .hasMessageStartingWith("A terrible error");
```

## Concurrency Handling

Handle concurrency, by ensuring that the `TestSubscriber` awaits a terminal event before asserting:

```java
    assertThat(ts).afterTerminalEvent()
                  .hasNoErrors()
                  .hasReceivedValue("someValue")
                  .hasCompleted();
```

Download
--------

Releases are available as dependencies via [Jitpack](https://jitpack.io/#peter-tackage/assert-rx/0.9.7).

Implementation
--------------

The library is built as an extension to the [AssertJ](https://joel-costigliola.github.io/assertj/) framework, but to avoid duplication of the assertion logic in
`TestSubscriber`, in most cases it simply calls through to the `TestSubscriber` assertion methods.
The exceptions to this are the higher level assertions, which are provided by returning instances of AssertJ assertions.

Acknowledgements
----------------

Brought to you by the power of the [Chilicorn](http://spiceprogram.org/chilicorn-history/) and the [Futurice Open Source Program](http://spiceprogram.org/).

![Chilicorn Logo](https://raw.githubusercontent.com/futurice/spiceprogram/gh-pages/assets/img/logo/chilicorn_no_text-256.png)

License
=======

    Copyright 2016 Peter Tackage

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

