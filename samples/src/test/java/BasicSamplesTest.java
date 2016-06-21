import org.junit.Ignore;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import java.util.concurrent.TimeUnit;

import static com.petertackage.assertrx.Assertions.assertThat;

/**
 * Some more real-world style test samples.
 * <p/>
 * Remove @Ignore annotations to experiment.
 */
public class BasicSamplesTest {

    @Test
    public void testDelay() {
        // Not an ideal way of testing Observables with concurrency because it will still take 10 seconds!
        String value = "avalue";
        Observable<String> delayObservable = Observable.just(value).delay(10, TimeUnit.SECONDS);

        TestSubscriber<String> ts = TestSubscriber.create();
        delayObservable.subscribe(ts);

        assertThat(ts)
                .afterTerminalEvent()
                .hasReceivedValue(value);
    }

    @Test
    public void testFiltering() {
        String value = "catalog";
        String value2 = "caterpillar";
        String value3 = "dog";
        Observable<String> filteredObservable = Observable.just(value, value2, value3)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.startsWith("cat");
                    }
                });

        TestSubscriber<String> ts = TestSubscriber.create();
        filteredObservable.subscribe(ts);

        assertThat(ts)
                .hasNoErrors()
                .hasReceivedValuesWhich()
                .containsOnly(value, value2);
    }

    @Test
    @Ignore("This test is intentionally failing to demonstrate the behavior - ignored so that the build passes")
    public void testFilteringEmitsUnexpectedValue() {
        String value = "catalog";
        String value2 = "caterpillar";
        String value3 = "dog";
        Observable<String> filteredObservable = Observable.just(value, value2, value3)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.startsWith("cat");
                    }
                });

        TestSubscriber<String> ts = TestSubscriber.create();
        filteredObservable.subscribe(ts);

        assertThat(ts)
                .hasNoErrors()
                .hasReceivedValuesWhich()
                .containsSequence(value, value2, value3);
    }

    @Test
    @Ignore("This test is intentionally failing to demonstrate the behavior - ignored so that the build passes")
    public void testFilteringErrors() {
        String value = "catalog";
        Observable<String> erroringFilteringObservable = Observable.just(value).
                concatWith(Observable.<String>error(new NullPointerException()));

        TestSubscriber<String> ts = TestSubscriber.create();
        erroringFilteringObservable.subscribe(ts);

        assertThat(ts)
                .hasNoErrors()
                .hasReceivedValuesWhich()
                .containsOnly(value);
    }

    @Test
    @Ignore("This test is intentionally failing to demonstrate the behavior - ignored so that the build passes")
    public void testNeverErrors() {
        Observable<String> empty = Observable.never();

        TestSubscriber<String> ts = TestSubscriber.create();
        empty.subscribe(ts);

        assertThat(ts).hasTerminalEvent();
    }

}
