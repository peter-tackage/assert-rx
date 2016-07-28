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

public class TestSubscriberAssertDelegatedTest {

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

}
