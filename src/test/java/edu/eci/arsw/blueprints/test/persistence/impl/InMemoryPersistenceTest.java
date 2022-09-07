/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.filter.impl.UndersamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);

        System.out.println(ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
        }
    }

    @Test
    public void redunancyFilterTest(){
        List<Point> test = new ArrayList<>();
        test.add(new Point(10, 0));test.add(new Point(10, 10));
        test.add(new Point(5, -5));test.add(new Point(7, 5));

        RedundancyFilter prueba = new RedundancyFilter();
        Point[] puntos = {new Point(10, 0), new Point(10, 0), new Point(10, 10),
                new Point(7, 5), new Point(5, -5), new Point(5, -5)};
        Blueprint bp = new Blueprint("test_redundancy", "test", puntos);
        Blueprint bp_prueba = prueba.filter(bp);
        assertEquals(bp_prueba.getPoints().size(),test.size());
    }

    @Test
    public void underSamplingFilterTest(){
        UndersamplingFilter prueba = new UndersamplingFilter();
        Point[] puntos = {new Point(10, 0), new Point(10, 0), new Point(10, 10),
                new Point(7, 5), new Point(5, -5), new Point(5, -5)};
        int size = puntos.length/2;
        Blueprint bp = new Blueprint("test_underSampling", "test", puntos);
        Blueprint bp_prueba = prueba.filter(bp);
        assertEquals(bp_prueba.getPoints().size(),size);
    }


    @Test
    public void  getBluePrintTest() {
        InMemoryBlueprintPersistence blueprintPersistence=new InMemoryBlueprintPersistence();
        Blueprint bpTest = null;
        Point[] puntos=new Point[]{
                new Point(40, 25),new Point(90, 87)
        };
        Blueprint bp=new Blueprint("test_save", "saved",puntos);
        try {
            blueprintPersistence.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
        }
        bpTest = blueprintPersistence.getBlueprint("test_save", "saved");
        assertEquals(bpTest,bp);
    }

    @Test
    public void getBlueprintsByAuthorTest(){
        InMemoryBlueprintPersistence blueprintPersistence=new InMemoryBlueprintPersistence();
        Blueprint bp1=new Blueprint("aa", "12",new Point[]{new Point(3, 7),new Point(10, 10)});
        Blueprint bp2=new Blueprint("bb", "13",new Point[]{new Point(7, 7),new Point(10, 10)});
        Blueprint bp3=new Blueprint("cc", "14",new Point[]{new Point(7, 7),new Point(10, 10)});
        Blueprint bp4=new Blueprint("dd", "15",new Point[]{new Point(12, 12),new Point(16, 16)});
        try {
            blueprintPersistence.saveBlueprint(bp1);
            blueprintPersistence.saveBlueprint(bp2);
            blueprintPersistence.saveBlueprint(bp3);
            blueprintPersistence.saveBlueprint(bp4);
        } catch (BlueprintPersistenceException e) {    }
        Set<Blueprint> prueba = new HashSet<>();
        prueba.add(bp1);
        assertEquals(prueba,blueprintPersistence.getBlueprintsByAuthor("aa"));

    }
}
