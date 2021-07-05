package com.islands.games.lifesim.life

/**
 * Trait an {@link Organism} subclass can implement, indicating it reproduces sexually via standard M/F biology.
 */
trait Sexual implements Serializable {
    // Male parent of the Organism.
    Sexual father
    // Female parent of the Organism.
    Sexual mother
    // List of all children of the Organism.
    final ArrayList<Sexual> children = new ArrayList<>()

    /**
     * Obtains all full siblings of the object -- that is, objects that are {@link #children} of both this object's
     * father and mother.
     * @return List of this object's full siblings.
     */
    ArrayList<Sexual> getFullSiblings() {
        ArrayList<Sexual> items = new ArrayList<>()
        for(child in father.children)
            if(child in mother.children && child != this)
                items.add(child)

        return items
    }

    /**
     * Obtains all siblings of the object -- that is, objects that are {@link #children} of eithger this object's
     * father or mother.
     * @return List of this object's siblings.
     */
    ArrayList<Sexual> getSiblings() {
        ArrayList<Sexual> items = new ArrayList<>()
        items.addAll(father.children)
        for(child in mother.children)
            if(child !in items && child != this)
                items.add(child)

        return items
    }
}