package com.islands.games.lifesim.external.visual

import com.islands.games.lifesim.external.Simulation

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