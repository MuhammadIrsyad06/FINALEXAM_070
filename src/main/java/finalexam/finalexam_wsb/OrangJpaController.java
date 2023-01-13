/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalexam.finalexam_wsb;

import finalexam.finalexam_wsb.exceptions.NonexistentEntityException;
import finalexam.finalexam_wsb.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author lenovo
 */
public class OrangJpaController implements Serializable {

    public OrangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalexam_finalexam_wsb_jar_0.0.1-SNAPSHOTPU");

    public OrangJpaController() {
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orang orang) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(orang);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrang(orang.getId()) != null) {
                throw new PreexistingEntityException("Orang " + orang + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orang orang) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            orang = em.merge(orang);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orang.getId();
                if (findOrang(id) == null) {
                    throw new NonexistentEntityException("The orang with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orang orang;
            try {
                orang = em.getReference(Orang.class, id);
                orang.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orang with id " + id + " no longer exists.", enfe);
            }
            em.remove(orang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orang> findOrangEntities() {
        return findOrangEntities(true, -1, -1);
    }

    public List<Orang> findOrangEntities(int maxResults, int firstResult) {
        return findOrangEntities(false, maxResults, firstResult);
    }

    private List<Orang> findOrangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orang.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Orang findOrang(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orang.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orang> rt = cq.from(Orang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
