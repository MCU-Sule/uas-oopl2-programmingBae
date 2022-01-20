package UASPBO2.dao;
/**
 * @author - AbednegoSteven 1972009
 */

import UASPBO2.entities.FeMemberEntity;
import UASPBO2.utils.HibernateUtil;
import javafx.collections.FXCollections;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class MemberDaoImpl implements daointerface<FeMemberEntity>{
    @Override
    public int addData(FeMemberEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public int delData(FeMemberEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public int updateData(FeMemberEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.update(data);
        t.commit();
        s.close();
        return 0;
    }

    @Override
    public List<FeMemberEntity> getAll() {
        Session s = HibernateUtil.getSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(FeMemberEntity.class);
        query.from(FeMemberEntity.class);
        List<FeMemberEntity> cList = s.createQuery(query).getResultList();
        s.close();
        return FXCollections.observableArrayList(cList);
    }
}
