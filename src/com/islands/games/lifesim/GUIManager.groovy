package com.islands.games.lifesim

import groovy.swing.SwingBuilder
import net.miginfocom.swing.MigLayout

import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JTabbedPane
import java.awt.Color
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener

class GUIManager {
    static boolean waiting_on_input = false
    static boolean input_ready = false
    static String input = ""

    SwingBuilder builder = new SwingBuilder()
    GUI gui = new GUI()

    void init() {
        gui.with {
            builder.edt {
                mainFrame = frame(title: "History Engine", size: [900, 900], show: true, defaultCloseOperation: JFrame.EXIT_ON_CLOSE,resizable: false) {
                    panel(layout: new MigLayout("fillx, wrap 1", "[grow]")) {
                        label(font:headerFont,text: "Console", constraints: " north,alignx center")
                        console = textArea(font:consoleFont,minimumSize: [300, 300],  background: Color.black, foreground: Color.green, constraints: "cell 0 0,span")
                        tabbedPane(id:"tabs",tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,constraints:"cell 1 0") {
                            panel(name:"Tab 1",layout:new MigLayout()) {
                                label(text:"wow how'd you get here",constraints:"cell 0 0")
                            }
                            panel(name:"Tab 2",layout:new MigLayout()) {
                                label(text:"sheeeeesh",constraints:"cell 0 0")
                            }
                        }
                        consoleEntry = textField(columns:80,constraints:"cell 0 1 2 1",actionPerformed: {
                            new Thread().start() {
                                String text = consoleEntry.text
                                consoleEntry.text = ""
                                if(!waiting_on_input) {
                                    if (!Simulation.exec(text)) {
                                        if (JOptionPane.showConfirmDialog(mainFrame, "They'll be dead, you know.", "Kill all those people?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
                                            System.exit(0)
                                    }
                                } else {
                                    input = text
                                    input_ready = true
                                    waiting_on_input = false
                                }
                            }
                        })
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
