package delivery.kursinis.hibernate;

import delivery.kursinis.model.Comment;
import delivery.kursinis.model.Forum;
import delivery.kursinis.model.Manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ForumHib {
    private EntityManager entityManager = null;
    private EntityManagerFactory entityManagerFactory = null;

    public ForumHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createForum(Forum forum){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(forum);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateForum(Forum forum){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(forum);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }
    public List<Forum> getAllForums(){
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Forum.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null)
                entityManager.close();
        }
        return new ArrayList<Forum>();
    }
    public Forum getForumByID(int id){
        entityManager = entityManagerFactory.createEntityManager();
        Forum forum = null;
        try{
            entityManager.getTransaction().begin();
            forum = entityManager.find(Forum.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println("No such user");
        }
        return forum;
    }
}
