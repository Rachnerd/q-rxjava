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

        number$.onNext(1);

        //----------end-----------------

        Assert.assertEquals(number.get(), 1);
    }


    @Test()
    public void assignment2() {
        //---------start----------------x

        Subject<Integer> number$ = BehaviorSubject.create();

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

        Observable
                .combineLatest(text$, number$, (s, n) -> s + n)
                .subscribe(text::append);

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

        number$
                .delay(99, TimeUnit.MILLISECONDS)
                .subscribe(number::set);

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

        number$.onComplete();

        //---------end----------------
        number$.onNext(3);

        Assert.assertEquals(numbers.size(), 2);
    }


    @Test()
    public void assignment6() throws InterruptedException {
        Subject<Integer> number$ = PublishSubject.create();
        List<Integer> numbers = new ArrayList<>();

        //---------start--------------

        Disposable d = number$
                .subscribe(numbers::add);

        //---------end----------------

        number$.onNext(1);
        number$.onNext(2);

        //---------start--------------

        d.dispose();

        //---------end----------------
        number$.onNext(3);

        Assert.assertEquals(numbers.size(), 2);
        Assert.assertEquals(number$.hasComplete(), false);
    }

    @Test()
    public void assignment7() throws InterruptedException {
        Subject<Integer> number$ = PublishSubject.create();
        List<Integer> numbers = new ArrayList<>();

        //---------start--------------

        Disposable d = number$
                .subscribe(numbers::add);

        //---------end----------------

        number$.onNext(1);
        number$.onNext(2);

        //---------start--------------

        d.dispose();

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
        TestScheduler scheduler = new TestScheduler();
        number$
                .delay(99, TimeUnit.MILLISECONDS, scheduler)
                .subscribe(number::set);

        //---------end----------------

        number$.onNext(1);

        Assert.assertEquals(number.get(), 0);

        //---------start--------------
        scheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS);
        //---------end--------------

        Assert.assertEquals(number.get(), 1);
    }
}
