package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "CUSTOMERS")
//@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
public class Customer extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "CUSTOMERS_SEQ_GENERATOR", sequenceName = "CUSTOMERS_SEQ", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMERS_SEQ_GENERATOR")
    @Column(name = "CUSTOMER_ID", unique = true, nullable = false)
    private long customerId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(length = 255)
    private String website;

    @Column(name = "CREDIT_LIMIT", precision = 8, scale = 2)
    private BigDecimal creditLimit;

    //bi-directional many-to-one association to Contact
    @OneToMany(mappedBy = "customer")//OneToMany default JPA fetch is Lazy
    private List<Contact> contacts;

    //bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "customer")//OneToMany default JPA fetch is Lazy
    private List<Order> orders;

    public Contact addContact(Contact contact) {
        getContacts().add(contact);
        contact.setCustomer(this);

        return contact;
    }

    public Contact removeContact(Contact contact) {
        getContacts().remove(contact);
        contact.setCustomer(null);

        return contact;
    }

    public Order addOrder(Order order) {
        getOrders().add(order);
        order.setCustomer(this);

        return order;
    }

    public Order removeOrder(Order order) {
        getOrders().remove(order);
        order.setCustomer(null);

        return order;
    }
}
