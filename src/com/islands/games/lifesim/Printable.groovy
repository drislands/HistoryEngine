package com.islands.games.lifesim

trait Printable {
    static void print(String msg) {
        PrintManager.managedPrint(msg)
    }

    static void println(String msg) {
        PrintManager.managedPrintln(msg)
    }

    static void DBG(msg) {
        if(Simulation.DEBUG)
            println "DEBUG: $msg"
    }
}