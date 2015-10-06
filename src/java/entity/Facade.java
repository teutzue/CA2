
package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author CosticaTeodor
 */
public class Facade 
{
   EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) 
    {
        this.emf = emf;
    }
     public InfoEntity addInfo(InfoEntity e)
     {
         EntityManager em = emf.createEntityManager();
         try
         {
             em.getTransaction().begin();
             em.persist(e);
             em.getTransaction().commit();
         }finally{
             em.close();
         }
         return e;
     }
       public Address addAddress(Address e)
     {
         EntityManager em = emf.createEntityManager();
         try
         {
             em.getTransaction().begin();
             em.persist(e);
             em.getTransaction().commit();
         }finally{
             em.close();
         }
         return e;
     }
   
}
