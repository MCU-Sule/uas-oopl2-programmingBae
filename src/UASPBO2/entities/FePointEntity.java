package UASPBO2.entities;
/**
 * @author - AbednegoSteven 1972009
 */

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fe_point", schema = "oopl20211", catalog = "")
public class FePointEntity {
    private int id;
    private int value;
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
    @Column(name = "value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FePointEntity that = (FePointEntity) o;
        return value == that.value && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
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
