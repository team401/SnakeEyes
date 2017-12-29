package org.team401.snakeeyes.view

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs

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

class GridView(val rows: Int, val cols: Int): View {
    val subViews = Array(rows, {Array<View>(cols, {BlankView()})})

    @Synchronized fun putView(v: View, row: Int, col: Int) {
        subViews[row][col] = v
    }

    @Synchronized override fun render(width: Int, height: Int, type: Int, properties: RenderProperties): Mat {
        val widthPerView = width / cols
        val heightPerView = height / rows
        val gridMat = Mat(height, width, type)

        var rowCurs = 0
        var colCurs = 0
        var roi: Mat
        var view: Mat

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                roi = gridMat.submat(rowCurs, rowCurs + heightPerView, colCurs, colCurs + widthPerView)
                view = subViews[row][col].render(widthPerView, heightPerView, type)
                view.copyTo(roi)
                view.release()
                roi.release()
                colCurs += widthPerView
            }
            rowCurs += heightPerView
            colCurs = 0
        }
        return gridMat
    }
}