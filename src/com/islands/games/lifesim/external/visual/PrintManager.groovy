package com.islands.games.lifesim.external.visual

class PrintManager {
    // the default print operation. Can be swapped for other code to allow for other printing.
    static Closure printOperation = { String msg, boolean newLine->
        if(newLine) {
            println msg
        } else {
            print msg
        }
    }

    static void managedPrint(String msg) {
        printOperation(msg,false)
    }

    static void managedPrintln(String msg) {
        printOperation(msg,true)
    }
}
