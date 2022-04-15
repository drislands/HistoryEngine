package com.islands.games.lifesim.exceptions

/**
 * Exception class for problems arising from configuration steps.
 */
class ConfigurationException extends Exception {
    ConfigurationException(String msg) {
        super(msg)
    }

    ConfigurationException(String msg,Throwable cause) {
        super(msg,cause)
    }
}
