package org.team401.snakeeyes.camera

import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio
import org.team401.snakeeyes.MatProvider

/*
 * snakeeyes - Created on 10/17/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 10/17/17
 */
class Camera: MatProvider() {
    private val cap = VideoCapture()
    private val v4l = org.jv4l2.Camera()
    private val current = Mat()

    var framerate = 0.0; private set

    internal fun grab() = cap.grab()
    internal fun retrieve() {
        cap.retrieve(current)
        update(current)
    }

    fun open(path: String) {
        v4l.open(path)
        cap.open(path)
        framerate = cap.get(Videoio.CAP_PROP_FPS)
        val mat = Mat()
        cap.read(mat)
        mat.release()
    }

    fun open(id: Int) {
        v4l.open(id)
        cap.open(id)
        val mat = Mat()
        cap.read(mat)
        mat.release()
    }

    fun setProperty(property: Int, value: Int) {
        v4l.setProperty(property, value)
    }

    fun close() {
        cap.release()
        v4l.close()
    }
}