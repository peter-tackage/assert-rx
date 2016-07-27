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

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.util.Objects;
import rx.observers.TestSubscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Assertion methods for {@link rx.observers.TestSubscriber}s.
 * <p>
 * To create an instance of this class, invoke <code>{@link Assertions#assertThat(TestSubscriber)}</code>.
 * </p>
 */
public final class TestSubscriberAssert<T> extends AbstractAssert<TestSubscriberAssert<T>, TestSubscriber<T>> {

    TestSubscriberAssert(final TestSubscriber<T> actual, final Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * See {@link TestSubscriber#awaitTerminalEvent()}
     */
    public TestSubscriberAssert<T> afterTerminalEvent() {
        isNotNull();
        actual.awaitTerminalEvent();
        return this;
    }

    /**
     * See {@link TestSubscriber#awaitTerminalEvent(long, TimeUnit)}
     */
    public TestSubscriberAssert<T> afterTerminalEvent(final long timeout, final TimeUnit unit) {
        isNotNull();
        actual.awaitTerminalEvent(timeout, unit);
        return this;
    }

    /**
     * See {@link TestSubscriber#awaitTerminalEventAndUnsubscribeOnTimeout(long, TimeUnit)}
     */
    public TestSubscriberAssert<T> afterTerminalEventAndUnsubscribingIfTimeout(final long timeout, final TimeUnit unit) {
        isNotNull();
        actual.awaitTerminalEventAndUnsubscribeOnTimeout(timeout, unit);
        return this;
    }

    /**
     * See {@link TestSubscriber#assertNoValues()}
     */
    public TestSubscriberAssert<T> hasNoValues() {
        isNotNull();
        actual.assertNoValues();
        return this;
    }

    /**
     * See {@link TestSubscriber#assertReceivedOnNext(List)}
     */
    public TestSubscriberAssert<T> hasReceivedValues(final List<T> values) {
        isNotNull();
        actual.assertReceivedOnNext(values);
        return this;
    }

    /**
     * See {@link TestSubscriber#assertReceivedOnNext(List)}
     */
    public TestSubscriberAssert<T> hasReceivedValues(final T... values) {
        isNotNull();
        actual.assertValues(values);
        return this;
    }

    /**
     * See {@link TestSubscriber#assertValue(Object)}
     */
    public TestSubscriberAssert<T> hasReceivedValue(final T value) {
        isNotNull();
        actual.assertValue(value);
        return this;
    }

    /**
     * Passes when {@link TestSubscriber} has received any {@link TestSubscriber#onNext(Object)} event.
     *
     * @return this {@link TestSubscriberAssert}
     */
    public TestSubscriberAssert<T> hasReceivedValues() {
        isNotNull();
        List<T> actualEvents = actual.getOnNextEvents();
        assertThat(actualEvents)
                .overridingErrorMessage("Expected received onNext events not to be empty.")
                .isNotEmpty();
        return this;
    }

    /**
     * Returns a {@link AbstractAssert} for higher order assertions on the single received onNext value.
     */
    public AbstractObjectAssert<?, T> hasReceivedValueWhich() {
        isNotNull();
        List<T> received = actual.getOnNextEvents();
        assertThat(received)
                .overridingErrorMessage("Expected a single onNext value, but was: <%s>.", received)
                .isNotNull()
                .hasSize(1);
        return assertThat(received.get(0));
    }

    /**
     * Returns an {@link AbstractListAssert} for higher order assertions on the received onNext values.
     */
    public AbstractListAssert<?, ? extends List<? extends T>, T> hasReceivedValuesWhich() {
        isNotNull();
        return assertThat(actual.getOnNextEvents());
    }

    /**
     * See {@link TestSubscriber#assertValueCount(int)}
     */
    public TestSubscriberAssert<T> hasValueCount(final int count) {
        isNotNull();
        actual.assertValueCount(count);
        return this;
    }

    /**
     * See {@link TestSubscriber#assertCompleted()}
     */
    public TestSubscriberAssert<T> hasCompleted() {
        isNotNull();
        actual.assertCompleted();
        return this;
    }

    /**
     * See {@link TestSubscriber#assertNotCompleted()}
     */
    public TestSubscriberAssert<T> hasNotCompleted() {
        isNotNull();
        actual.assertNotCompleted();
        return this;
    }

    /**
     * See {@link TestSubscriber#assertNoErrors()}
     */
    public TestSubscriberAssert<T> hasNoErrors() {
        isNotNull();
        actual.assertNoErrors();
        return this;
    }

    /**
     * See {@link TestSubscriber#assertError(Throwable)}
     */
    public TestSubscriberAssert<T> hasError(final Throwable throwable) {
        isNotNull();
        assertThat(throwable).isNotNull();
        actual.assertError(throwable);
        return this;
    }

    /**
     * See {@link TestSubscriber#assertError(Class)}
     */
    public TestSubscriberAssert<T> hasError(final Class<? extends Throwable> clazz) {
        isNotNull();
        assertThat(clazz).isNotNull();
        actual.assertError(clazz);
        return this;
    }

    /**
     * Returns an {@link AbstractThrowableAssert} for higher order assertions on the single received onError value.
     */
    public AbstractThrowableAssert<?, ? extends Throwable> hasErrorWhich() {
        isNotNull();
        List<? extends Throwable> received = actual.getOnErrorEvents();
        assertThat(received)
                .overridingErrorMessage("Expected a single onError event, but was: <%s>.", received)
                .isNotNull()
                .hasSize(1);
        return assertThat(received.get(0));
    }

    /**
     * See {@link TestSubscriber#assertTerminalEvent()}
     */
    public TestSubscriberAssert<T> hasTerminalEvent() {
        isNotNull();
        actual.assertTerminalEvent();
        return this;
    }

    /**
     * See {@link TestSubscriber#assertNoTerminalEvent()}
     */
    public TestSubscriberAssert<T> hasNoTerminalEvent() {
        isNotNull();
        actual.assertNoTerminalEvent();
        return this;
    }

    /**
     * See {@link TestSubscriber#assertUnsubscribed()}
     */
    public TestSubscriberAssert<T> isUnsubscribed() {
        isNotNull();
        actual.assertUnsubscribed();
        return this;
    }

    /**
     * Passes when the {@link TestSubscriber}'s last seen thread (refer {@link TestSubscriber#lastSeenThread})
     * is equal to the supplied {@link Thread} parameter.
     *
     * @param thread the expected last seen {@link Thread}
     * @return this {@link TestSubscriberAssert}
     */
    public TestSubscriberAssert<T> wasLastObservedOn(final Thread thread) {
        isNotNull();
        final Thread lastSeenThread = actual.getLastSeenThread();
        if (!Objects.areEqual(lastSeenThread, thread)) {
            failWithMessage("Expected to be observed on: <%s>, was observed on: <%s>.", thread, lastSeenThread);
        }
        return this;
    }

}
