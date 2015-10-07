/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facade.Facade;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import entity.InfoEntity;
import entity.Person;
import entity.Phone;
import facade.JSONConverter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author CosticaTeodor
 */
@Path("person")
public class GenericResource {

    @Context
    private UriInfo context;
    Facade cus = new Facade(Persistence.createEntityManagerFactory("CA2-deployment"));

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("complete")
    @Produces("application/json")
    public String getJson() {

        Gson gson = new Gson();
        List<Person> people = cus.getAllData();
        JsonArray jsonArray = new JsonArray();
        for (Person p : people) {
            JsonObject json = new JsonObject();
            json.addProperty("firstName", p.getFirstName());
            json.addProperty("lastName", p.getLastName());
            json.addProperty("email", p.getEmail());
            
            List<Phone> phones = p.getPhones();  
            JsonArray phoneArray = new JsonArray();
            for (Phone phone : phones) {
                JsonObject phoneJson = new JsonObject();
                phoneJson.addProperty("number", phone.getNumber());
                phoneJson.addProperty("description", phone.getDescription());
                phoneArray.add(phoneJson);
            }
            
            json.addProperty("phones", phoneArray.toString());
            json.addProperty("strret", p.getAddress().getStreet());
            json.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
            json.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
            json.addProperty("city", p.getAddress().getCityInfo().getCity());
            jsonArray.add(json);
        }        
        
        return gson.toJson(jsonArray);
//        Gson gson = new Gson();
//
//        List<Person> list = cus.getAllData();
//
//        JsonArray greatdude = new JsonArray();
//        JsonArray phones = new JsonArray();
//        for (Person p : list) {
//            ArrayList<Phone> listPhones = (ArrayList<Phone>) p.getPhones();
//
//            JsonObject newPerson = new JsonObject();
//            newPerson.addProperty("firstName", p.getFirstName());
//            newPerson.addProperty("lastName", p.getLastName());
//            newPerson.addProperty("email", p.getEmail());
//
//            
//          
//            for (Phone phone : listPhones) {
//                JsonObject phonej = new JsonObject();
//                phonej.addProperty("number", phone.getNumber());
//                phonej.addProperty("description", phone.getDescription());
//                phones.add(phonej);
//            }
//
//            newPerson.add("phones", phones);
//
//            greatdude.add(newPerson);
//            
//
//        }
////        String jsonList = gson.toJson(greatdude);
//        
//        
//        
////        return jsonList;
//        
//        return greatdude.toString();
    }

//          JsonObject jo = new JsonObject();
//          ArrayList<JsonObject> listj = new ArrayList<JsonObject>();
//          for (int i = 0; i < list.size(); i++)
//          {
//            Person p = list.get(i);
//             JsonObject newPerson = new JsonObject();
//             newPerson.addProperty("firstName",p.getFirstName());
//             newPerson.addProperty("lastName",p.getLastName());
//             newPerson.addProperty("email", p.getEmail());
//          ArrayList<Phone> listPhones = (ArrayList<Phone>) p.getPhones();
//        JsonArray phones = new JsonArray();
//        if(listPhones.size()>=1)
//        {
//              for (int j = 0; j <listPhones.size(); j++) 
//              {
//                  Phone phone = listPhones.get(i);
//                  JsonObject phonej = new JsonObject();
//                  phonej.addProperty("number",phone.getNumber());
//                  phonej.addProperty("description", phone.getDescription());
//                  phones.add(phonej);
//                  
//                  
//              }
//          
//             newPerson.add("phones", phones);
//        }
//             listj.add(newPerson);
//            
//          }
//          return listj.toString();
        /**
         * PUT method for updating or creating an instance of GenericResource
         *
         * @param content representation for the resource
         * @return an HTTP response with content of the updated or created
         * resource.
         */
//    @PUT
//    @Consumes("application/json")
//    public void putJson(String content) {
//    }
//    
//    @POST
//    @Consumes("application/json")
//    @Produces("application/json")
//    public String postPerson(String jsonAsString)
//    {
//        Person p = new Person();
//        JsonObject person = new JsonParser().parse(jsonAsString).getAsJsonObject();
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
//        p.setFirstName(person.get("firstName").getAsString());
////        
//        
//    }
    
}
