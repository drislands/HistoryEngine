package com.islands.games.lifesim

import javax.swing.JFrame
import javax.swing.JTabbedPane
import javax.swing.JTextArea
import javax.swing.JTextField
import java.awt.Font

class GUI {
    ////////
    // Swing components.
    Font headerFont = new Font("Impact",Font.BOLD,35)
    Font consoleFont = new Font("Consolas",Font.PLAIN,12)

    Font categoryFont = new Font("Ariel",Font.BOLD,16)
    Font dataFont = new Font("Courier New",Font.PLAIN,14)

    JFrame mainFrame
    JTextArea console
    JTextField consoleEntry
    JTabbedPane infoPane

    ////////

    ArrayList<String> entryHistory = new ArrayList<>()
    Integer entryIndex = null
    String currentEntry = ""

    ////////
}
