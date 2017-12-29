package org.team401.snakeeyes.view

import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.team401.snakeeyes.Tools

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

class MatView(val mat: Mat): View {
    override fun render(width: Int, height: Int, type: Int, properties: RenderProperties): Mat {
        if (mat.rows() == height && mat.cols() == width) {
            return mat
        } else {
            val destMat = Mat()
            Tools.resizeKeepAspect(mat, destMat, Size(width.toDouble(), height.toDouble()), properties.makeBorderOnResize)
            return destMat
        }
    }
}

