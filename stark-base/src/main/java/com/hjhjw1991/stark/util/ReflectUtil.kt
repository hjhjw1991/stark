package com.hjhjw1991.stark.util

import android.util.Log

/**
 * 反射工具类. 为了不新增依赖故自己做了一个
 * @author huangjun.barney
 * @since 2020-06-02
 */

object ReflectUtil {
    private val tag = "Stark"
    fun visit(obj: Any) {
        val aClass = obj.javaClass
//    aClass.fields 无法获取 kotlin 创建的成员
        val declaredFields = aClass.declaredFields
        val methods = aClass.methods
        for (mt in methods) {
            val mn = mt.name
            val dv = mt.defaultValue
            val returnType = mt.returnType.name
            Log.d(tag, "method name: $mn    default value: $dv    return type: $returnType")
        }
        for (field in declaredFields) {
            try {
                field.isAccessible = true // 如果不设置不能获对应的值
                val fn = field.name
                Log.d(tag, "field name: $fn  field value: ${field.get(obj)}")
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    fun getField(obj: Any, fieldName: String) : Any? {
        val clazz = obj.javaClass
        val field = clazz.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(obj)
    }

    fun setField(obj: Any, fieldName: String, value: Any?) {
        val clazz = obj.javaClass
        val field = clazz.getDeclaredField(fieldName)
        field.apply {
            isAccessible = true
            set(obj, value)
        }
    }

    fun replaceField(obj: Any, fieldName: String, newValue: Any?) : Any? {
        val old = getField(obj, fieldName)
        setField(obj, fieldName, newValue)
        return old
    }
}