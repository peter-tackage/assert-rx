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

import org.assertj.core.api.AbstractObjectAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSubscriberAssertTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // Higher Order tests - these don't use TestSubscriber assertions.

    // hasReceivedFirstValue

    @Test
    public void hasReceivedFirstValue_doesNotAssert_whenSourceObservableEmitsOnceEqual() {
        Object value = new Object();
        Observable<Object> oi = Observable.just(value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(value);
    }

    @Test
    public void hasReceivedFirstValue_doesNotAssert_whenSourceObservableEmitsMultipleFirstEqual() {
        Object value = new Object();
        Observable<Object> oi = Observable.just(value, new Object());
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(value);
    }

    @Test
    public void hasReceivedFirstValue_asserts_whenSourceObservableEmitsMultipleFirstNotEqualAndOtherValueEquals() {
        Object value = new Object();
        Object otherValue = new Object();
        expectAssertionErrorWithMessage(String.format("Expected first received onNext event to be: <%s>, but was: <%s>.", otherValue, value));
        Observable<Object> oi = Observable.just(value, otherValue);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(otherValue);
    }

    @Test
    public void hasReceivedFirstValue_asserts_whenSourceObservableEmitsOnceNotEqual() {
        Object otherValue = new Object();
        Object value = new Object();
        expectAssertionErrorWithMessage(String.format("Expected first received onNext event to be: <%s>, but was: <%s>.", otherValue, value));
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
        expectAssertionErrorWithMessage(String.format("Expected first received onNext event to be: <%s>, but was: <%s>", otherValue, value));
        Observable<Object> oi = Observable.just(value, otherValue);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValue(otherValue);
    }

    // hasReceivedLastValue

    @Test
    public void hasReceivedLastValue_doesNotAssert_whenSourceObservableEmitsOnceEqual() {
        Object value = new Object();
        Observable<Object> oi = Observable.just(value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValue(value);
    }

    @Test
    public void hasReceivedLastValue_doesNotAssert_whenSourceObservableEmitsMultipleLastEqual() {
        Object value = new Object();
        Observable<Object> oi = Observable.just(new Object(), value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValue(value);
    }

    @Test
    public void hasReceivedLastValue_asserts_whenSourceObservableEmitsMultipleLastValueNotEqualsOtherEquals() {
        Object value = new Object();
        Object otherValue = new Object();
        expectAssertionErrorWithMessage(String.format("Expected last received onNext event to be: <%s>, but was: <%s>.", otherValue, value));
        Observable<Object> oi = Observable.just(otherValue, value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValue(otherValue);
    }

    @Test
    public void hasReceivedLastValue_asserts_whenSourceObservableEmitsOnceNotEqual() {
        Object otherValue = new Object();
        Object value = new Object();
        expectAssertionErrorWithMessage(String.format("Expected last received onNext event to be: <%s>, but was: <%s>.", otherValue, value));
        Observable<Object> oi = Observable.just(value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValue(otherValue);
    }

    @Test
    public void hasReceivedLastValue_asserts_whenSourceObservableIsEmpty() {
        expectAssertionErrorWithMessage("Expected received onNext events not to be empty.");
        Observable<Object> oi = Observable.empty();
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValue(new Object());
    }

    @Test
    public void hasReceivedLastValue_asserts_whenSourceObservableEmitsMultipleOtherEquals() {
        Object otherValue = new Object();
        Object value = new Object();
        expectAssertionErrorWithMessage(String.format("Expected last received onNext event to be: <%s>, but was: <%s>", otherValue, value));
        Observable<Object> oi = Observable.just(otherValue, value);
        TestSubscriber<Object> ts = new TestSubscriber<Object>();
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValue(otherValue);
    }

    // hasReceivedValues

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

    // hasReceivedValueWhich

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

    // hasReceivedFirstValueWhich

    @Test
    public void hasReceivedFirstValueWhich_returnsNonNullAssertionInstance() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Collections.singletonList(new Object()));

        AbstractObjectAssert<?, Object> instance =
                new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValueWhich();

        assertThat(instance).isNotNull();
    }

    @Test
    public void hasReceivedFirstValueWhich_doesNotAssert_whenSingleOnNextEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Collections.singletonList(new Object()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValueWhich();
    }

    @Test
    public void hasReceivedFirstValueWhich_doesNotAssert_whenMultipleSingleOnNextEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Arrays.asList(new Object(), new Object()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValueWhich();
    }

    @Test
    public void hasReceivedFirstValueWhich_doesNotAssert_whenMultipleOnNextEvents() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Arrays.asList(new Object(), new Object()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedFirstValueWhich();
    }

    // hasReceivedLastValueWhich

    @Test
    public void hasReceivedLastValueWhich_returnsNonNullAssertionInstance() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Collections.singletonList(new Object()));

        AbstractObjectAssert<?, Object> instance =
                new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValueWhich();

        assertThat(instance).isNotNull();
    }

    @Test
    public void hasReceivedLastValueWhich_doesNotAssert_whenSingleOnNextEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Collections.singletonList(new Object()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValueWhich();
    }

    @Test
    public void hasReceivedLastValueWhich_doesNotAssert_whenMultipleSingleOnNextEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Arrays.asList(new Object(), new Object()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValueWhich();
    }

    @Test
    public void hasReceivedLastValueWhich_doesNotAssert_whenMultipleOnNextEvents() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);
        when(ts.getOnNextEvents()).thenReturn(Arrays.asList(new Object(), new Object()));

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasReceivedLastValueWhich();
    }

    // hasErrorWhich

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

    private void expectAssertionErrorWithMessage(final String message) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(message);
    }

}
