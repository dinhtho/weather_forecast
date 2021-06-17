package com.nab.weatherforecast.view_model.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Method
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

/**
 * Created by tho nguyen on 6/18/2021.
 * dinhthohcmus@gmail.com
 */


suspend fun Method.invokeSuspend(obj: Any, vararg args: Any?): Any? =
    suspendCoroutineUninterceptedOrReturn { cont ->
        invoke(obj, *args, cont)
    }

fun <T> Class<T>.getMethodByName(methodName: String): Method? {
    return declaredMethods.firstOrNull { it.name == methodName }?.apply {
        isAccessible = true
    }
}

suspend fun invokeSuspendMethod(obj: Any, methodName: String, vararg args: Any?) {
    obj.javaClass.getMethodByName(methodName)?.invokeSuspend(obj, *args)
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
