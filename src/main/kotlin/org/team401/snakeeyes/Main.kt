package org.team401.snakeeyes

import org.opencv.core.Core
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.videoio.VideoCapture
import org.team401.snakeeyes.camera.Camera
import org.team401.snakeeyes.server.MjpegServer
import org.team401.snakeeyes.view.CameraView
import org.team401.snakeeyes.view.CrosshairView
import org.team401.snakeeyes.view.MatView
import org.team401.snakeeyes.view.PipView

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

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    val cam = Camera(0)

    cam.open()

    val pip = CameraView(cam)

    val server = MjpegServer(pip)
    server.start("test", 8080)

}