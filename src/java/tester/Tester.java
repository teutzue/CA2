
package tester;

import entity.Address;
//import entity.Facade;
import entity.Hobby;
import entity.InfoEntity;
import entity.Person;
import entity.Phone;
import facade.Facade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author CosticaTeodor
 */
public class Tester {
  
  public static void main(String[] args) {
    //Persistence.generateSchema("pu_production", null);
    //Persistence.generateSchema("CA2PU", null);
    Persistence.generateSchema("CA2-deployment", null);
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("CA2-deployment");
    Facade fa = new  Facade(emf);
  //  Person getPersonById = fa.getPerson(1);
//    Person returned = (Person) fa.find(3);
//    System.out.println("The person with id 3 is "+returned.getFirstName());
//    if(returned instanceof Person)
//    {
//        System.out.println("YEs");
//    }
    
    InfoEntity info = fa.findInfoEntity(3);
   // System.out.println("The person with id 3 is "+info.getFirstName());
    InfoEntity afterDelete = fa.deleteInfo(info);
    Person p = (Person) afterDelete;
      System.out.println("the persn with id 3 is "+p.getFirstName());
    
    
////    
////    Phone phone = new Phone();
////    phone.setNumber(45678943);
////    
////    Hobby hobby = new Hobby();
////    hobby.setName("Basketball");
////    hobby.setDescription("Totaly stupid");
//    
//    
//    Person p = new  Person();
//    p.setFirstName("Bo");
//    p.setLastName("Vistrup");
//    p.setEmail("hana.v@gmail.com");
////    p.addPhone(phone);
////    p.addHobby(hobby);
//    
//    Person newadded = (Person) fa.addInfo(p);
//      System.out.println("The person is "+newadded.getId());
//      
//      Address adres = new Address();
//      adres.addInfoEntity(p);
//      fa.addAddress(adres);
//    
  }
  
}
