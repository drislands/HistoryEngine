package com.islands.games.lifesim

/**
 * Enumeration representing the size of a thing.
 */
enum Scale {
    
    TINY(10),
    STANDARD(100),
    HUGE(1000)

    float value

    Scale(float value) {
        this.value = value
    }
}