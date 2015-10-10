package facade;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Address;
import entity.CityInfo;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import java.util.List;

public class JSONConverter 
{

    private static Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
  //private static Gson gson = new Gson();

    public static Person getPersonFromJson(String jsonAsString)
    {
        Gson gson = new Gson();
        Person p = new Person(); 
        JsonObject person = new JsonParser().parse(jsonAsString).getAsJsonObject();
        if(person.has("id"))
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
     //   System.out.println(p.getId());
        return p;

    }

    
    
     public static String getJSONFromPersons(List<Person> people) 
    {
        
        JsonArray jsonArray = new JsonArray();
        for (Person p : people) 
        {
            JsonObject json = new JsonObject();
            json.addProperty("id", p.getId());
            json.addProperty("firstName", p.getFirstName());
            json.addProperty("lastName", p.getLastName());
            json.addProperty("email", p.getEmail());
            
            List<Phone> phones = p.getPhones();
            if (!phones.isEmpty()) 
            {
            JsonArray phoneArray = new JsonArray();
            for (Phone phone : phones) {
                JsonObject phoneJson = new JsonObject();
                phoneJson.addProperty("number", phone.getNumber());
                phoneJson.addProperty("description", phone.getDescription());
                phoneArray.add(phoneJson);
            }
            json.add("phones", phoneArray); 
            }

            if(p.getAddress() != null)
            {
                 json.addProperty("street", p.getAddress().getStreet());
                 json.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
                 
            }
            if(p.getAddress().getCityInfo() != null)
                    {
            json.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
            json.addProperty("city", p.getAddress().getCityInfo().getCity());
                        
                    }
           
            List<Hobby> hobbies = p.getHobbys();
            JsonArray hobArray = new JsonArray();
            if(!hobbies.isEmpty())
            {
            for (Hobby hob : hobbies) 
            {
                JsonObject hobJson = new JsonObject();
                hobJson.addProperty("name", hob.getName());
                hobJson.addProperty("description", hob.getDescription());
                hobArray.add(hobJson);
            }
            json.add("hobbies", hobArray);
            }
            jsonArray.add(json);
        }
        return gson.toJson(jsonArray);
    }
    
    public static String getJSONFromPerson(Person p) 
    {     
            JsonObject json = new JsonObject();
            json.addProperty("id", p.getId());
            json.addProperty("firstName", p.getFirstName());
            json.addProperty("lastName", p.getLastName());
            json.addProperty("email", p.getEmail());
            List<Phone> phones = p.getPhones();  
            if(!phones.isEmpty())
            {
            JsonArray phoneArray = new JsonArray();
            for (Phone phone : phones) 
            {
                JsonObject phoneJson = new JsonObject();
                phoneJson.addProperty("number", phone.getNumber());
                phoneJson.addProperty("description", phone.getDescription());
                phoneArray.add(phoneJson);
            }
            json.add("phones", phoneArray);    
            }       
             if(p.getAddress()!=null )
          {
          json.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
          json.addProperty("street", p.getAddress().getStreet());
          }
          if(p.getAddress().getCityInfo()!=null)
          {
          json.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
          json.addProperty("city", p.getAddress().getCityInfo().getCity());
          }
            
            
            List<Hobby> hobbies = p.getHobbys();
            if(!hobbies.isEmpty())
            {
            JsonArray hobArray = new JsonArray();
            for (Hobby hob : hobbies) 
            {
                JsonObject hobJson = new JsonObject();
                hobJson.addProperty("name", hob.getName());
                hobJson.addProperty("description", hob.getDescription());
                hobArray.add(hobJson);
            }
            json.add("hobbies", hobArray);  
            }
            return gson.toJson(json);
    }
    
     public static String getJSONFromPersonForPUT(Person p) 
    {
        
            JsonObject json = new JsonObject();
            json.addProperty("id", p.getId());
            json.addProperty("firstName", p.getFirstName());
            json.addProperty("lastName", p.getLastName());
            json.addProperty("email", p.getEmail());

            List<Phone> phones = p.getPhones();
            
            if(!phones.isEmpty())
            {
            JsonArray phoneArray = new JsonArray();
            for (Phone phone : phones) 
            {
                JsonObject phoneJson = new JsonObject();
                phoneJson.addProperty("number", phone.getNumber());
                phoneJson.addProperty("description", phone.getDescription());
                phoneArray.add(phoneJson);
            }
            json.add("phones", phoneArray);    
            }

            
             if(p.getAddress()!=null )
          {
          json.addProperty("additionalInfo", p.getAddress().getAdditionalInfo());
          json.addProperty("street", p.getAddress().getStreet());
          }
          if(p.getAddress().getCityInfo()!=null)
          {
          json.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
          json.addProperty("city", p.getAddress().getCityInfo().getCity());
          }
            
            
            List<Hobby> hobbies = p.getHobbys();
            if(!hobbies.isEmpty())
            {
            JsonArray hobArray = new JsonArray();
            for (Hobby hob : hobbies) 
            {
                JsonObject hobJson = new JsonObject();
                hobJson.addProperty("name", hob.getName());
                hobJson.addProperty("description", hob.getDescription());
                hobArray.add(hobJson);
            }
            json.add("hobbies", hobArray);  
            }
            return gson.toJson(json);
    }
    

    public static String getJSONFromPerson(List<Person> persons) 
    {
        return gson.toJson(persons);
    }
}
