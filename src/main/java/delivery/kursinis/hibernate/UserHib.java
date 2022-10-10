package delivery.kursinis.hibernate;

import delivery.kursinis.model.Courier;
import delivery.kursinis.model.Manager;
import delivery.kursinis.model.Truck;
import delivery.kursinis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserHib {

    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createUser(User user){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateUser(User user){
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //TODO: Make filters here for user

    public User getUserByID(int id){
        entityManager = entityManagerFactory.createEntityManager();
        User user = null;
        try{
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println("No such user");
        }
        return user;
    }
    public User getUserByLoginData(String login, String password, boolean isManager){
        entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Courier> queryCourier = null;
        CriteriaQuery<Manager> queryManager = null;
        Query q = null;
        if (!isManager){
            queryCourier = criteriaBuilder.createQuery(Courier.class);
            Root<Courier> root = queryCourier.from(Courier.class);
            queryCourier.select(root).where(criteriaBuilder.like(root.get("login"), login),
                    criteriaBuilder.like(root.get("password"), password));
        }else {
            queryManager = criteriaBuilder.createQuery(Manager.class);
            Root<Manager> root = queryManager.from(Manager.class);
            queryManager.select(root).where(criteriaBuilder.like(root.get("login"), login),
                    criteriaBuilder.like(root.get("password"), password));
        }
        try {
            if (queryCourier != null) q = entityManager.createQuery(queryCourier);
            if (queryManager != null) q = entityManager.createQuery(queryManager);
            return (User) q.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    public List<Manager> getAllManagers(){
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Manager.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null)
                entityManager.close();
        }
        return new ArrayList<Manager>();
    }
    public List<Courier> getAllCouriers(){
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Courier.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null)
                entityManager.close();
        }
        return new ArrayList<Courier>();
    }
}
