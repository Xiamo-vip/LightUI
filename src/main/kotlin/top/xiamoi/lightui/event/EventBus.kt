package top.xiamoi.lightui.event

import java.lang.reflect.Method
import java.lang.reflect.Modifier

@Target(AnnotationTarget.FUNCTION)
annotation class EventListener

internal fun getCallerClass(): Class<*>? {
    val stackTrace = Thread.currentThread().stackTrace
    for (i in stackTrace.indices) {
        if (stackTrace[i].className == Thread::class.java.name &&
            i + 3 < stackTrace.size) {
            return Class.forName(stackTrace[i + 3].className)
        }
    }
    return null
}

internal data class MethodCaller(val method: Method,val instance:Any?,val clazz:Class<*>) {}

open class Event() {
    fun broadcast() {
        EventBus.subscribers.forEach {
            if (it.method.parameters[0].type.isInstance(this)) {
                it.method.invoke(it.instance,this)
            }
        }
    }

}

object EventBus {
    internal val subscribers: MutableSet<MethodCaller> = mutableSetOf()
    fun subscribe(subscriber: Any) {
        unsubscribe(subscriber)
        for (method in subscriber.javaClass.methods) {
            if (method.isAnnotationPresent(EventListener::class.java) && method.parameterCount == 1) {
                val type = method.parameterTypes[0]
                if ((type.superclass != null && type.superclass == Event::class.java) || type == Event::class.java) {
                    subscribers.add(MethodCaller(method, if (Modifier.isStatic(method.modifiers)) {
                        null
                    } else {
                        subscriber
                    }, subscriber.javaClass))
                }

            }
        }
    }

    fun unsubscribe(subscriber: Any?) {
        if (subscriber == null) {
            getCallerClass()?.let {
                unsubscribe(it)
            }
            return
        }

        subscribers.removeIf {
            if (subscriber is Class<*>) {
                it.clazz == subscriber
            }
            else {
                it.instance === subscriber
            }
        }
    }




}


