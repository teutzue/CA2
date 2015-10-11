/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import deploy.DeploymentConfiguration;
import entity.Company;
import entity.InfoEntity;
import entity.Person;
import entity.Phone;
import exceptions.DataNotFoundException;
import facade.Facade;
import facade.JSONConverter;
import java.util.List;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Yoana
 */
@Path("person")
public class PersonResource {

    @Context
    private UriInfo context;
   Facade cus = new Facade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));
    /**
     * Creates a new instance of PersonResource
     */
    public PersonResource() {
    }

    /**
     * Retrieves representation of an instance of rest.PersonResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("complete/{id}")
    @Produces("application/json")
    public String getJson(@PathParam("id")  Integer id)
    {
        
        Gson gson = new Gson();
        Person p =  (Person) cus.findInfoEntity(id);
//         //return " "+p.getFirstName();
         String stringPerson = JSONConverter.getJSONFromPerson(p);
         return stringPerson;
    }
        
    @GET
    @Path("complete")
    @Produces("application/json")
    public String getJson() throws DataNotFoundException 
    {
        Gson gson = new Gson();
        List<Person> people = cus.getAllData();
       String stringPeople =  JSONConverter.getJSONFromPersons(people);
       return stringPeople;
    }
    
     
    @GET
    @Path("contactinfo/{id}")
    @Produces("application/json")
    public String getJson2(@PathParam("id") Integer id) throws DataNotFoundException 
    {
  
          Gson gson = new Gson();
          JsonObject json = new JsonObject();
          Person p = (Person) cus.findInfoEntity(id);
          
          if(p==null)
          {
              throw new DataNotFoundException("Sorry no person with that id ");
          }
          
          if(p.getAddress()!=null )
          {
          json.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
          json.addProperty("street", p.getAddress().getStreet());
          
          if(p.getAddress().getCityInfo()!=null)
          {
          json.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
          json.addProperty("city", p.getAddress().getCityInfo().getCity());
          }
          }
         
       return gson.toJson(json);
   
    }
    @GET
    @Path("contactinfo")
    @Produces("application/json")
    public String getContactInfoForAllPersons()  
    {
        Gson gson = new Gson();
        List<Person> people = cus.getAllData();
        
          JsonArray jsonArray = new JsonArray();
        for (Person p : people) 
        {
           JsonObject json = new JsonObject();
          if(p.getAddress()!=null )
          {
          json.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
          json.addProperty("street", p.getAddress().getStreet());
          
          if(p.getAddress().getCityInfo()!=null)
          {
          json.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
          json.addProperty("city", p.getAddress().getCityInfo().getCity());
          }
          }
          jsonArray.add(json);
        }
        
        return gson.toJson(jsonArray);
    }

    /**
     * PUT method for updating or creating an instance of PersonResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String postPerson(String jsonAsString) 
    {
        Gson gson = new Gson();  
        Person p = JSONConverter.getPersonFromJson(jsonAsString);
      //  return p.toString();
       Person returnPerson = (Person) cus.addPerson(p);
        
        String returnedStringPerson = JSONConverter.getJSONFromPerson(returnPerson);
   
       return   returnedStringPerson;
        
    }
    
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String putJson(String content) throws DataNotFoundException
    {
        
        Gson gson = new Gson();  
        Person p = JSONConverter.getPersonFromJson(content);
        Person returnPerson = (Person) cus.editPerson(p);
        String returnedStringPerson = JSONConverter.getJSONFromPerson(returnPerson);
   
     return   returnedStringPerson;
        
    }
    
    
    
    @DELETE
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces("application/json")
    public String delete(@PathParam("id") Integer id) 
    {
        Gson gson = new Gson();

       InfoEntity info = cus.findInfoEntity(id);
        //*******TEO for the method he created
       List<Phone> phoness = cus.getPhonesByIdPerson(id);
        for (int i = 0; i < phoness.size(); i++)
        {
            Phone ph = cus.deletePhone(phoness.get(i));
        }
       
       
       
        InfoEntity inforeturned = cus.deleteInfo(info);
 
        if (inforeturned instanceof Person)
        {
            Person returnedOne = (Person) inforeturned;
            
            String returnPerson = JSONConverter.getJSONFromPerson(returnedOne);
            
            
//            JsonObject json = new JsonObject();
//            json.addProperty("id", returnedOne.getId());
//            json.addProperty("firstName", returnedOne.getFirstName());
//            json.addProperty("lastName", returnedOne.getLastName());
//            json.addProperty("email", returnedOne.getEmail());
//
//            List<Phone> phones = returnedOne.getPhones();
//            JsonArray phoneArray = new JsonArray();
//            for (Phone phone : phones) {
//                JsonObject phoneJson = new JsonObject();
//                phoneJson.addProperty("number", phone.getNumber());
//                phoneJson.addProperty("description", phone.getDescription());
//                phoneArray.add(phoneJson);
//            }
//
//            json.add("phones", phoneArray);
//            json.addProperty("street", returnedOne.getAddress().getStreet());
//            json.addProperty("additionalInfo", returnedOne.getAddress().getAdditionalInfo());
//            json.addProperty("zipcode", returnedOne.getAddress().getCityInfo().getZipCode());
//            json.addProperty("city", returnedOne.getAddress().getCityInfo().getCity());
           // jsonArray.add(json);
           // return gson.toJson(json);
            return returnPerson;
        } else 
        {
            Company returnedOne = (Company) inforeturned;

            JsonObject json = new JsonObject();
            json.addProperty("firstName", returnedOne.getId());
            json.addProperty("firstName", returnedOne.getName());
            json.addProperty("lastName", returnedOne.getDescription());
            json.addProperty("email", returnedOne.getEmail());
            json.addProperty("cvr", returnedOne.getCvr());
            json.addProperty("NumbeOfEmployees", returnedOne.getNumEmployees());
            json.addProperty("marketValue", returnedOne.getMarketValue());

            List<Phone> phones = returnedOne.getPhones();
            JsonArray phoneArray = new JsonArray();
            for (Phone phone : phones) {
                JsonObject phoneJson = new JsonObject();
                phoneJson.addProperty("number", phone.getNumber());
                phoneJson.addProperty("description", phone.getDescription());
                phoneArray.add(phoneJson);
            }

            json.add("phones", phoneArray);
            json.addProperty("street", returnedOne.getAddress().getStreet());
            json.addProperty("additionalInfo", returnedOne.getAddress().getAdditionalInfo());
            json.addProperty("zipcode", returnedOne.getAddress().getCityInfo().getZipCode());
            json.addProperty("city", returnedOne.getAddress().getCityInfo().getCity());
            return gson.toJson(json);
        }

}
}
