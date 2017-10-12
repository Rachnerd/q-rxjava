package nl.quintor.qrxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HotAssignments {

    @Test()
    public void assignment1() {
        Subject<Integer> number$ = PublishSubject.create();
        AtomicInteger number = new AtomicInteger();

        number$
                .subscribe(number::set);

        //---------start----------------

        //----------end-----------------

        Assert.assertEquals(number.get(), 1);
    }


    @Test()
    public void assignment2() {
        //---------start----------------x

        Subject<Integer> number$ = null;

        //----------end-----------------

        AtomicInteger number = new AtomicInteger();

        number$.onNext(1);

        number$
                .subscribe(number::set);

        Assert.assertEquals(number.get(), 1);
    }

    @Test()
    public void assignment3() {
        Subject<Integer> number$ = PublishSubject.create();
        Subject<String> text$ = PublishSubject.create();
        StringBuilder text = new StringBuilder();

        //---------start--------------

        //---------end----------------

        text$.onNext("Number: ");

        number$.onNext(1);

        Assert.assertEquals(text.toString(), "Number: 1");
    }

    @Test()
    public void assignment4() throws InterruptedException {
        Subject<Integer> number$ = PublishSubject.create();
        AtomicInteger number = new AtomicInteger();

        //---------start--------------

        //---------end----------------

        number$.onNext(1);

        Assert.assertEquals(number.get(), 0);

        Thread.sleep(100);

        Assert.assertEquals(number.get(), 1);
    }

    @Test()
    public void assignment5() throws InterruptedException {
        Subject<Integer> number$ = PublishSubject.create();
        List<Integer> numbers = new ArrayList<>();

        number$
                .subscribe(numbers::add);

        number$.onNext(1);
        number$.onNext(2);

        //---------start--------------

        //---------end----------------
        number$.onNext(3);

        Assert.assertEquals(numbers.size(), 2);
    }


    @Test()
    public void assignment6() throws InterruptedException {
        Subject<Integer> number$ = PublishSubject.create();
        List<Integer> numbers = new ArrayList<>();

        //---------start--------------

        //---------end----------------

        number$.onNext(1);
        number$.onNext(2);

        //---------start--------------

        //---------end----------------
        number$.onNext(3);

        Assert.assertEquals(numbers.size(), 2);
        Assert.assertEquals(number$.hasComplete(), false);
    }


    @Test()
    public void assignment8() {
        Subject<Integer> number$ = PublishSubject.create();
        AtomicInteger number = new AtomicInteger();

        //---------start--------------
        // HINT: TestScheduler

        //---------end----------------

        number$.onNext(1);

        Assert.assertEquals(number.get(), 0);

        //---------start--------------

        //---------end--------------

        Assert.assertEquals(number.get(), 1);
    }

    /**
     * BONUS
     */
    @Test
    public void assignment9() {
        Observable<Integer> allNumbers$ = Observable.just(1, 2, 3, 4, 5);
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();

        //---------start----------------

        //----------end-----------------

        Assert.assertEquals(even, Arrays.asList(2, 4));
        Assert.assertEquals(odd, Arrays.asList(1, 3, 5));

    }
}
