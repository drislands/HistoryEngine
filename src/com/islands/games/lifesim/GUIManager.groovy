package com.islands.games.lifesim

import groovy.swing.SwingBuilder
import net.miginfocom.swing.MigLayout

import javax.swing.JFrame
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import java.awt.Color

class GUIManager {
    SwingBuilder builder = new SwingBuilder()
    GUI gui = new GUI()

    void init() {
        gui.with {
            builder.edt {
                frame(title: "History Engine", size: [400, 400], show: true, defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
                    panel(layout: new MigLayout("fill, wrap 1", "[grow]")) {
                        label(text: "Console", constraints: " north")
                        console = textArea(minimumSize: [300, 300],  background: Color.black, foreground: Color.green, constraints: "cell 0 0,span")
                    }
                }
            }
        }
    }

    static void main(args) {
        GUIManager gMan = new GUIManager()
        PrintManager.printOperation = { String msg, boolean newLine ->
            gMan.gui.console.append(msg + (newLine ? "\n" : ""))
            gMan.gui.console.caretPosition = gMan.gui.console.document.length
        }
        gMan.init()
        Simulation.main(args)
    }
}
