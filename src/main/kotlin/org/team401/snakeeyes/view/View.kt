package org.team401.snakeeyes.view

import org.opencv.core.CvType
import org.opencv.core.Mat

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
interface View {
    fun render(width: Int, height: Int, type: Int = CvType.CV_8UC3, properties: RenderProperties = RenderProperties()): Mat
}