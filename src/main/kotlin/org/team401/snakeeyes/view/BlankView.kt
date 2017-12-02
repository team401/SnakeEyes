package org.team401.snakeeyes.view

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size

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

class BlankView: View {
    override fun render(width: Int, height: Int, type: Int, properties: RenderProperties): Mat {
        return Mat.zeros(height, width, type)
    }
}