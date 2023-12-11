package delivery.kursinis.hibernate;

import delivery.kursinis.model.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class TruckHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public TruckHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public void createTruck(Truck truck){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(truck);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List getAllTrucks(){
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Truck.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null)
                entityManager.close();
        }
        return new ArrayList<Truck>();
    }
    public void updateTruck(Truck truck){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(truck);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }
    public Truck getTruckByID(int id){
        entityManager = entityManagerFactory.createEntityManager();
        Truck truck = null;
        try{
            entityManager.getTransaction().begin();
            truck = entityManager.find(Truck.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println("No such user");
        }
        return truck;
    }
    public void removeTruck(Truck truck){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(truck) ? truck : entityManager.merge(truck));
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
