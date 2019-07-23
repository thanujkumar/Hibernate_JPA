package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "CONTACTS")
//@NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c")
public class Contact extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "CONTACTS_SEQ_GENERATOR", sequenceName = "CONTACTS_SEQ", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACTS_SEQ_GENERATOR")
    @Column(name = "CONTACT_ID", unique = true, nullable = false)
    private long contactId;

    @Column(name = "FIRST_NAME", nullable = false, length = 255)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 255)
    private String lastName;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 20)
    private String phone;

    //bi-directional many-to-one association to Customer
    @ManyToOne(fetch = FetchType.LAZY) //ManyToOne default JPA fetch is eager
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
}
