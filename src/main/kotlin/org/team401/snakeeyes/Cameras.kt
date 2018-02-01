package org.team401.snakeeyes

import org.team401.snakeeyes.camera.Camera
import org.team401.snakeeyes.service.Service
import java.util.*
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
    private val threads = Vector<Thread>()

    private class CameraTask(val camera: Camera): Runnable {
        val msPerFrame = (1000/camera.framerate).toLong()
        var time = 0L

        override fun run() {
            while (!Thread.interrupted()) {
                time = System.currentTimeMillis()
                camera.grab()
                camera.retrieve()
                Thread.sleep(Math.max(0, msPerFrame - (System.currentTimeMillis() - time)))
            }
        }
    }

    fun add(camera: Camera) {
        threads.add(Thread(CameraTask(camera)))
    }

    override fun start() {
        threads.forEach {
            it.start()
        }
    }

    override fun stop() {
        threads.forEach {
            it.interrupt()
        }
        threads.forEach {
            it.join()
        }
    }
}