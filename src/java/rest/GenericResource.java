/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facade.Facade;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Address;
import entity.CityInfo;
import entity.Company;
import entity.Hobby;
import entity.InfoEntity;
import entity.Person;
import entity.Phone;
import java.util.List;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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

            json.add("phones", phoneArray);
            json.addProperty("strret", p.getAddress().getStreet());
            json.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
            json.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
            json.addProperty("city", p.getAddress().getCityInfo().getCity());
            List<Hobby> hobbies = p.getHobbys();
            JsonArray hobArray = new JsonArray();
            for (Hobby hob : hobbies) {
                JsonObject hobJson = new JsonObject();
                hobJson.addProperty("name", hob.getName());
                hobJson.addProperty("description", hob.getDescription());
                hobArray.add(hobJson);
            }
            json.add("hobbies", hobArray);
            jsonArray.add(json);
        }

        return gson.toJson(jsonArray);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String postPerson(String jsonAsString) {
        Gson gson = new Gson();

        Person p = new Person();

        JsonObject person = new JsonParser().parse(jsonAsString).getAsJsonObject();

        p.setFirstName(person.get("firstName").getAsString());
        p.setLastName(person.get("lastName").getAsString());
        p.setEmail(person.get("email").getAsString());

        JsonArray phonesArr = person.getAsJsonArray("phones");
        for (JsonElement pjo : phonesArr) {
            Phone phone = new Phone();
            String ph = pjo.getAsJsonObject().get("number").getAsString();
            String desc = pjo.getAsJsonObject().get("description").getAsString();
            int phnumber = Integer.parseInt(ph);
            phone.setNumber(phnumber);
            phone.setDescription(desc);
            p.addPhone(phone);
            phone.setInfoEntity(p);
        }

        Address address = new Address();
        String strret = person.get("strret").getAsString();
        String additionalinfo = person.get("additionalInfo").getAsString();
        address.setStreet(strret);
        address.setAdditionalInfo(additionalinfo);
        p.setAddress(address);
        address.addInfoEntity(p);

        CityInfo cityinfo = new CityInfo();
        
        String zip = person.get("zipcode").getAsString();
        String city = person.get("city").getAsString();
        int zipnumber = Integer.parseInt(zip);
        cityinfo.setZip(zipnumber);
        cityinfo.setCity(city);

        address.setCityInfo(cityinfo);
        
        cityinfo.addAddress(address);

        JsonArray hobbiesArr = person.getAsJsonArray("hobbies");
        for (JsonElement pjo : hobbiesArr) {
            Hobby hob = new Hobby();
            String name = pjo.getAsJsonObject().get("name").getAsString();
            String desc = pjo.getAsJsonObject().get("description").getAsString();

            hob.setName(name);
            hob.setDescription(desc);
            p.addHobby(hob);
            hob.addPerson(p);

        }

//        
        Person returnPerson = (Person) cus.addPerson(p);

        JsonObject jsonReturned = new JsonObject();
        jsonReturned.addProperty("id", returnPerson.getId());
        jsonReturned.addProperty("firstName", p.getFirstName());
        jsonReturned.addProperty("lastName", p.getLastName());
        jsonReturned.addProperty("email", p.getEmail());

        List<Phone> phones = returnPerson.getPhones();
        JsonArray phoneArray = new JsonArray();
        for (Phone phone : phones) {
            JsonObject phoneJson = new JsonObject();
            phoneJson.addProperty("number", phone.getNumber());
            phoneJson.addProperty("description", phone.getDescription());
            phoneArray.add(phoneJson);
        }

        jsonReturned.add("phones", phoneArray);

        jsonReturned.addProperty("strret", p.getAddress().getStreet());
        jsonReturned.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
        jsonReturned.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
        jsonReturned.addProperty("city", p.getAddress().getCityInfo().getCity());

        List<Hobby> hobbies = returnPerson.getHobbys();
        JsonArray hobArray = new JsonArray();
        for (Hobby hob : hobbies) {
            JsonObject hobJson = new JsonObject();
            hobJson.addProperty("name", hob.getName());
            hobJson.addProperty("description", hob.getDescription());
            hobArray.add(hobJson);
        }
        jsonReturned.add("phones", phoneArray);

        return gson.toJson(jsonReturned);
    }

    @DELETE
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces("application/json")
    public String delete(@PathParam("id") Integer id) {
        Gson gson = new Gson();

        InfoEntity info = cus.find(id);
        InfoEntity inforeturned = cus.deleteInfo(info);
        
        if (inforeturned instanceof Person) {
            Person returnedOne = (Person) inforeturned;
            JsonObject json = new JsonObject();
            json.addProperty("id", returnedOne.getId());
            json.addProperty("firstName", returnedOne.getFirstName());
            json.addProperty("lastName", returnedOne.getLastName());
            json.addProperty("email", returnedOne.getEmail());

            List<Phone> phones = returnedOne.getPhones();
            JsonArray phoneArray = new JsonArray();
            for (Phone phone : phones) {
                JsonObject phoneJson = new JsonObject();
                phoneJson.addProperty("number", phone.getNumber());
                phoneJson.addProperty("description", phone.getDescription());
                phoneArray.add(phoneJson);
            }

            json.add("phones", phoneArray);
            json.addProperty("strret", returnedOne.getAddress().getStreet());
            json.addProperty("additionalInfo", returnedOne.getAddress().getAdditionalInfo());
            json.addProperty("zipcode", returnedOne.getAddress().getCityInfo().getZipCode());
            json.addProperty("city", returnedOne.getAddress().getCityInfo().getCity());
            //jsonArray.add(json);
            return gson.toJson(json);
        } else {
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
            json.addProperty("strret", returnedOne.getAddress().getStreet());
            json.addProperty("additionalInfo", returnedOne.getAddress().getAdditionalInfo());
            json.addProperty("zipcode", returnedOne.getAddress().getCityInfo().getZipCode());
            json.addProperty("city", returnedOne.getAddress().getCityInfo().getCity());
            //jsonArray.add(json);
            return gson.toJson(json);
        }
    }
}
