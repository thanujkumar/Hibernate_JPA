package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "ORDERS")
//@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "ORDERS_SEQ_GENERATOR", sequenceName = "ORDERS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ_GENERATOR")
    @Column(name = "ORDER_ID", unique = true, nullable = false)
    private long orderId;

    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_DATE", nullable = false)
    private Date orderDate;

    @Column(nullable=false, length=20)
    private String status;

    //bi-directional many-to-one association to Customer
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    //bi-directional many-to-one association to Employee
    @ManyToOne
    @JoinColumn(name = "SALESMAN_ID")
    private Employee employee;

    //bi-directional many-to-one association to OrderItem
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public OrderItem addOrderItem(OrderItem orderItem) {
        getOrderItems().add(orderItem);
        orderItem.setOrder(this);

        return orderItem;
    }

    public OrderItem removeOrderItem(OrderItem orderItem) {
        getOrderItems().remove(orderItem);
        orderItem.setOrder(null);

        return orderItem;
    }
}
