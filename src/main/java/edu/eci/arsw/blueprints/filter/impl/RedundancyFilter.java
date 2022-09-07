package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.FilterBlueprint;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

public class RedundancyFilter implements FilterBlueprint {

    @Override
    public Blueprint filter(Blueprint blueprint) {
        Blueprint blueprintFiltered = new Blueprint(blueprint.getAuthor(),blueprint.getName());
        for (int i= 0; i< blueprint.getPoints().size()-1; i+=2){
            int posX =blueprint.getPoints().get(i).getX();
            int posY =blueprint.getPoints().get(i).getY();
            int posX_1 =blueprint.getPoints().get(i+1).getX();
            int posY_1 =blueprint.getPoints().get(i+1).getY();
            if (posX==posX_1 || posY==posY_1){
            } else{
                blueprintFiltered.addPoint(new Point(blueprint.getPoints().get(i+1).getX(),blueprint.getPoints().get(i+1).getY()));
            }blueprintFiltered.addPoint(new Point(blueprint.getPoints().get(i).getX(),blueprint.getPoints().get(i).getY()));
        }
        return blueprintFiltered;
    }
}
