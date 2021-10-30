package ru.borhammere.dildo

import android.content.Context
import android.util.Log
import kotlin.reflect.KClass

private const val TAG = "DI"

object Dildo {
    const val DEFAULT_NAME = "default"

    private var dependenciesMap: Map<DependencyKey, Dependency>? = null

    fun initialize(context: Context, dependenciesArray: Array<Dependency>) {
        if (dependenciesMap != null) throw IllegalStateException("You can't call DI.initialize twice")

        dependenciesMap = dependenciesArray.asDependenciesMap().apply {
            val dep = singleton<Context> { context }
            put(dep.key, dep)
        }
    }

    fun get(clazz: KClass<*>, name: String = DEFAULT_NAME): Any {
        val key = DependencyKey(clazz, name)
        return dependenciesMap?.get(key)?.create()
            ?: throw IllegalStateException("Dependency was not defined")
    }

    inline fun <reified T> inject(name: String = DEFAULT_NAME): T = get(T::class, name) as T

    internal data class DependencyKey(
        private val clazz: KClass<*>,
        private val name: String = DEFAULT_NAME
    )

    abstract class Dependency(
        protected val clazz: KClass<*>,
        protected val creator: () -> Any
    ) {
        internal abstract fun create(): Any
        internal abstract val key: DependencyKey
    }

    class Factory(clazz: KClass<*>, creator: () -> Any) : Dependency(clazz, creator) {
        override fun create(): Any = creator()
        override val key: DependencyKey = DependencyKey(clazz)
    }

    class Singleton(
        clazz: KClass<*>,
        private val name: String = DEFAULT_NAME,
        creator: () -> Any
    ) : Dependency(clazz, creator) {

        private val instance by lazy { creator() }
        override fun create(): Any = instance.apply {
            Log.d(TAG, "create $name : ${clazz.qualifiedName}/${this.hashCode()}")
        }

        override val key: DependencyKey = DependencyKey(clazz, name)
    }

    inline fun <reified T> singleton(
        name: String = DEFAULT_NAME,
        noinline creator: () -> Any
    ): Singleton {
        return Singleton(T::class, name, creator)
    }

    inline fun <reified T> factory(
        noinline creator: () -> Any
    ): Factory {
        return Factory(T::class, creator)
    }
}

private fun Array<Dildo.Dependency>.asDependenciesMap(): MutableMap<Dildo.DependencyKey, Dildo.Dependency> =
    if (isNotEmpty()) {
        map { Pair(it.key, it) }.toMap(HashMap(size))
    } else {
        HashMap()
    }
