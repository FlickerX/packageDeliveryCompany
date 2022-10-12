package delivery.kursinis.hibernate;

import delivery.kursinis.model.Destination;
import delivery.kursinis.model.Truck;
import delivery.kursinis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class DestinationHib {
    private EntityManager entityManager = null;
    private EntityManagerFactory entityManagerFactory = null;

    public DestinationHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public void createDestination(Destination destination){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(destination);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List getAllDestinations(){
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Destination.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null)
                entityManager.close();
        }
        return new ArrayList<Destination>();
    }
}
