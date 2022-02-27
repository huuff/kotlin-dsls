package xyz.haff.dsl.cache

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.doubles.shouldNotBeExactly
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

class MemoizedTest : FunSpec({

    class LongMemoizedRandom {
        val value: Double by Memoized(1.hours) { Math.random() }
    }

    test("Correctly memoizes") {
        val memoized = LongMemoizedRandom()

        val firstValue = memoized.value
        val secondValue = memoized.value

        secondValue shouldBeExactly firstValue
    }

    class ShortMemoizedRandom {
        val value: Double by Memoized(1.milliseconds) { Math.random() }
    }

    test("Memoization expires") {
        val memoized = ShortMemoizedRandom()

        val firstValue = memoized.value
        Thread.sleep(2)
        val secondValue = memoized.value

        secondValue shouldNotBeExactly  firstValue
    }
})