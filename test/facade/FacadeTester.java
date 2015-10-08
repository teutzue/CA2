/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.InfoEntity;
import entity.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import testData.TestDataPerson;

/**
 *
 * @author bo
 */
public class FacadeTester {
    
    //Facade facade = new Facade(Persistence.createEntityManagerFactory("CA2-deployment"));
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2-deployment");
    Facade facade = new Facade(emf);
    
    public FacadeTester() {
        
    }
    
    
    
    @Before
    public void setUp() {
        
        EntityManager em = facade.getEntityManager();
        try {

            em.getTransaction().begin();
            // here should all the tables in the database be deleted
            em.createQuery("DELETE FROM InfoEntity").executeUpdate();
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }
        
        
    } // End of setup()
    
  
    @Test
    public void addPerson() {
        
        // Add a person to the data base
        Person newPersonWithId = (Person) facade.addPerson(TestDataPerson.getNewPerson());
        assertNotNull(newPersonWithId);
        
        Person person = (Person) facade.find(newPersonWithId.getId());
        assertTrue(TestDataPerson.compareProperties(person));
        
    } // End of method
    
    @Test
    public void getPerson() {
        
        // Add a person to the data base
        Person newPersonWithId = (Person) facade.addPerson(TestDataPerson.getNewPerson());
        assertNotNull(newPersonWithId);
        
        Person samePerson = (Person) facade.find(newPersonWithId.getId());
        assertTrue(TestDataPerson.compareProperties(samePerson));
        
    } // End of method
    
    
    @Test
    public void deletePerson() {
        
        // Add a person to the data base
        InfoEntity newPersonWithId = facade.addPerson(TestDataPerson.getNewPerson());
        assertNotNull(newPersonWithId);
        
        InfoEntity deletedPerson = (Person) facade.deleteInfo(newPersonWithId);
        assertEquals(facade.getAllData().size(), 0);
    } // End of method
            // Person editPerson(Person entity) {
    
    @Test
    public void editPerson() {
        
         // Add a person to the data base
        InfoEntity newPersonWithId = (Person) facade.addPerson(TestDataPerson.getNewPerson());
        assertNotNull(newPersonWithId);
        
//        newPersonWithId.s
//        
//        Person EditPerson = facade.editPerson(newPersonWithId);
//        assertEquals(EditPerson.getfName(), newPersonWithId.getfName());
        
    } // End of method
           
    
} // End of class
