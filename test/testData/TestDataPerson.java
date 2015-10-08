/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testData;

import entity.Person;

/**
 *
 * @author bo
 */
public class TestDataPerson {
    
    private static final String FIRST_NAME = "TEST-FIRST-NAME";
    private static final String LAST_NAME = "TEST-LAST-NAME";
    
    private TestDataPerson() {}
    
    public static Person getNewPerson() {
        
        return new Person(FIRST_NAME, LAST_NAME);
    }
    
    public static boolean compareProperties(Person p) {

        boolean isProperties = false;

        if (
                p.getFirstName().equals(FIRST_NAME)      &
                p.getLastName().equals(LAST_NAME))
        {

            isProperties = true;
        }

        return isProperties;
    } // End of method
    
    
} // End of Class
