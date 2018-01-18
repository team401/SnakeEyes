package org.team401.snakeeyes.view

import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.team401.snakeeyes.Colors

/*
 * snakeeyes - Created on 10/13/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 10/13/17
 */

class CrosshairView(val bgView: View, val thickness: Int = 1, val color: Scalar = Colors.GREEN): View {
    var x = 0
    @Synchronized get
    @Synchronized set(value) { field = if (value > 0) value else 0 }

    var y = 0
    @Synchronized get
    @Synchronized set(value) { field = if (value > 0) value else 0 }

    var visible = true
    @Synchronized get
    @Synchronized set


    override fun render(width: Int, height: Int, type: Int, properties: RenderProperties): Mat {
        if (visible) {
            val rendered = bgView.render(width, height, type, properties)
            val vTop = Point(x.toDouble(), 0.0)
            val vBottom = Point(x.toDouble(), height.toDouble())
            val hLeft = Point(0.0, y.toDouble())
            val hRight = Point(width.toDouble(), y.toDouble())
            Imgproc.line(rendered, vTop, vBottom, color, thickness)
            Imgproc.line(rendered, hLeft, hRight, color, thickness)
            return rendered
        } else {
            return bgView.render(width, height, type)
        }
    }
}