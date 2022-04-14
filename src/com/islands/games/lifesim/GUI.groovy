package com.islands.games.lifesim

import javax.swing.JFrame
import javax.swing.JTextArea
import javax.swing.JTextField
import java.awt.Font

class GUI {
    Font headerFont = new Font("Impact",Font.BOLD,35)
    Font consoleFont = new Font("Courier New",Font.PLAIN,12)

    JFrame mainFrame
    JTextArea console
    JTextField consoleEntry
}
