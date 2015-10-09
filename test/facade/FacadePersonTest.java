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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import testData.TestDataPerson;

/**
 *
 * @author bo
 */
public class FacadePersonTest {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2-test");
    Facade facade = new Facade(emf);
    
    public FacadePersonTest() {}
    
   
    
    @Before
    public void setUp() {EntityManager em = facade.getEntityManager();
    
    // can't workout how to delete columnes in the data base ?????????????
    
//        try {
//
//            em.getTransaction().begin();
//            // here should all the tables in the database be deleted
//            em.createQuery("DELETE FROM InfoEntity").executeUpdate();
//            em.getTransaction().commit();
//            
//        } finally {
//            em.close();
//        }
    } // end of Before
    

  
    @Test
    public void addPerson() {
        
        // Add a person to the data base
        Person newPersonWithId = (Person) facade.addPerson(TestDataPerson.getNewPerson());
        assertNotNull(newPersonWithId);
        
        Person person = (Person) facade.findInfoEntity(newPersonWithId.getId());
        assertTrue(TestDataPerson.compareProperties(person));
        
    } // End of method
    
    @Test
    public void getPerson() {
        
        // Add a person to the data base
        Person newPersonWithId = (Person) facade.addPerson(TestDataPerson.getNewPerson());
        assertNotNull(newPersonWithId);
        
        Person samePerson = (Person) facade.findInfoEntity(newPersonWithId.getId());
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

