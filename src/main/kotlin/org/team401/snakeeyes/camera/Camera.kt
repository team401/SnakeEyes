package org.team401.snakeeyes.camera

import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture

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
class Camera(val id: Int) {
    private val cap = VideoCapture()

    @Synchronized fun open() {
        cap.open(id)
    }

    @Synchronized fun read(): Mat {
        val mat = Mat()
        if (cap.isOpened) {
            cap.read(mat)
        }
        return mat
    }
}