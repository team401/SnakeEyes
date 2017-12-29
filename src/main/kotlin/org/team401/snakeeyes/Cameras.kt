package org.team401.snakeeyes

import org.team401.snakeeyes.camera.Camera
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/*
 * snakeeyes - Created on 12/28/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 12/28/17
 */

object Cameras: Service {
    private val cameras = arrayListOf<Camera>()
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private var future: ScheduledFuture<*>? = null

    var framerate = 30

    fun add(camera: Camera) {
        cameras.add(camera)
    }

    override fun start() {
        future = executor.scheduleAtFixedRate({
            cameras.forEach {
                it.grab()
            }
            cameras.forEach {
                it.retrieve()
            }
        }, 0L, (1000/framerate).toLong(), TimeUnit.MILLISECONDS)
    }

    override fun stop() {
        future?.cancel(false)
    }
}