package dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import model.Opentalk;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OpentalkDAO {
    private EntityManager entityManager;

    public OpentalkDAO() {
        this.entityManager = DBContext.getEntityManager();
    }

    public List<Opentalk> getAllOpentalks() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Opentalk> cq = cb.createQuery(Opentalk.class);
        Root<Opentalk> root = cq.from(Opentalk.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    public boolean addOpentalk(Opentalk opentalk) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(opentalk);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public boolean deleteOpentalk(int id) {
        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaDelete<Opentalk> delete = cb.createCriteriaDelete(Opentalk.class);
            Root<Opentalk> root = delete.from(Opentalk.class);
            delete.where(cb.equal(root.get("id"), id));
            entityManager.createQuery(delete).executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public Opentalk updateOpentalk(Opentalk opentalk) {
        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<Opentalk> update = cb.createCriteriaUpdate(Opentalk.class);
            Root<Opentalk> root = update.from(Opentalk.class);
            update.set(root.get("title"), opentalk.getTitle());
            update.set(root.get("startTime"), opentalk.getStartTime());
            update.set(root.get("endTime"), opentalk.getEndTime());
            update.set(root.get("status"), opentalk.getStatus());
            update.where(cb.equal(root.get("id"), opentalk.getId()));

            entityManager.createQuery(update).executeUpdate();
            entityManager.getTransaction().commit();
            return searchOpentalk(opentalk.getId());
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Opentalk searchOpentalk(int id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Opentalk> cq = cb.createQuery(Opentalk.class);
        Root<Opentalk> root = cq.from(Opentalk.class);
        cq.select(root).where(cb.equal(root.get("id"), id));

        return entityManager.createQuery(cq).getSingleResult();
    }
}
