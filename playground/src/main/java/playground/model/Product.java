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
@Table(name = "PRODUCTS")
//@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "PRODUCTS_SEQ_GENERATOR", sequenceName = "PRODUCTS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTS_SEQ_GENERATOR")
    @Column(name = "PRODUCT_ID", unique = true, nullable = false)
    private long productId;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 255)
    private String productName;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STANDARD_COST", precision = 9, scale = 2)
    private BigDecimal standardCost;

    @Column(name = "LIST_PRICE", precision = 9, scale = 2)
    private BigDecimal listPrice;

    //bi-directional many-to-one association to Inventory
    @OneToMany(mappedBy = "product") //product belongs to multiple inventory locations
    private List<Inventory> inventories;

    //bi-directional many-to-one association to OrderItem
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    //bi-directional many-to-one association to ProductCategory
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private ProductCategory productCategory;

    public Inventory addInventory(Inventory inventory) {
        getInventories().add(inventory);
        inventory.setProduct(this);

        return inventory;
    }

    public Inventory removeInventory(Inventory inventory) {
        getInventories().remove(inventory);
        inventory.setProduct(null);

        return inventory;
    }

    public OrderItem addOrderItem(OrderItem orderItem) {
        getOrderItems().add(orderItem);
        orderItem.setProduct(this);

        return orderItem;
    }

    public OrderItem removeOrderItem(OrderItem orderItem) {
        getOrderItems().remove(orderItem);
        orderItem.setProduct(null);

        return orderItem;
    }
}
