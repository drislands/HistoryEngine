package com.islands.games.lifesim.metaphysics

class AffinityManager {
    static ArrayList<Affinity> AFFINITIES = new ArrayList<>()

    static Affinity get(String a) {
        AFFINITIES.find {
            it.name == a
        }
    }
}
