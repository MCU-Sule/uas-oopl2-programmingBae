package UASPBO2.entities;
/**
 * @author - AbednegoSteven 1972009
 */

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "fe_transaction", schema = "oopl20211", catalog = "")
public class FeTransactionEntity {
    private int id;
    private Date transDate;
    private long nominal;
    private FeMemberEntity feMemberByMemberCitizenId;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Basic
    @Column(name = "trans_date")
    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    @Basic
    @Column(name = "nominal")
    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeTransactionEntity that = (FeTransactionEntity) o;
        return nominal == that.nominal && Objects.equals(id, that.id) && Objects.equals(transDate, that.transDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transDate, nominal);
    }

    @ManyToOne
    @JoinColumn(name = "member_citizenId", referencedColumnName = "citizenId", nullable = false)
    public FeMemberEntity getFeMemberByMemberCitizenId() {
        return feMemberByMemberCitizenId;
    }

    public void setFeMemberByMemberCitizenId(FeMemberEntity feMemberByMemberCitizenId) {
        this.feMemberByMemberCitizenId = feMemberByMemberCitizenId;
    }
}
