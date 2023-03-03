package cancellationwithcoroutines

import kotlinx.coroutines.*

/**
 * Why cancel a coroutine?
 *
 * Result no longer needed;
 * Taking too long to perform a tasks.
 * Etc.
 */

fun main() = runBlocking {

    println("Start of main program: ${Thread.currentThread().name}")

    /**
     * Coroutine must be cooperative to cancel.
     *
     * Cooperative?
     * Must invoke/use a suspending function that checks for cancellation - delay(), withContext(), etc.
     * in the coroutine.
     *
     */
    val job: Job = launch {
        for (i in 0..500) {
            print(" $i ")
            delay(50)
        }
    }

    delay(500)
    job.cancel() // cancels the coroutine.

    println("\nEnd of main program: ${Thread.currentThread().name}")

}