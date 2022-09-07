package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.FilterBlueprint;
import edu.eci.arsw.blueprints.model.Blueprint;

public class UndersamplingFilter implements FilterBlueprint {

    @Override
    public Blueprint filter(Blueprint blueprint) {
        Blueprint blueprintFiltered = new Blueprint(blueprint.getAuthor(),blueprint.getName());
        for (int i= 0;i<blueprint.getPoints().size();i++){
            if (i%2==0) {
                blueprintFiltered.addPoint(blueprint.getPoints().get(i));
            }
        }
        return blueprintFiltered;
    }
}
