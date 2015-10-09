package facade;

import entity.Company;
import entity.InfoEntity;
import entity.Person;
import entity.Phone;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yoana
 */
public class Facade {

    EntityManagerFactory emf;
    private EntityManager emp;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Facade() {

    }

    protected EntityManager getEntityManager() {
        return emp;
    }

    public InfoEntity addPerson(InfoEntity p) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return p;
    }
    
    public Person editPerson(Person entity) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return entity;

    }

    public List<Person> getAllData() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT c FROM Person c", Person.class);
        // query.setParameter("phonenr", phone);
        List<Person> results = null;
        try {
            results = query.getResultList();
        } catch (Exception e) {
            System.out.println("Fejl i getAllPersons: " + e.getMessage());
        }
        return results;

    }

    public InfoEntity findInfoEntity(int id) {
        EntityManager em = emf.createEntityManager();
        System.out.println("I am in find method " + em.find(Person.class, id));
        return em.find(Person.class, id);
    }
    
    public Phone findPhone(int id) {
        EntityManager em = emf.createEntityManager();
        System.out.println("I am in find method " + em.find(Phone.class, id));
        return em.find(Phone.class, id);
    }

    public InfoEntity deleteInfo(InfoEntity entity) {
        EntityManager ems = emf.createEntityManager();
        try {
            ems.getTransaction().begin();
            entity = ems.merge(entity);
            ems.remove(entity);
            ems.getTransaction().commit();
        } finally {
            ems.close();
        }
        return entity;
    }
    
    public Phone deletePhone(Phone entity) {
        EntityManager ems = emf.createEntityManager();
        try {
            ems.getTransaction().begin();
            entity = ems.merge(entity);
            ems.remove(entity);
            ems.getTransaction().commit();
        } finally {
            ems.close();
        }
        return entity;
    }

    public Company findCompanyByCvr(int cvrNumber) {
        EntityManager ems = emf.createEntityManager();
        String queryString = "SELECT e FROM Company e WHERE e.cvr =:num";
        Query query3 = ems.createQuery(queryString);
        query3.setParameter("num", cvrNumber);
        Company l = (Company) query3.getSingleResult();
        return l;

    }

    public List<Person> getPersonsWithGivenHobby(String hob) {
        EntityManager ems = emf.createEntityManager();
        String queryString = "select e.per FROM Hobby e where e.name =:something";
        Query query3 = ems.createQuery(queryString);
        query3.setParameter("something", hob);
        List<Person> l = (List<Person>) query3.getResultList();
        return l;//it is needed to be checked if its null
    }

    public List<Company> getCompaniesWithMOreThanEmployees(int number) {
        EntityManager ems = emf.createEntityManager();
        String queryString = "select e. FROM Company e where e.NumEmployees >:something";
        Query query3 = ems.createQuery(queryString);
        query3.setParameter("something", number);
        List<Company> l = (List<Company>) query3.getResultList();
        return l;//it is needed to be checked if its null
    }

    public InfoEntity getInfoForPersonWIthAPHoneNumber(int number) {
        EntityManager ems = emf.createEntityManager();
        String queryString = "select e.infoEntity FROM Phone e where e.infoEntity =:something";
        Query query3 = ems.createQuery(queryString);
        query3.setParameter("something", number);
        InfoEntity info = (InfoEntity) query3.getSingleResult();
        return info;//it is needed to be checked if its null
    }

    public List<Integer> getListOfzipcodes() {
        EntityManager ems = emf.createEntityManager();
        String queryString = "select e.zip FROM CityInfo e ";
        Query query3 = ems.createQuery(queryString);

        List<Integer> list = query3.getResultList();
        return list;//it is needed to be checked if its null
    }

    public Integer getTheCOuntOfPeopleWithGivenHobby(int number) {

        EntityManager ems = emf.createEntityManager();
        String queryString = "select size(e.per) FROM Hobby e where e.name =:something";
        Query query3 = ems.createQuery(queryString);
        query3.setParameter("something", number);
        Integer num4e = (Integer) query3.getSingleResult();
        return num4e;

    }

    public List<InfoEntity> getAllinfoEntity() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<InfoEntity> query = em.createQuery("SELECT c FROM Person c", InfoEntity.class);
        // query.setParameter("phonenr", phone);
        List<InfoEntity> results = null;
        try {
            results = query.getResultList();
        } catch (Exception e) {
            System.out.println("Fejl i getAllPersons: " + e.getMessage());
        }
        return results;

    }

}
