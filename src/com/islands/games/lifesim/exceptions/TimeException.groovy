package com.islands.games.lifesim.exceptions

/**
 * Exception class for issues that arise when calculating {@link com.islands.games.lifesim.Time} object interactions.
 */
class TimeException extends Exception {
    TimeException(String msg) {
        super(msg)
    }

    TimeException(String msg,Throwable cause) {
        super(msg,cause)
    }
}
