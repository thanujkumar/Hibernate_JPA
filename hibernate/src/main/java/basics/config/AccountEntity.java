package basics.config;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "acct")
@Table(name = "ACCOUNT")
@Data
public class AccountEntity {

    @Id
    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_generator")
    @SequenceGenerator(allocationSize = 1, sequenceName = "ACCOUNT_SEQ", name = "account_seq_generator")
    private Long id;

    @Column(name = "NAME", length = 40)
    private String name;

    @Column(name = "AGE")
    private Integer age;

    @Column(name="DATE_OF_BIRTH", nullable = false)
    private Date dob;

    @Version
    private Long version;
}
