package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.filter.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class main {

    public static void main(String[] args) throws BlueprintPersistenceException, BlueprintNotFoundException {
        ApplicationContext a= new ClassPathXmlApplicationContext("SpringSchemaContext.xml");
        BlueprintsServices b= a.getBean(BlueprintsServices.class);

        Point[] pts0=new Point[]{new Point(15,15),new Point(16,15),new Point(40,15),new Point(40,50)};
        Blueprint bp0=new Blueprint("Alguien", "what",pts0);
        b.addNewBlueprint(bp0);
        System.out.println(b.getBlueprint("Alguien","what").getPoints().size()==4);



    }
}
