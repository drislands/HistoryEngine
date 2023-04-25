package com.islands.games.lifesim

import groovy.swing.SwingBuilder
import net.miginfocom.swing.MigLayout

import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JTabbedPane
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.Toolkit

class GUIManager {
    /*
     These four values act as a state machine for the simulation. While not waiting on input, any new text the user
     provides act as a new individual command. While the command is running, any new reads will make use of these
     variables to indicate that more information is needed -- and then the text the user enters in will be taken by
     the running command instead of creating a new one to execute.
     */
    static boolean waiting_on_input = false
    static boolean input_ready = false
    static boolean new_command_allowed = true
    static String input = ""

    static boolean DEBUG = false

    SwingBuilder builder = new SwingBuilder()
    GUI gui = new GUI()

    void init() {
        gui.with {
            builder.edt {
                mainFrame = frame(title: "History Engine", size: [900, 900], show: true, defaultCloseOperation: JFrame.EXIT_ON_CLOSE,resizable: false) {
                    panel(layout: new MigLayout("ins 0,fillx ${DEBUG?",debug":""}", "[grow|]")) {
                        label(font:headerFont,text: "Console", constraints: " north,alignx center")
                        scrollPane(minimumSize: [425, 750],constraints:"cell 0 0,span") {
                            console = textArea(editable: false, lineWrap:true,wrapStyleWord: true, font: consoleFont,  background: Color.black, foreground: Color.green)
                        }
                        infoPane = tabbedPane(id:"tabs",tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,constraints:"cell 1 0,w 425px") {
                            panel(name:"Tribe 1",layout:new MigLayout()) {
                                label(text:"Tribe Name:",font:categoryFont,constraints:"cell 0 0,alignx right")
                                label(text:"Sample Tribe Name",font:dataFont,constraints:"cell 1 0")
                                label(text:"Member Count:",font:categoryFont,constraints:"cell 0 1,alignx right")
                                label(text:"100",font:dataFont,constraints:"cell 1 1")
                                label(text:"Coordinates:",font:categoryFont,constraints:"cell 0 2,alignx right")
                                label(text:"(025.22,-199.55)",font:dataFont,constraints:"cell 1 2")
                                label(text:"Nearby Threats:",font:categoryFont,constraints:"cell 0 3,alignx right")
                                label(text:"Volcano",font:dataFont,constraints:"cell 1 3")
                                label(text:"Nearby Resources:",font:categoryFont,constraints:"cell 0 4,alignx right")
                                label(text:"Fertile land, Running water",font:dataFont,constraints:"cell 1 4")
                            }
                            panel(name:"Tribe 2",layout:new MigLayout()) {
                                label(text:"Tribe Name:",font:categoryFont,constraints:"cell 0 0,alignx right")
                                label(text:"Other Tribe Name",font:dataFont,constraints:"cell 1 0")
                                label(text:"Member Count:",font:categoryFont,constraints:"cell 0 1,alignx right")
                                label(text:"169",font:dataFont,constraints:"cell 1 1")
                                label(text:"Coordinates:",font:categoryFont,constraints:"cell 0 2,alignx right")
                                label(text:"(803.72,459.90)",font:dataFont,constraints:"cell 1 2")
                                label(text:"Nearby Threats:",font:categoryFont,constraints:"cell 0 3,alignx right")
                                label(text:"Dinosaurs, Magic Leyline",font:dataFont,constraints:"cell 1 3")
                                label(text:"Nearby Resources:",font:categoryFont,constraints:"cell 0 4,alignx right")
                                label(text:"Gold, Magic Leyline",font:dataFont,constraints:"cell 1 4")
                            }
                        }
                        consoleEntry = textField(columns:80,constraints:"cell 0 1 2 1,aligny center, alignx center",
                                focusTraversalKeysEnabled: false,
                                actionPerformed: {
                            // Start a thread so the GUI doesn't get locked up while executing the given command.
                            new Thread().start() {
                                String text = consoleEntry.text
                                entryHistory << text
                                entryIndex = null
                                currentEntry = ""
                                consoleEntry.text = ""
                                if(!waiting_on_input) {
                                    if(new_command_allowed) {
                                        new_command_allowed = false
                                        if (!Simulation.exec(text)) {
                                            if (JOptionPane.showConfirmDialog(mainFrame,
                                                    "They'll be dead, you know.",
                                                    "Kill all those people?",
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
                                                System.exit(0)
                                        }
                                        new_command_allowed = true
                                    } else {
                                        JOptionPane.showMessageDialog(mainFrame,
                                                "Please wait for current command to finish.",
                                                "Command currently running",
                                                JOptionPane.WARNING_MESSAGE)
                                    }
                                } else {
                                    input = text
                                    input_ready = true
                                    waiting_on_input = false
                                }
                            }
                        })
                        consoleEntry.addKeyListener( new KeyListener() {
                            @Override
                            void keyTyped(KeyEvent e) {
                                // Do nothing.
                            }

                            @Override
                            void keyPressed(KeyEvent e) {
                                gui.with {
                                    if (e.keyCode !in [KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_TAB]) {
                                        entryIndex = null
                                        // We want to stop tracking history, and then continue as normal.
                                        //  This accounts for going up, picking an old entry, then modifying it.
                                        return
                                    }

                                    // Tab autocomplete functionality.
                                    if(e.keyCode == KeyEvent.VK_TAB) {
                                        String text = consoleEntry.text
                                        def command = Simulation.commands.find { cmd ->
                                            cmd.startsWith(text)
                                        }
                                        if(command) consoleEntry.text = command
                                    }

                                    if (e.keyCode == KeyEvent.VK_UP) {
                                        if (entryIndex == null && entryHistory.size() > 0) {
                                            currentEntry = consoleEntry.text
                                            entryIndex = entryHistory.size() - 1
                                        } else if (entryIndex > 0) {
                                            entryIndex--
                                        } else {
                                            Toolkit.defaultToolkit.beep()
                                            // TODO: flash? indicate to the user somehow that we are at the beginning
                                            // If the index is 0, we're at the oldest change and don't need to go up further.
                                        }
                                    } else if (e.keyCode == KeyEvent.VK_DOWN) {
                                        if (entryIndex != null && entryIndex < entryHistory.size() - 1) {
                                            entryIndex++
                                        } else {
                                            if(entryIndex == null) {
                                                if(consoleEntry.text != "") {
                                                    entryHistory << consoleEntry.text
                                                }
                                                consoleEntry.text = ""
                                            } else if(entryIndex == entryHistory.size() - 1) {
                                                consoleEntry.text = currentEntry
                                                entryIndex = null
                                            }
                                            // If the index is one less than the size, we're at the newest change and don't need to go down further.
                                        }
                                    }

                                    if(entryIndex != null)
                                        consoleEntry.text = entryHistory[entryIndex]
                                }
                            }

                            @Override
                            void keyReleased(KeyEvent e) {
                                // Do nothing.
                            }
                        })
                    }
                }
            }
        }
    }

    static void main(args) {
        GUIManager gMan = new GUIManager()
        Simulation.gMan = gMan
        PrintManager.printOperation = { String msg, boolean newLine ->
            gMan.gui.console.append(msg + (newLine ? "\n" : ""))
            gMan.gui.console.caretPosition = gMan.gui.console.document.length
        }
        gMan.init()
        gMan.gui.consoleEntry.requestFocus()
        Simulation.main(args)
    }
}
