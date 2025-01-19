package cc.duduhuo.git.conflict.tool.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CachedProperty<T>(private val initial: () -> T) : ReadWriteProperty<Any?, T> {

    var cache: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (cache == null) {
            cache = initial()
        }
        return cache!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        cache = value
    }
}
