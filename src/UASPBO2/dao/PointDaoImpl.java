package UASPBO2.dao;
/**
 * @author - AbednegoSteven 1972009
 */

import UASPBO2.entities.FeMemberEntity;
import UASPBO2.entities.FePointEntity;
import UASPBO2.entities.FeTransactionEntity;
import UASPBO2.utils.HibernateUtil;
import javafx.collections.FXCollections;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class PointDaoImpl implements daointerface<FePointEntity>{
    @Override
    public int addData(FePointEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public int delData(FePointEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public int updateData(FePointEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.update(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public List<FePointEntity> getAll() {
        Session s = HibernateUtil.getSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(FePointEntity.class);
        query.from(FePointEntity.class);
        List<FePointEntity> cList = s.createQuery(query).getResultList();
        s.close();
        return FXCollections.observableArrayList(cList);
    }

    public List<FePointEntity> getAllPoint( FeMemberEntity citizenId) {
        Session s = HibernateUtil.getSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(FePointEntity.class);
        Root<FePointEntity> root =  query.from(FePointEntity.class);
        Predicate p = builder.equal(root.get("feMemberByMemberCitizenId"), citizenId);
        List<FePointEntity> cList = s.createQuery(query.where(p)).getResultList();
        s.close();
        return FXCollections.observableArrayList(cList);
    }
}
