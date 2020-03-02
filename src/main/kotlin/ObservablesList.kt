import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO


val observable: Observable<Tweet> = Observable.create {
    subscriber ->
    subscriber.onNext(Tweet("First Tweet"))
    subscriber.onComplete()
}

fun <T> just(x: T) : Observable<T>{
    return Observable.create{
        subscriber ->
        subscriber.onNext(x);
        subscriber.onComplete();
    }
}

fun <T> never(x: T): Observable<T>{
    return Observable.create {
        subscriber ->
    }
}

fun <T> empty(x: T): Observable<T>{
    return Observable.create {
        subscriber ->
        subscriber.onComplete()
    }
}

fun <T> from(iterator : Iterator<T>) : Observable<T>{
    return Observable.create {
        subscriber ->
        while (iterator.hasNext()){
            subscriber.onNext(iterator.next())
        }
        subscriber.onComplete()
    }
}

var ints: Observable<Int> = Observable.create{
        subscriber ->
    log("Create")
    subscriber.onNext(5)
    subscriber.onNext(6)
    subscriber.onNext(7)
    subscriber.onComplete()
    log("Completed")
}

val intsMultipleSub: Observable<Int> = Observable.create {
    subscriber ->
    log("Create")
    subscriber.onNext(42)
    subscriber.onComplete()
    log("Completed")
}

val naturalNumbers: Observable<BigInteger?> = Observable.create { subscriber: ObservableEmitter<BigInteger?> ->
    val r = Runnable {
        var i: BigInteger = ZERO
        while (!subscriber.isDisposed) {
            subscriber.onNext(i)
            i = i.add(ONE)
        }
    }
    Thread(r).start()
}

val naturalNumbersTillHundred: Observable<BigInteger?> = Observable.create { subscriber: ObservableEmitter<BigInteger?> ->
    val r = Runnable {
        var i: BigInteger = ZERO
        while (!subscriber.isDisposed) {
            if(i == BigInteger.valueOf(101)){
                subscriber.onComplete()
            }
            subscriber.onNext(i)
            i = i.add(ONE)
        }
    }
    Thread(r).start()
}

val naturalNumbersTimeOut: Observable<BigInteger?> = Observable.create { subscriber: ObservableEmitter<BigInteger?> ->
    val r = Runnable {
        var i: BigInteger = ZERO
        while (!subscriber.isDisposed) {
            Thread.sleep(95)
            //Thread.sleep(200)
            subscriber.onNext(i)
            i = i.add(ONE)
        }
    }
    Thread(r).start()
}

fun log(text: String) = println(text)