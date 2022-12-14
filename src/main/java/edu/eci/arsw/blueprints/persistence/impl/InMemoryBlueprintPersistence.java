/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();


    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getAllBlueprints()  {
        Set<Blueprint> set = new HashSet<>();
        for (Tuple<String,String> tuple : blueprints.keySet()){
            Blueprint blueprint = getBlueprint(blueprints.get(tuple).getAuthor(), blueprints.get(tuple).getName());
            set.add(blueprint);
        }
        return set;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) {
        Set<Blueprint> set = new HashSet<>();
        for (Tuple<String, String> tuple: blueprints.keySet()){
            if (blueprints.get(tuple).getAuthor().equals(author)){
                Blueprint blueprint = getBlueprint(blueprints.get(tuple).getAuthor(), blueprints.get(tuple).getName());
                set.add(blueprint);
            }
        }
        return set;
    }
}
