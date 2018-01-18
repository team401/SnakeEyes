package org.team401.snakeeyes

import org.opencv.core.Mat
import java.util.concurrent.atomic.AtomicReference

/*
 * snakeeyes - Created on 12/28/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 12/28/17
 */

abstract class MatProvider {
    private val currentMat = AtomicReference<Mat>(Mat())

    protected fun update(mat: Mat) {
        currentMat.set(mat)
    }

    fun get() = currentMat.get().clone()
    fun get(mat: Mat) = currentMat.get().copyTo(mat)
}