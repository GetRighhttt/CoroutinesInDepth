package coroutinebuilders

import kotlinx.coroutines.*

/**
 * Coroutine Builders are nothing ;more than functions that we use to create coroutines.
 * Declaring a Coroutine which operates IN a background thread...
 *
 * launch, async, and runblocking are coroutine builders that essentially just create coroutines.
 * Job can be used to execute a number of methods built in to the coroutines api including canceling coroutines.
 */
fun main() = runBlocking { // creates a blocking coroutine that executes in main

    println("Start of main program: ${Thread.currentThread().name}")

    /**
     * launch = standalone: known for fire and forget; launches then forgets essentially and doesn't block thread in which
    * it operates. Also, returns a job object that we can use to manipulate the coroutines.
     */
    val job: Job = launch {
        println("Start of launch worker thread: ${Thread.currentThread().name}")
        delay(1000)
        println("End of launch worker thread: ${Thread.currentThread().name}")
    }

    /**
     * async = deferred; doesn't return a job object but instead returns a deferred object which is a subclass of Job.
     * The return type is of a generic type, so we can pass whatever we want into it.
     *
     * Async function is usually used when we want to return some data. It is pretty much the same as launch
     * except it returns a Deferred<T> object of generic type.
     */
    val jobDeferred: Deferred<Int> = async {
        println("Start of async worker thread: ${Thread.currentThread().name}")
        delay(1000)
        println("End of async worker thread: ${Thread.currentThread().name}")
        22// returns a deferred object and here we pass that object as an int
    }

    val numberFromAsync = jobDeferred.await() // wait for coroutine to finish and retrieve the value from the coroutine.
    println("The value from the async deferred job is: $numberFromAsync !")


    // join() is used to wait for coroutine to finish executing, then next statement will be executed.
    job.join()
    println("End of main program: ${Thread.currentThread().name}")

}