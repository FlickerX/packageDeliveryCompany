package delivery.kursinis.hibernate;

import delivery.kursinis.model.Comment;
import delivery.kursinis.model.Manager;
import delivery.kursinis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CommentHib {
    private EntityManager entityManager = null;
    private EntityManagerFactory entityManagerFactory = null;

    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createComment(Comment comment){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateComment(Comment comment){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(comment);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }
    public void deleteUser(Comment comment){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }
    public Comment getCommentByID(int id){
        entityManager = entityManagerFactory.createEntityManager();
        Comment comment = null;
        try{
            entityManager.getTransaction().begin();
            comment = entityManager.find(Comment.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println("No such user");
        }
        return comment;
    }
}
