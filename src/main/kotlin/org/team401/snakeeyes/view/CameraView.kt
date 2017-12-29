package org.team401.snakeeyes.view

import org.opencv.core.Mat
import org.opencv.core.Size
import org.team401.snakeeyes.Tools
import org.team401.snakeeyes.camera.Camera

/*
 * snakeeyes - Created on 10/10/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 10/10/17
 */

class CameraView(private val camera: Camera): View {
    override fun render(width: Int, height: Int, type: Int, properties: RenderProperties): Mat {
        val mat = camera.get()
        if (mat.rows() == height && mat.cols() == width) {
            return mat
        } else {
            val destMat = Mat()
            Tools.resizeKeepAspect(mat, destMat, Size(width.toDouble(), height.toDouble()), properties.makeBorderOnResize)
            mat.release()
            return destMat
        }
    }
}