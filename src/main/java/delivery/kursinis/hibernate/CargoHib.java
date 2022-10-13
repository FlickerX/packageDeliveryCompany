package delivery.kursinis.hibernate;

import delivery.kursinis.model.Cargo;
import delivery.kursinis.model.Checkpoint;
import delivery.kursinis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CargoHib {
    private EntityManager entityManager = null;
    private EntityManagerFactory entityManagerFactory = null;

    public CargoHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createCargo(Cargo cargo){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List getAllCargos(){
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Cargo.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null)
                entityManager.close();
        }
        return new ArrayList<Cargo>();
    }
    public void removeCargo(Cargo cargo){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(cargo) ? cargo : entityManager.merge(cargo));
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
