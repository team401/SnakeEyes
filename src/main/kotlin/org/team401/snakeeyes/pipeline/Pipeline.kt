package org.team401.snakeeyes.pipeline

import org.opencv.core.Mat
import org.team401.snakeeyes.MatProvider
import org.team401.snakeeyes.service.Service
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/*
 * snakeeyes - Created on 1/31/18
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 1/31/18
 */
abstract class Pipeline(val provider: MatProvider): Service {
    abstract fun process(mat: Mat)

    private var mat = Mat()

    private fun runProcess() {
        while (!Thread.interrupted()) {
            try {
                provider.await()
                mat = provider.get()
                process(mat)
                mat.release()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    private val thread = Thread(::runProcess)

    override fun start() {
        thread.start()
    }

    override fun stop() {
        thread.interrupt()
        thread.join()
    }
}