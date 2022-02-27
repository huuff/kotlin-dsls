package xyz.haff.dsl.cache

import com.google.common.base.Suppliers
import java.util.concurrent.TimeUnit
import kotlin.reflect.KProperty
import kotlin.time.Duration

class Memoized<T, U>(
    private val duration: Duration,
    private val getter: () -> T,
) {
    private val memoization = Suppliers.memoizeWithExpiration(getter, duration.inWholeMilliseconds, TimeUnit.MILLISECONDS)

    operator fun getValue(thisRef: U, property: KProperty<*>): T = memoization.get()
}