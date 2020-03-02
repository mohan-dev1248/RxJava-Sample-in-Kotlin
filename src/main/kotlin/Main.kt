import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

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
