package org.team401.snakeeyes.view

import org.opencv.core.Mat
import org.opencv.core.Point

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

class PipView(val main: View, val sub: View, val pos: Position = Position.BOTTOM_RIGHT, val ratio: Double = .25): View {
    enum class Position(val top: Boolean, val bottom: Boolean, val left: Boolean, val right: Boolean) {
        TOP_LEFT      (true, false, true, false),
        TOP_CENTER    (true, false, false, false),
        TOP_RIGHT     (true, false, false, true),
        BOTTOM_LEFT   (false, true, true, false),
        BOTTOM_CENTER (false, true, false, false),
        BOTTOM_RIGHT  (false, true, false, true),
        LEFT_CENTER   (true, false, false, false),
        RIGHT_CENTER  (false, true, false, false),
        CENTER        (false, false, false, false)
    }

    override fun render(width: Int, height: Int, type: Int, properties: RenderProperties): Mat {
        val mainMat = main.render(width, height, type)

        var subWidth = (width * ratio).toInt()
        var subHeight = (height * ratio).toInt()
        val centerX = width / 2
        val centerY = height / 2

        val subMat = sub.render(subWidth, subHeight, type, RenderProperties(makeBorderOnResize = false))
        subWidth = subMat.cols()
        subHeight = subMat.rows()

        var rowStart = 0
        var rowEnd = 0
        var colStart = 0
        var colEnd = 0

        when {
            pos.left -> {
                colStart = 0 //Start at left of image
                colEnd = subWidth //End at left + width of sub
            }
            pos.right -> {
                colEnd = width //End at right of image
                colStart = width - subWidth //Start at right of image offset
            }
            else -> {
                colStart = centerX - (subWidth / 2)
                colEnd = centerX + (subWidth / 2)
            }
        }

        when {
            pos.top -> {
                rowStart = 0 //Start at top of image
                rowEnd = subHeight //End at top + height of sub
            }
            pos.bottom -> {
                rowEnd = height //End at bottom of image
                rowStart = height - subHeight //Start at bottom of image - height of sub
            }
            else -> {
                rowStart = centerY - (subHeight / 2)
                rowEnd = centerY + (subHeight / 2)
            }
        }

        val roi = mainMat.submat(rowStart, rowEnd, colStart, colEnd)
        subMat.copyTo(roi)
        return mainMat.clone()
    }
}