package org.team401.snakeeyes.config.`object`

import java.util.concurrent.atomic.AtomicReference

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
class ConfigItem<T>(value: T): AtomicReference<T>(value), ConfigObject<T>