package org.team401.snakeeyes.server

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
interface Server {
    fun start(address: String, port: Int)
    fun stop()
}