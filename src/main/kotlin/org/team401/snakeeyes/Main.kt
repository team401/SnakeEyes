package org.team401.snakeeyes

import org.opencv.core.Core
import org.team401.snakeeyes.camera.Camera
import org.team401.snakeeyes.server.MjpegServer
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
    System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    val cam = Camera()
    cam.open("/dev/video0")

    Cameras.add(cam)
    Cameras.start()

    val camView = CameraView(cam)
    val view = CrosshairView(camView, 5)
    view.x = 100
    view.y = 100

    val server = MjpegServer(view, 640, 480)
    server.start(8080)

    Thread.sleep(10000)

    for (i in 0 until 640) {
        if (i < 480) {
            view.y = i
        }
        view.x = i
        Thread.sleep(10)
    }
}