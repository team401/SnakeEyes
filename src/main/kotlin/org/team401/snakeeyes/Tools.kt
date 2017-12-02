package org.team401.snakeeyes

import org.opencv.core.*
import org.opencv.imgproc.Imgproc

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

object Tools {
    @JvmStatic fun resizeKeepAspect(src: Mat, dest: Mat, size: Size, makeBorder: Boolean = true, borderColor: Scalar = Scalar(0.0, 0.0, 0.0)): Mat {
        if (!size.empty()) {
            var top = 0
            var down = 0
            var left = 0
            var right = 0

            val h1 = size.width * (src.rows() / src.cols().toDouble())
            val w2 = size.height * (src.cols() / src.rows().toDouble())

            if (h1 <= size.height) {
                top = ((size.height - h1) / 2).toInt()
                down = top
                Imgproc.resize(src, dest, Size(size.width, Math.round(h1).toDouble()))
            } else {
                left = ((size.width - w2) / 2).toInt()
                right = left
                Imgproc.resize(src, dest, Size(Math.round(w2).toDouble(), size.height))
            }

            if (makeBorder) {
                Core.copyMakeBorder(dest, dest, top, down, left, right, Core.BORDER_CONSTANT, borderColor)
                Imgproc.resize(dest, dest, size)
            }
            return dest
        } else {
            return Mat()
        }
    }
}