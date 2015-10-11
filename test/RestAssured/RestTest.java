/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 // * and open the template in the editor.
 // */
//package RestAssured;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
package RestAssured;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import Entity.Person;
import facade.Facade;
//import JaxRs.JsonConverter;
import com.google.common.net.MediaType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

// RestAssured
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
// com.jayway.restassured.RestAssured.Parser;
import com.jayway.restassured.parsing.Parser;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;
//import com.jayway.restassured.Response;
import entity.Address;
import entity.CityInfo;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import facade.JSONConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static javax.ws.rs.client.Entity.json;
//import org.json.JSONArray;

import testData.TestDataPerson;

/**
 *
 * @author bo
 */
public class RestTest {

    // This has to be changed later
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2-deployment"); //CA2-test //CA2-deployment
//    Facade facade = new Facade(emf);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    public RestTest() {
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        baseURI = "http://localhost:8080";
        defaultParser = Parser.JSON;
        basePath = "/CA2";
    }

    @Before
    public void setUp() {

//        EntityManager em = facade.getEntityManager();
//
//        try {
//
//            em.getTransaction().begin();
//            // here should all the tables in the database be deleted
//            em.createQuery("DELETE FROM Person WHERE DTYPE = P").executeUpdate();
//            //DELETE FROM INFOENTITY WHERE (DTYPE = ?)
//            em.getTransaction().commit();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
    }

    @Test
    public void getPersonJson() {

        Integer id = postPerson(TestDataPerson.getNewPerson());

        String personJsWithId = getPerson(id);

        Person personWithId = JSONConverter.getPersonFromJson(personJsWithId);

        assertTrue(TestDataPerson.compareProperties(personWithId));

    } // End of method

    @Test
    public void putPersonJson() {

        Integer id = postPerson(TestDataPerson.getNewPerson());

        String personJsWithId = getPerson(id);

        Person personToEdit = JSONConverter.getPersonFromJson(personJsWithId);

        personToEdit.setFirstName(TestDataPerson.getFIRST_NAME() + "-additional text");
        personToEdit.setLastName(TestDataPerson.getLAST_NAME() + "-additional text");

        id = putPerson(personToEdit);

        String personEditedString = getPerson(id);

        Person personEdited = JSONConverter.getPersonFromJson(personEditedString);

        assertFalse(TestDataPerson.compareProperties(personEdited));
        assertTrue(personEdited.getFirstName().contentEquals(TestDataPerson.getFIRST_NAME() + "-additional text"));

    } // End of method

    @Test
    public void getPersonsListOfJson() {

        // insert to persons in the database
        postPerson(TestDataPerson.getNewPerson());
        postPerson(TestDataPerson.getNewPerson());

        String jsonString = getPersons();

       ArrayList<Person> persons = (ArrayList) json2persons(jsonString); // .json2persons(jsonString) );
        //JsonArray ja = gson.fromJson(jsonString, JsonArray.class);
       
        // assertTrue( TestData.compareProperties(jsonString) );

        //assertEquals(persons.size(), 2);
//        assertTrue(TestDataPerson.compareProperties(persons.));
//        assertTrue(TestDataPerson.compareProperties(persons.get(1)));
    }

    public static List<Person> json2persons(String jsonString) {

        JsonArray ja = gson.fromJson(jsonString, JsonArray.class);

        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {

            persons.add(JSONConverter.getPersonFromJson((ja.get(i).toString())));

        }
        return persons;
    }

    //---------------------------------------
    public String getPersons() {

        Response response
                = given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/person")
                .then()
                .extract().response();

        String jsonString = response.asString(); // return json string

        return jsonString;
    }

    public Integer putPerson(Person person) {

        return given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .put("api/person")
                .then().extract().path("id");
    } // End of method

    public Integer postPerson(Person person) {

        return given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .post("api/person")
                .then().extract().path("id");
    } // End of method

    public String getPerson(int id) {

        Response response
                = given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/person/complete/" + id)
                .then()
                .extract().response();

        return response.asString(); // return json string

    } // End of method

} // End of Class
