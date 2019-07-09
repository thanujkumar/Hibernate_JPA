package playground.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

/**
 * The primary key class for the ORDER_ITEMS database table.
 * <p>
 * Composite primary key order_id and item_id
 */
@Data
@Embeddable
public class OrderItemPK {

    @Column(name = "ORDER_ID", insertable = false, updatable = false, unique = true, nullable = false, precision = 12)
    private long orderId;

    @Column(name = "ITEM_ID", unique = true, nullable = false, precision = 12)
    private long itemId;

}
