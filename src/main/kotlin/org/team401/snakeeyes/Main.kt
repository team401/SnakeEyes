package org.team401.snakeeyes

import org.opencv.core.Core
import org.team401.snakeeyes.camera.Camera
import org.team401.snakeeyes.service.MjpegServer
import org.team401.snakeeyes.view.*

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
    val cam = Camera()
    cam.open("/dev/v4l/by-id/usb-CN045G287248768KB1ECA01_Integrated_Webcam_HD-video-index0")

    Cameras.add(cam)
    Cameras.start()

    val camView = CameraView(cam)
    val view = CrosshairView(camView, 5)
    view.x = 100
    view.y = 100

    val server = MjpegServer(8080, view, 640, 480)
    server.start()
}