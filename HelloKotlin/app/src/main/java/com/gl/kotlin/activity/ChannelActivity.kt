package com.gl.kotlin.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gl.kotlin.databinding.ActivityChannelBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

class ChannelActivity : AppCompatActivity() {

    private val mBinding: ActivityChannelBinding by lazy {
        ActivityChannelBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
    /* 输出：
        1
        4
        9
        16
        25
        Done! */

    fun biscChannel() = runBlocking {
        //sampleStart
        val channel = Channel<Int>()
        launch {
            // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
            for (x in 1..5) channel.send(x * x)
        }
        // here we print five received integers:
        repeat(5) { println(channel.receive()) }
        println("Done!")
        //sampleEnd
    }


    fun closeChannel() = runBlocking {
        //sampleStart
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x * x)
            channel.close() // we're done sending
        }
        // here we print received values using `for` loop (until the channel is closed)
        for (y in channel) println(y)
        println("Done!")
        //sampleEnd
    }

    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
        for (x in 1..5) send(x * x)
    }

    fun buildChannelProducer() = runBlocking {
        //sampleStart
        val squares = produceSquares()
        squares.consumeEach { println(it) }
        println("Done!")
        //sampleEnd
    }

    /*  输出结果：
      Processor #2 received 1
      Processor #4 received 2
      Processor #0 received 3
      Processor #1 received 4
      Processor #3 received 5
      Processor #2 received 6
      Processor #4 received 7
      Processor #0 received 8
      Processor #1 received 9
      Processor #3 received 10 */

    // 多个协程可以从同一个通道接收数据，在它们之间分配任务
    fun fanOutChannel() = runBlocking<Unit> {
        //sampleStart
        val producer = produceNumbers()
        repeat(5) { launchProcessor(it, producer) }
        delay(950)
        producer.cancel() // cancel producer coroutine and thus kill them all
        //sampleEnd
    }

    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1 // start from 1
        while (true) {
            send(x++) // produce next
            delay(100) // wait 0.1s
        }
    }

    fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        for (msg in channel) {
            println("Processor #$id received $msg")
        }
    }

    /* 输出结果：
     foo
     foo
     BAR!
     foo
     foo
     BAR!*/

    // 多个协程可以发送到同一个通道
    fun fanInChannel() = runBlocking {
        //sampleStart
        val channel = Channel<String>()
        launch { sendString(channel, "foo", 200L) }
        launch { sendString(channel, "BAR!", 500L) }
        repeat(6) { // receive first six
            println(channel.receive())
        }
        coroutineContext.cancelChildren() // cancel all children to let main finish
        //sampleEnd
    }

    suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
        while (true) {
            delay(time)
            channel.send(s)
        }
    }

    /* 输出结果：
    Sending 0
    Sending 1
    Sending 2
    Sending 3
    Sending 4*/

    fun bufferChannel() = runBlocking<Unit> {
        //sampleStart
        val channel = Channel<Int>(4) // create buffered channel
        val sender = launch { // launch sender coroutine
            repeat(10) {
                println("Sending $it") // print before sending each element
                channel.send(it) // will suspend when buffer is full
            }
        }
        // don't receive anything... just wait....
        delay(1000)
        sender.cancel() // cancel sender coroutine
        //sampleEnd
    }

    //sampleStart
    data class Ball(var hits: Int)

    /* 输出结果：
    ping Ball(hits=1)
    pong Ball(hits=2)
    ping Ball(hits=3)
    pong Ball(hits=4)*/
    fun fairChannel() = runBlocking {
        val table = Channel<Ball>() // a shared table
        launch { player("ping", table) }
        launch { player("pong", table) }
        table.send(Ball(0)) // serve the ball
        delay(1000) // delay 1 second
        coroutineContext.cancelChildren() // game over, cancel them
    }

    suspend fun player(name: String, table: Channel<Ball>) {
        for (ball in table) { // receive the ball in a loop
            ball.hits++
            println("$name $ball")
            delay(300) // wait a bit
            table.send(ball) // send the ball back
        }
    }
    //sampleEnd

    /* 输出结果：
     Initial element is available immediately: kotlin.Unit
     Next element is not ready in 50 ms: null
     Next element is ready in 100 ms: kotlin.Unit
     Consumer pauses for 150ms
     Next element is available immediately after large consumer delay: kotlin.Unit
     Next element is ready in 50ms after consumer pause in 150ms: kotlin.Unit
     */
    fun tickerChannel() = runBlocking<Unit> {
        val tickerChannel =
            ticker(delayMillis = 100, initialDelayMillis = 0) // create ticker channel
        var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Initial element is available immediately: $nextElement") // initial delay hasn't passed yet

        nextElement =
            withTimeoutOrNull(50) { tickerChannel.receive() } // all subsequent elements has 100ms delay
        println("Next element is not ready in 50 ms: $nextElement")

        nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
        println("Next element is ready in 100 ms: $nextElement")

        // Emulate large consumption delays
        println("Consumer pauses for 150ms")
        delay(150)
        // Next element is available immediately
        nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Next element is available immediately after large consumer delay: $nextElement")
        // Note that the pause between `receive` calls is taken into account and next element arrives faster
        nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
        println("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

        tickerChannel.cancel() // indicate that no more elements are needed
    }

    fun View.setOnceClick(block: suspend () -> Unit) {
        val action = GlobalScope.actor<Unit> {
            for (event in channel) {
                block()
                delay(2000)//延迟2s
            }
        }
        setOnClickListener {
            action.offer(Unit)
        }
    }

}
