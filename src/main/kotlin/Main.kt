import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Timed
import java.util.*
import java.util.concurrent.TimeUnit


fun main() {

    initialCreate()
    multipleSubscriberExampleOne()
    multipleSubscriberExampleTwo()
    printAllNaturalNumbers()
    printNaturalNumbersTillHundred()
    printNaturalNumbersWithTimer()
}

fun initialCreate() {
    //first run
    log("Starting")
    ints.subscribe { num -> log("$num") }
    log("exit")
}

fun multipleSubscriberExampleOne() {
    log("Starting")
    intsMultipleSub.subscribe { num -> log("$num") }
    intsMultipleSub.subscribe { num -> log("$num") }
    log("exit")
}

fun multipleSubscriberExampleTwo() {
    log("Starting")
    val intMultipleSubTwo = intsMultipleSub.cache()
    intMultipleSubTwo.subscribe { num -> log("$num") }
    intMultipleSubTwo.subscribe { num -> log("$num") }
    intMultipleSubTwo.subscribe { num -> log("$num") }
    log("exit")
}

fun printAllNaturalNumbers(){
    log("Starting")
    val subscription = naturalNumbers.subscribe { num -> log("$num") }
//    thread {
//        Thread.sleep(1000)
//        subscription.dispose()
//    }.join()
    log("exit")
}

fun printNaturalNumbersTillHundred(){
    log("Starting")
    naturalNumbersTillHundred.subscribe{ num -> log("$num")}
    log("exit")
}

fun printNaturalNumbersWithTimer(){
    log("Starting")
    naturalNumbersTimeOut.timeout(100, TimeUnit.MILLISECONDS).subscribe { num -> log("$num") }
    log("exit")
}

fun mapFilterExample(){
    Observable
        .just(8, 9, 10)
        .doOnNext { i: Int -> println("A: $i") }
        .filter { i: Int -> i % 3 > 0 }
        .doOnNext { i: Int -> println("B: $i") }
        .map { i: Int -> "#" + i * 10 }
        .doOnNext { s: String -> println("C: $s") }
        .filter { s: String -> s.length < 4 }
        .subscribe { s: String -> println("D: $s") }
}

fun repeatFirstEightAlphabetsEightTimes(){
    val oneToEight = Observable.range(1, 8)
    val ranks = oneToEight
        .map { obj: Int -> obj.toString() }
    val files = oneToEight
        .map { x: Int -> 'a'.toInt() + x - 1 }
        .map { ascii: Int -> ascii.toChar() }
        .map { ch: Char? ->
            (ch!!).toString()
        }
    files.flatMap { file: String ->
            ranks.map { rank: String -> file + rank }
        }.subscribe{ s: String -> println(s)}
}


fun zipExample(){
    val red = Observable.interval(10, TimeUnit.MILLISECONDS)
    val green = Observable.interval(10, TimeUnit.MILLISECONDS)
    Observable.zip(
        red.timestamp(),
        green.timestamp(),
        BiFunction<Timed<Long>, Timed<Long>, Any> { r: Timed<Long>, g: Timed<Long> -> r.time() - g.time() }
    ).forEach { x: Any? -> println(x) }
}

fun scanExample(){
    val progress = Observable.just(10,14, 12, 15, 14,10)

    val totalProgress = progress.scan{ total, chunk -> total + chunk}.subscribe { it -> println("$it") }
}

fun reduceExample(){
    val all: Disposable? = Observable
        .range(10, 20)
        .reduce(
            ArrayList(),
            { list: ArrayList<Any?>, item: Int? ->
                list.add(item)
                list
            }
        ).subscribe { it -> it.forEach { item -> println("$item") } }
}