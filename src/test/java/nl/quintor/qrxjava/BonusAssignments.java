package nl.quintor.qrxjava;

import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BonusAssignments {

    @Test
    public void assignment1() {
        Observable<Integer> allNumbers$ = Observable.just(1, 2, 3, 4, 5);
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();

        //---------start----------------

        Observable<GroupedObservable<Boolean, Integer>> groupedEvenNumbers = allNumbers$
                .groupBy(n -> n % 2 == 0);

        groupedEvenNumbers
                .filter(GroupedObservable::getKey)
                .switchMap(g -> g)
                .subscribe(even::add);

        groupedEvenNumbers
                .filter(g -> !g.getKey())
                .switchMap(g -> g)
                .subscribe(odd::add);

        //----------end-----------------

        Assert.assertEquals(even, Arrays.asList(2, 4));
        Assert.assertEquals(odd, Arrays.asList(1, 3, 5));

    }
}
