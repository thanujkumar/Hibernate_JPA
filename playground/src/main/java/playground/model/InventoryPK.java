package playground.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the INVENTORIES database table.
 * <p>
 * Composite primary key (product_id, warehouse_id)
 */
@Data
@Embeddable
public class InventoryPK implements Serializable {

    @Column(name = "PRODUCT_ID", insertable = false, updatable = false, unique = true, nullable = false, precision = 12)
    private long productId;

    @Column(name = "WAREHOUSE_ID", insertable = false, updatable = false, unique = true, nullable = false, precision = 12)
    private long warehouseId;
}
