package org.team401.snakeeyes.config


/*
import org.team401.snakeeyes.config.`object`.ConfigItem
import org.team401.snakeeyes.config.`object`.ConfigList
import org.team401.snakeeyes.config.`object`.ConfigObject
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.collections.LinkedHashMap

/*
 * snakeeyes - Created on 12/21/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 12/21/17
 */

class UserConfig {
    private val config = LinkedHashMap<String, Any?>()
    @Transient private val items = LinkedHashMap<String, ConfigObject<*>>()

    internal fun postDeserialize() {
        config.forEach {
            key, value ->
            when (value) {
                is Boolean, Double, Int, String -> items.put(key, ConfigItem(value))
                is List<*> -> items.put(key, ConfigList(value))
            }
        }
    }

    internal fun preSerialize() {
        config.clear()
        items.forEach {
            key, value ->
            when (value) {
                is ConfigItem<*> -> config.put(key, value.get())
                is ConfigList<*> -> config.put(key, ArrayList(value))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getItem(key: String, type: String, default: T?): ConfigItem<T> {
        try {
            return if (items.containsKey(key)) {
                items[key] as ConfigItem<T>
            } else {
                if (default != null) {
                    ConfigItem(default)
                } else {
                    throw ConfigException("Item '$key' not found")
                }
            }
        } catch (e: ClassCastException) {
            throw ConfigException("Item '$key' is not of type '$type'")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getList(key: String, type: String, default: List<T>?): ConfigList<T> {
        try {
            return if (items.containsKey(key)) {
                items[key] as ConfigList<T>
            } else {
                if (default != null) {
                    return ConfigList(default)
                } else {
                    throw ConfigException("List '$key' not found")
                }
            }
        } catch (e: ClassCastException) {
            throw ConfigException("List '$key' is not of type '$type'")
        }
    }

    fun getInt(key: String, default: Int? = null) = getItem(key, "int", default)
    fun getString(key: String, default: String? = null) = getItem(key, "String", default)
    fun getDouble(key: String, default: Double? = null) = getItem(key, "double", default)
    fun getBoolean(key: String, default: Boolean? = null) = getItem(key, "boolean", default)

    fun getIntList(key: String, default: List<Int>? = null) = getList(key, "int", default)
    fun getStringList(key: String, default: List<String>? = null) = getList(key, "String", default)
    fun getDoubleList(key: String, default: List<Double>? = null) = getList(key, "double", default)
    fun getBooleanList(key: String, default: List<Boolean>? = null) = getList(key, "boolean", default)
}
*/