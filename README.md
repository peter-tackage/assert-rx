Assert-Rx
=========

A Java fluent assertion wrapper to improve readability of [RxJava's TestSubscriber](http://reactivex.io/RxJava/javadoc/rx/observers/TestSubscriber.html) assertions.

[![Build Status](https://travis-ci.org/peter-tackage/assert-rx.svg?branch=master)](https://travis-ci.org/peter-tackage/assert-rx)

# Usage

Typically, to test the behavior of a completing, filtering `Observable`, you would write:

 ```
    TestSubscriber<String> ts = ...... // subscribe to a filtered Observable

    ts.assertNoErrors();
    ts.assertValue("someValue");
    ts.assertCompleted();
 ```

This library makes this a little more readable by allowing you to express assertions in a fluent style:

 ```
    assertThat(ts).hasNoErrors()
                  .hasReceivedValue("someValue")
                  .hasCompleted();
 ```

## Expanded OnNext Assertions

In addition to the assertions provided by `TestSubscriber`, this library also provides additional assertions, such as:

Assert that the subscriber received any single onNext value:

```
    assertThat(ts).hasReceivedAnyValue();

```

Assert that the subscriber has received one or more onNext values:

```
    assertThat(ts).hasReceivedAnyValues();
```

Multiple, in-order values:

```
    assertThat(ts).hasReceivedValues("a", "b", "c");
```

Assert conditions for single onNext events (currently only as `Object` instances):

```
    assertThat(ts).hasReceivedValueWhich()
                  .is(notEmptyOrNull());
```

Assert conditions for the entire onNext value sequence:

```
    assertThat(ts).hasReceivedValuesWhich()
                  .doesNotContain(someObject);
```

Assert the first or last received onNext values:

```
    assertThat(ts).hasReceivedFirstValue("the first value");
```

```
    assertThat(ts).hasReceivedLastValue("the last value");
```

```
    assertThat(ts).hasReceivedFirstValueWhich()
                  .is(notEmptyOrNull());
```

```
    assertThat(ts).hasReceivedLastValueWhich()
                  .is(notEmptyOrNull());
```

## Expanded OnError Assertions

Received an `IOException` instance in `onError`:

```
    assertThat(ts).hasError(IOException.class);
```


Assert conditions for onError events (currently only as `Throwable` instances):

```
    assertThat(ts).hasErrorWhich()
                  .hasMessageStartingWith("A terrible error");
```

## Other Assertions

Handle concurrency, by ensuring that the `TestSubscriber` awaits a terminal event before asserting:

```
    assertThat(ts).afterTerminalEvent()
                  .hasNoErrors()
                  .hasReceivedValue("someValue")
                  .hasCompleted();
```

Download
--------

Releases are available as dependencies via [Jitpack](https://jitpack.io/#peter-tackage/assert-rx/0.9.6-beta).

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

