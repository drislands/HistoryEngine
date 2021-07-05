package com.islands.games.lifesim.exceptions

/**
 * Exception class for when a program quit is initiated by the user.
 */
class UserQuittingException extends Exception {
    UserQuittingException(String msg) {
        super(msg)
    }
}
