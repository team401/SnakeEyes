package org.team401.snakeeyes.camera

import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
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
    private val current = Mat()

    internal fun grab() = cap.grab()
    internal fun retrieve() {
        cap.retrieve(current)
        update(current)
    }

    fun open(path: String) {
        cap.open(path)
        cap.read(Mat())
    }
}