package hellojpa;

import com.sun.istack.Nullable;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Member {
    @Id
    private long id;

    @Column(name = "user_name")
    private String userName;

    private BigDecimal bigDecimal;

    private int age;

    @Nullable
    private String gender;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ColumnDefault("NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    @Transient
    private int temp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }
}
