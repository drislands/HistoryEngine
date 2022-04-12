package com.islands.games.lifesim

import com.islands.games.lifesim.exceptions.ConfigurationException

import java.text.DecimalFormat

class Location {
    static DecimalFormat DF = new DecimalFormat("#000.00")

    boolean global = false

    double X
    double Y

    /**
     * Create a Location with the given coordinates.
     */
    Location(X,Y) {
        this.X = X
        this.Y = Y
    }

    String toString() {
        "($formattedX,$formattedY)"
    }

    String getFormattedX() {
        DF.format(X)
    }

    String getFormattedY() {
        DF.format(Y)
    }

    /**
     * Create a global Location.
     * @param global Must be true to call this.
     * @throws ConfigurationException Thrown if false is passed to this constructor.
     */
    Location(global) throws ConfigurationException {
        if(!global)
            throw new ConfigurationException("Tried to create Location with no data points.")
        else
            this.global = global
    }

    /**
     * Tests if the given Location is in range of this.
     * @param l2 Second Location to test.
     * @param distance Distance to test between Location objects.
     * @return True if they are in range (or if at least one is global). False otherwise.
     */
    boolean inRange(Location l2,double distance) {
        return global || l2.global || inRange(X,Y,l2.X,l2.Y,distance)
    }

    /**
     * This should not be called outside of the {@link #inRange(Location,double)}.
     */
    static boolean inRange(x1,y1,x2,y2,distance) {
        double ac = Math.abs(y2-y1)
        double cb = Math.abs(x2-x1)

        return Math.hypot(ac,cb) < distance
    }
}
