package org.team401.snakeeyes.service

/*
 * snakeeyes - Created on 1/11/18
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 1/11/18
 */

object Services {
    private val services = arrayListOf<Service>()

    fun add(service: Service) {
        services.add(service)
    }

    fun start() {
        services.forEach {
            it.start()
        }
    }

    fun stop() {
        services.forEach {
            it.stop()
        }
    }
}