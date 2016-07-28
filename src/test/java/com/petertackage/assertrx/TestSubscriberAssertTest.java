/**
 * Copyright 2016 Peter Tackage
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.petertackage.assertrx;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSubscriberAssertTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // Higher Order tests - these don't use TestSubscriber assertions.

    @Test
    public void hasReceivedFirstValue_doesNotAssert_whenEqual_andSourceObservableEmitsOnce() {
        Object value = new Object();
        Observable<Object> oi = Observable.just(value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(value);
    }

    @Test
    public void hasReceivedFirstValue_doesNotAssert_whenEqual_andSourceObservableEmitsMultiple() {
        Object value = new Object();
        Observable<Object> oi = Observable.just(value, new Object());
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(value);
    }

    @Test
    public void hasReceivedFirstValue_asserts_whenNotEqual_andSourceObservableEmitsMultipleAndOtherValuesMatches() {
        Object value = new Object();
        Object otherValue = new Object();
        expectAssertionErrorWithMessage(String.format("Expected first received onNext events to be: <%s>, but was: <%s>.", otherValue, value));
        Observable<Object> oi = Observable.just(value, otherValue);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(otherValue);
    }

    @Test
    public void hasReceivedFirstValue_asserts_whenNotEqual_andSourceObservableEmitsOnce() {
        Object otherValue = new Object();
        Object value = new Object();
        expectAssertionErrorWithMessage(String.format("Expected first received onNext events to be: <%s>, but was: <%s>.", otherValue, value));
        Observable<Object> oi = Observable.just(value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(otherValue);
    }

    @Test
    public void hasReceivedFirstValue_asserts_whenSourceObservableIsEmpty() {
        expectAssertionErrorWithMessage("Expected received onNext events not to be empty.");
        Observable<Object> oi = Observable.empty();
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(new Object());
    }

    @Test
    public void hasReceivedFirstValue_asserts_whenNotEqual_andSourceObservableEmitsMultiple() {
        Object otherValue = new Object();
        Object value = new Object();
        expectAssertionErrorWithMessage(String.format("Expected first received onNext events to be: <%s>, but was: <%s>", otherValue, value));
        Observable<Object> oi = Observable.just(value, otherValue);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(otherValue);
    }


    @Test
    public void hasReceivedValues_asserts_whenSourceObservableIsEmpty() {
        expectAssertionErrorWithMessage("Expected received onNext events not to be empty.");
        Observable<Object> oi = Observable.empty();
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedValues();
    }

    @Test
    public void hasReceivedValues_asserts_whenSourceObservableIsError() {
        expectAssertionErrorWithMessage("Expected received onNext events not to be empty.");
        Observable<Object> oi = Observable.error(new Throwable());
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedValues();
    }

    @Test
    public void hasReceivedValues_asserts_whenSourceObservableIsNever() {
        expectAssertionErrorWithMessage("Expected received onNext events not to be empty.");
        Observable<Object> oi = Observable.never();
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedValues();
    }

    @Test
    public void hasReceivedValues_doesNotAssert_whenSourceObservableEmitsOnce() {
        Observable<Integer> oi = Observable.just(1);
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasReceivedValues();
    }

    @Test
    public void hasReceivedValues_doesNotAssert_whenSourceObservableEmitsMultiple() {
        Observable<Integer> oi = Observable.just(1, 2);
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasReceivedValues();
    }

    @Test
    public void hasReceivedValueWhich_asserts_whenOnNextEventsListIsEmpty() {
        List<Object> values = Collections.emptyList();
        expectAssertionErrorWithMessage(String.format("Expected a single onNext value, but was: <%s>", values));
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(values);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedValueWhich();
    }

    @Test
    public void hasReceivedValueWhich_asserts_whenMultipleOnNextEvents() {
        List<Object> values = Arrays.asList(new Object(), new Object());
        expectAssertionErrorWithMessage(String.format("Expected a single onNext value, but was: <%s>", values));
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(values);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedValueWhich();
    }

    @Test
    public void hasReceivedValueWhich_doesNotAssert_whenSingleOnNextEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Collections.singletonList(new Object()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedValueWhich();
    }

    @Test
    public void hasErrorWhich_asserts_whenErrorEventListIsEmpty() {
        List<Throwable> values = Collections.emptyList();
        expectAssertionErrorWithMessage(String.format("Expected a single onError event, but was: <%s>", values));
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnErrorEvents()).thenReturn(values);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasErrorWhich();
    }

    @Test
    public void hasErrorWhich_asserts_whenMultipleErrorEvents() {
        List<Throwable> values = Arrays.asList(new Throwable(), new Throwable());
        expectAssertionErrorWithMessage(String.format("Expected a single onError event, but was: <%s>", values));
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnErrorEvents()).thenReturn(values);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasErrorWhich();
    }

    @Test
    public void hasErrorWhich_doesNotAssert_whenSingleErrorEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnErrorEvents()).thenReturn(Collections.singletonList(new Throwable()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasErrorWhich();
    }

    private void expectAssertionErrorWithMessage(String message) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(message);
    }

}
