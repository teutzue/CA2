
package tester;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Address;
import entity.CityInfo;
import entity.Hobby;
import entity.Person;
import entity.Phone;

/**
 *
 * @author CosticaTeodor
 */
public class TesterJoji 
{
    public static void main(String[] args) {
        
    
    String jsonAsString ="{" +
"\"id\":1," +
"\"firstName\": \"Vanessa\"," +
"\"lastName\": \"Jennings\"," +
"\"email\": \"a@in.co.uk\"" +
"}";
 
 

    
    
     Gson gson = new Gson();
        Person p = new Person(); 
        JsonObject person = new JsonParser().parse(jsonAsString).getAsJsonObject();
        
        if(person.has("id"))
        //if(person.)
        {
             p.setId(person.get("id").getAsInt());  
        }
        p.setFirstName(person.get("firstName").getAsString());
        p.setLastName(person.get("lastName").getAsString());
        p.setEmail(person.get("email").getAsString());
        
        if(person.has("phones"))
        {
        JsonArray phonesArr = person.getAsJsonArray("phones");
        for (JsonElement pjo : phonesArr) 
        {
            Phone phone = new Phone();
            String ph = pjo.getAsJsonObject().get("number").getAsString();
            String desc = pjo.getAsJsonObject().get("description").getAsString();
            int phnumber = Integer.parseInt(ph);
            phone.setNumber(phnumber);
            phone.setDescription(desc);
            p.addPhone(phone);
            phone.setInfoEntity(p);
        }
        }
        
        Address address = null;
        if(person.has("street"))
        {
        address = new Address();
        String street = person.get("street").getAsString();
        address.setStreet(street);
        }
        if(person.has("additionalInfo"))
        {
        String additionalinfo = person.get("additionalInfo").getAsString();
        address.setAdditionalInfo(additionalinfo);
        }
        if(address!=null)
        {
        p.setAddress(address);
        address.addInfoEntity(p);
        }

        CityInfo cityinfo = null;
        
        if(person.has("zipcode"))
        {
        String zip = person.get("zipcode").getAsString();
        int zipnumber = Integer.parseInt(zip);
        cityinfo = new CityInfo();
        cityinfo.setZip(zipnumber);
        }
        
        if(person.has("city"))
        {
        String city = person.get("city").getAsString();
        cityinfo.setCity(city);
        }
        
         if(cityinfo!=null)
         {
        address.setCityInfo(cityinfo);
        cityinfo.addAddress(address);
         }

       if(person.has("hobbies"))
       {
        JsonArray hobbiesArr = person.getAsJsonArray("hobbies");
        for (JsonElement pjo : hobbiesArr) 
        {
            Hobby hob = new Hobby();
            String name = pjo.getAsJsonObject().get("name").getAsString();
            String desc = pjo.getAsJsonObject().get("description").getAsString();

            hob.setName(name);
            hob.setDescription(desc);
            p.addHobby(hob);
            hob.addPerson(p);

        }
       }
       
        System.out.println("The person data is "+p.getId() + " "+p.getFirstName() + " "+ p.getLastName());
       
     //   System.out.println(p.getId());
      //  return p;
    }
}
