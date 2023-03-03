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
     * We can also use the boolean isActive flag to check if the cancellation status of the coroutine.
     */
    val job: Job = launch {
        for (i in 0..500) {
            print(" $i ")
            yield() // executes very fast, and makes coroutine cooperative.
        }
    }

    delay(7)
    job.cancelAndJoin() // cancels the coroutine.

    print("\n")

    val job2: Job = launch(Dispatchers.Default) {
        // try catch block for cancellation exception handling with coroutines.
        try {
            for (i in 0..500) {
                if (!isActive) {
                    break
                } // another way to make coroutine cooperative
                print(" $i ")
            }
        } catch (e: CancellationException) {
            println("Exception caught safely.")
        } finally {
            println("Finally block.")
        }
    }

    delay(5)
    job2.cancel("My own crash message.")
    job2.join()

    println("\nEnd of main program: ${Thread.currentThread().name}")

}