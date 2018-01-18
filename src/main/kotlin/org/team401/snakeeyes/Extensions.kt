package org.team401.snakeeyes

import org.opencv.core.Scalar

/*
 * snakeeyes - Created on 12/22/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 12/22/17
 */

fun List<Number>.toScalar(): Scalar {
    val array = DoubleArray(size)
    for (i in 0 until size) {
        array[i] = get(i).toDouble()
    }
    return Scalar(array)
}