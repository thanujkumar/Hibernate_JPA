package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the ORDER_ITEMS database table.
 * <p>
 * Order consists of multiple order items (item_id=product_id), and each order item is a product
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "ORDER_ITEMS")
@NamedQuery(name = "OrderItem.findAll", query = "SELECT o FROM OrderItem o")
public class OrderItem extends AuditAndOptimisticField implements Serializable {

    @EmbeddedId
    private OrderItemPK id;

    @Column(nullable = false, precision = 8, scale = 2)
    private double quantity;

    @Column(name = "UNIT_PRICE", nullable = false, precision = 8, scale = 2)
    private BigDecimal unitPrice;

    //bi-directional many-to-one association to Order
    @ManyToOne //Many items are part of order
    @JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false)
    private Order order;

    //bi-directional many-to-one association to Product
    @ManyToOne //Many products can exits as part of Order, her Product is orderItem
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;
}
