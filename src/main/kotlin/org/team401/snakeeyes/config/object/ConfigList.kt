package org.team401.snakeeyes.config.`object`

import java.util.*

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

class ConfigList<T>(value: List<T>): Vector<T>(value), ConfigObject<T>