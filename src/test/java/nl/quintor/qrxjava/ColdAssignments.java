package nl.quintor.qrxjava;

import io.reactivex.Observable;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ColdAssignments {

    @Test()
    public void assignment1() {
        AtomicInteger number = new AtomicInteger();

        Observable.just(1)
                //---------start----------------
                .map(n -> ++n)
                //----------end-----------------
                .subscribe(number::set);


        Assert.assertEquals(number.get(), 2);
    }

    @Test()
    public void assignment2() {
        List<Integer> numbers = new ArrayList<>();

        Observable.just(1, 2, 3, 4)
                //---------start----------------
                .filter(n -> n % 2 == 0)
                //----------end-----------------
                .subscribe(numbers::add);

        Assert.assertEquals(numbers, Arrays.asList(2, 4));
    }

    @Test()
    public void assignment3() {
        AtomicInteger number = new AtomicInteger();


        Observable.just(1, 2, 3, 4)
                //---------start----------------
                .scan((n1, n2) -> n1 + n2)
                //----------end-----------------
                .subscribe(number::set);

        Assert.assertEquals(number.get(), 10);
    }


    @Test()
    public void assignment4() {
        AtomicInteger number = new AtomicInteger();


        Observable.just(-33, 20, -20, 5, 10, 2)
                //---------start----------------
                .filter(n -> n > 0)
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .scan((n1, n2) -> n1 + n2)
                //----------end-----------------
                .subscribe(number::set);

        Assert.assertEquals(number.get(), 64); // (10 + 20 + 2) * 2
    }

    @Test()
    public void assignment5() {
        List<Integer> numbers = new ArrayList<>();

        Observable<Integer> odd$ = Observable.just(1, 2);
        Observable<Integer> even$ = Observable.just(3, 4);

        //---------start----------------

        Observable.merge(odd$, even$)
                .subscribe(numbers::add);

        //----------end-----------------

        Assert.assertEquals(numbers, Arrays.asList(1, 2, 3, 4));
    }

}
