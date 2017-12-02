package org.team401.snakeeyes.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.MatOfInt
import org.opencv.imgcodecs.Imgcodecs
import org.team401.snakeeyes.view.View
import java.net.InetSocketAddress
import java.util.concurrent.Executors

/*
 * snakeeyes - Created on 10/13/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 10/13/17
 */
class MjpegServer(val view: View, val width: Int = 640, val height: Int = 480, val framerate: Int = 30, var quality: Int = 30): Server {
    private val msPerFrame = (1000/framerate).toLong()

    private inner class MjpegServerHandler: HttpHandler {

        override fun handle(t: HttpExchange) {
            t.responseHeaders.set("Content-Type", "multipart/x-mixed-replace; boundary=--BoundaryString")
            t.sendResponseHeaders(200, 0)


            Thread {
                val body = t.responseBody
                var mat: Mat
                var mob: MatOfByte
                var bytes: ByteArray

                val options = MatOfInt(Imgcodecs.CV_IMWRITE_JPEG_QUALITY, quality)

                var time: Long

                while (true) {
                    try {
                        time = System.currentTimeMillis()
                        mat = view.render(640, 480)
                        mob = MatOfByte()
                        Imgcodecs.imencode(".jpg", mat, mob, options)
                        bytes = mob.toArray()
                        body.write(("--BoundaryString\r\n" +
                                "Content-type: image/jpeg\r\n" +
                                "Content-Length: " +

                                bytes.size +
                                "\r\n\r\n").toByteArray())
                        body.write(bytes)
                        body.write("\r\n\r\n".toByteArray())
                        body.flush()
                        Thread.sleep(Math.max(0, msPerFrame - (System.currentTimeMillis() - time)))
                    } catch (e: Exception) {
                        break
                    }
                }
            }.start()

        }
    }

    lateinit var server: HttpServer

    override fun start(address: String, port: Int) {
        server = HttpServer.create(InetSocketAddress(port), 0)
        server.createContext("/$address", MjpegServerHandler())
        server.start()
    }

    override fun stop() {
        server.stop(0)
    }
}