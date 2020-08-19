package com.hjhjw1991.stark.framework

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


/**
 * shake detector
 * @author huangjun.barney
 * @since 2020-05-15
 */

class ShakeDetector : SensorEventListener {
    private var listener: OnShakeListener? = null
    private var shakeTimestamp: Long = 0
    fun setOnShakeListener(listener: OnShakeListener?) {
        this.listener = listener
    }

    interface OnShakeListener {
        fun onShake()
    }

    fun setShakeGestureSensitivity(sensitivity: Float) {
        shakeThresholdGravity = sensitivity
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if (listener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH

            // gForce will be close to 1 when there is no movement
            val gForce =
                Math.sqrt(gX * gX + gY * gY + (gZ * gZ).toDouble()).toFloat()
            if (gForce > shakeThresholdGravity) {
                val now = System.currentTimeMillis()
                // ignore shake events too close to each other (500ms)
                if (shakeTimestamp + SHAKE_SPACING_TIME_MS > now) {
                    return
                }
                shakeTimestamp = now
                listener!!.onShake()
            }
        }
    }

    companion object {
        private var shakeThresholdGravity = 3.0f
        private const val SHAKE_SPACING_TIME_MS = 500
    }
}