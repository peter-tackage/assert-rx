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
import org.mockito.Matchers;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class TestSubscriberAssertTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // Delegated tests - only test that the appropriate TestSubscriber assert method is invoked.

    @Test
    public void afterTerminalEvent_invokesTestSubscriberAwaitTerminalEvent() {
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).afterTerminalEvent();

        verify(ts).awaitTerminalEvent();
    }

    @Test
    public void afterTerminalEventWithTimeout_invokesTestSubscriberAwaitTerminalEvent() {
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);
        long timeoutValue = 5;
        TimeUnit unitValue = TimeUnit.DAYS;

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).afterTerminalEvent(timeoutValue, unitValue);

        verify(ts).awaitTerminalEvent(eq(timeoutValue), eq(unitValue));
    }

    @Test
    public void afterTerminalEventAndUnsubscribingIfTimeout_invokesTestSubscriberAwaitTerminalEvent() {
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);
        long timeoutValue = 5;
        TimeUnit unitValue = TimeUnit.DAYS;

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class)
                .afterTerminalEventAndUnsubscribingIfTimeout(timeoutValue, unitValue);

        verify(ts).awaitTerminalEventAndUnsubscribeOnTimeout(eq(timeoutValue), eq(unitValue));
    }


    @Test
    public void hasNoValues_invokesTestSubscribeAssertNoValues() {
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasNoValues();

        verify(ts).assertNoValues();
    }

    @Test
    public void hasReceivedValue_invokesTestSubscriberAssertValue() {
        Integer value = 1;
        Observable<Integer> oi = Observable.just(value);
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);
        oi.subscribe(ts);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasReceivedValue(value);

        verify(ts).assertValue(eq(value));
    }

    @Test
    public void hasReceivedValues_invokesTestSubscriberAssertReceivedOnNextValue_whenMultiple() {
        List<Integer> values = Arrays.asList(1, 2, 3);
        Observable<Integer> oi = Observable.from(values);
        // Requires spy as decoration of TestSubscriber as Subscriber accesses TestSubscriber instance internals
        // which are not initialized when using a mock.
        TestSubscriber<Integer> ts = spy(new TestSubscriber<Integer>());
        oi.subscribe(ts);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasReceivedValues(values);

        verify(ts).assertReceivedOnNext(eq(values));
    }

    @Test
    public void hasReceivedValuesVarArgs_invokesTestSubscriberAssertValues_whenMultiple() {
        Integer[] values = {1, 2, 3};
        Observable<Integer> oi = Observable.from(values);
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);
        oi.subscribe(ts);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasReceivedValues(1, 2, 3);

        verify(ts).assertValues(Matchers.<Integer>anyVararg());
    }

    @Test
    public void hasValueCount_invokesTestSubscriberAssertValueCount() {
        List<Integer> values = Arrays.asList(1, 2, 3);
        Observable<Integer> oi = Observable.from(values);
        // Requires spy as decoration of TestSubscriber as Subscriber accesses TestSubscriber instance internals
        // which are not initialized when using a mock.
        TestSubscriber<Integer> ts = spy(new TestSubscriber<Integer>());
        oi.subscribe(ts);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasValueCount(values.size());

        verify(ts).assertValueCount(eq(values.size()));
    }

    @Test
    public void hasCompleted_invokesTestSubscriberAssertCompleted() {
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasCompleted();

        verify(ts).assertCompleted();
    }

    @Test
    public void hasNotCompleted_invokesTestSubscriberAssertNotCompleted() {
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasNotCompleted();

        verify(ts).assertNotCompleted();
    }

    @Test
    public void hasNoErrors_invokesTestSubscriberAssertNoErrors() {
        //noinspection unchecked
        TestSubscriber<Integer> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Integer>(ts, TestSubscriberAssert.class).hasNoErrors();

        verify(ts).assertNoErrors();
    }

    @Test
    public void hasTerminalEvent_invokesTestSubscriberAssertTerminalEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasTerminalEvent();

        verify(ts).assertTerminalEvent();
    }

    @Test
    public void hasNoTerminalEvent_invokesTestSubscriberAssertNoTerminalEvent() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasNoTerminalEvent();

        verify(ts).assertNoTerminalEvent();
    }

    @Test
    public void isUnsubscribed_invokesTestSubscriberAssertUnsubscribed() {
        //noinspection unchecked
        TestSubscriber<Object> ts = mock(TestSubscriber.class);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).isUnsubscribed();

        verify(ts).assertUnsubscribed();
    }

    @Test
    public void hasErrorInstance_invokesTestSubscriberAssertErrorInstance() {
        Throwable throwable = new Throwable();
        Observable<Object> oi = Observable.error(throwable);
        // Requires spy as decoration of TestSubscriber as Subscriber accesses TestSubscriber instance internals
        // which are not initialized when using a mock.
        TestSubscriber<Object> ts = spy(new TestSubscriber<Object>());
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasError(throwable);

        verify(ts).assertError(eq(throwable));
    }

    @Test
    public void hasErrorClass_invokesTestSubscriberAssertErrorClass() {
        Throwable throwable = new Throwable();
        Observable<Object> oi = Observable.error(throwable);
        // Requires spy as decoration of TestSubscriber as Subscriber accesses TestSubscriber instance internals
        // which are not initialized when using a mock.
        TestSubscriber<Object> ts = spy(new TestSubscriber<Object>());
        oi.subscribe(ts);

        new TestSubscriberAssert<Object>(ts, TestSubscriberAssert.class).hasError(throwable.getClass());

        verify(ts).assertError(eq(throwable.getClass()));
    }

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
