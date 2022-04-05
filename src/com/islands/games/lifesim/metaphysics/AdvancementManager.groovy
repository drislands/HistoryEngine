package com.islands.games.lifesim.metaphysics

class AdvancementManager {
    static ArrayList<Tech> TECHS = new ArrayList<>()
    static ArrayList<Magic> MAGICS = new ArrayList<>()

    static Advancement get(String name) {
        ((TECHS + MAGICS) as ArrayList<Advancement>).find {
            it.name == name
        }
    }
}
