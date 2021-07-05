package com.islands.games.lifesim.life

import com.islands.games.lifesim.Scale
import com.islands.games.lifesim.Time

/**
 * Class representing any kind of thing that can live and die.
 */
abstract class Organism implements Mortal,Serializable {
    // General Scale of the thing's size.
    Scale scale
    // Time of the thing's birth.
    final Time birth
    // Time of the thing's death, if it has died.
    Time death = null

    /**
     * Create an Organism, optionally setting the scale. Defaults to {@link Scale#STANDARD}.
     * @param birth {@link Time} of the thing's birth.
     * @param scale General {@link Scale} of the thing's size.
     */
    Organism(Time birth,Scale scale=Scale.STANDARD) {
        this.birth = birth
        this.scale = scale
    }
}
