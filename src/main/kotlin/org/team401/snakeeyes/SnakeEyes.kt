package org.team401.snakeeyes

import org.opencv.core.Core

/*
 * snakeeyes - Created on 1/3/18
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 1/3/18
 */
object SnakeEyes {
    @JvmStatic fun startup() {
        //Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    }
}