package org.team401.snakeeyes.server

import org.eclipse.jetty.server.Request
import org.team401.snakeeyes.Service
import org.team401.snakeeyes.view.View
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.MatOfFloat
import org.opencv.core.MatOfInt
import org.opencv.imgcodecs.Imgcodecs
import org.team401.snakeeyes.LockingDelegate
import org.team401.snakeeyes.view.CameraView
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

class MjpegServer(val view: View, val width: Int = 640, val height: Int = 480, framerate: Int = 30, quality: Int = 30, val timeout: Int = 2000): Service {
    var quality: Int by LockingDelegate(quality)

    private companion object {
        val TERM = "\r\n\r\n".toByteArray()
    }

    private val msPerFrame = (1000/framerate).toLong()

    private val server = ServerSocket()
    private var serverThread: Thread? = null
    private var future: ScheduledFuture<*>? = null
    private val clients = Vector<Socket>()
    private val executor = Executors.newSingleThreadScheduledExecutor()

    private inner class ConnHandler: Runnable {
        override fun run() {
            while (!Thread.interrupted()) {
                val client = server.accept()
                client.soTimeout = timeout
                client.getOutputStream().write((
                                "HTTP/1.0 200 OK\r\n" +
                                "Server: YourServerName\r\n" +
                                "Connection: close\r\n" +
                                "Max-Age: 0\r\n" +
                                "Expires: 0\r\n" +
                                "Cache-Control: no-cache, private\r\n" +
                                "Pragma: no-cache\r\n" +
                                "Content-Type: multipart/x-mixed-replace; " +
                                "boundary=--BoundaryString\r\n\r\n"
                        ).toByteArray())
                clients.add(client)
            }
        }
    }

    private inner class StreamHandler: Runnable {
        val baos = ByteArrayOutputStream()
        val mob = MatOfByte()
        var mat = Mat()

        val options = MatOfInt()
        val toRemove = arrayListOf<Socket>()
        var first = true

        override fun run() {
            if (clients.isNotEmpty()) {
                options.fromArray(Imgcodecs.CV_IMWRITE_JPEG_QUALITY, quality)
                mat = view.render(width, height)
                Imgcodecs.imencode(".jpg", mat, mob, options)
                mat.release()
                baos.write(mob.toArray())
                mob.release()
                clients.forEach {
                    try {
                        val os = it.getOutputStream()
                        os.write((
                                "--BoundaryString\r\n" +
                                        "Content-type: image/jpeg\r\n" +
                                        "Content-Length: " +
                                        baos.size() +
                                        "\r\n\r\n"
                                ).toByteArray())
                        baos.writeTo(os)
                        os.write(TERM)
                        os.flush()
                    } catch (e: Exception) { //Client disconnected
                        toRemove.add(it)
                    }
                }
                clients.removeAll(toRemove)
                toRemove.clear()
                baos.reset()
            }
        }
    }

    override fun start() {
        start(1800)
    }

    fun start(port: Int) {
        server.setPerformancePreferences(0, 1, 2)
        server.bind(InetSocketAddress(port))
        serverThread = Thread(ConnHandler())
        serverThread?.start()
        future = executor.scheduleAtFixedRate(StreamHandler(), 0L, msPerFrame, TimeUnit.MILLISECONDS)
    }

    override fun stop() {
        future?.cancel(false)
        serverThread?.interrupt()
        serverThread?.join()
    }
}