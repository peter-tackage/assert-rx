Assert-Rx
=========

A Java fluent assertion wrapper to improve readability of [RxJava's TestSubscriber](http://reactivex.io/RxJava/javadoc/rx/observers/TestSubscriber.html) assertions.

[![Build Status](https://travis-ci.org/peter-tackage/assert-rx.svg?branch=master)](https://travis-ci.org/peter-tackage/assert-rx)

Usage
-----

Typically, to test the behavior of a completing, filtering `Observable`, you would write:

 ```java
    TestSubscriber<String> ts = ...... // subscribe to a filtered Observable

    ts.assertNoErrors();
    ts.assertValue("someValue");
    ts.assertCompleted();
 ```

This library makes this a little more readable by allowing you to express assertions in a fluent style:

 ```java
     assertThat(ts).hasNoErrors()
                   .hasReceivedValue("someValue")
                   .hasCompleted();
 ```

Other common assertion scenarios, include:

Multiple, in-order values:

```java
    assertThat(ts).hasNoErrors()
                  .hasReceivedValues("a", "b", "c")
                  .hasCompleted();
```

Received an `IOException` instance in `onError`:

```java
    assertThat(ts).hasError(IOException.class);
```

Assert conditions for the onNext value sequence:

```java
    assertThat(ts).hasReceivedValuesWhich()
                  .doesNotContain(someObject);
```

Assert conditions for single onNext events (currently only as `Object` instances):

```java
 assertThat(ts).hasReceivedValueWhich()
               .is(notEmptyOrNull());
```

Similarly assert conditions for onError events (currently only as `Throwable` instances):

```java
 assertThat(ts).hasErrorWhich()
               .hasMessageStartingWith("A terrible error");
```

Handle concurrency, by ensuring that the `TestSubscriber` awaits a terminal event before asserting:

```java
 assertThat(ts).afterTerminalEvent()
               .hasNoErrors()
               .hasReceivedValue("someValue")
               .hasCompleted();
```

Download
--------

Releases are available as dependencies via [Jitpack](https://jitpack.io/#peter-tackage/assert-rx/0.9.1-beta).

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

