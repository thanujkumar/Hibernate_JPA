package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the WAREHOUSES database table.
 * <p>
 * Warehouse belong to a single location and each warehouse has multiple inventories.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "WAREHOUSES")
//@NamedQuery(name = "Warehouse.findAll", query = "SELECT w FROM Warehouse w")
public class Warehouse extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "WAREHOUSES_SEQ_GENERATOR", sequenceName = "WAREHOUSES_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WAREHOUSES_SEQ_GENERATOR")
    @Column(name = "WAREHOUSE_ID", unique = true, nullable = false)
    private long warehouseId;

    @Column(name = "WAREHOUSE_NAME", length = 255)
    private String warehouseName;

    //bi-directional many-to-one association to Location
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    //bi-directional one-to-many association to Inventory
    @OneToMany(mappedBy = "warehouse")//Inventory table will have whareshouse_id
    private List<Inventory> inventories;

    public Inventory addInventory(Inventory inventory) {
        getInventories().add(inventory);
        inventory.setWarehouse(this);

        return inventory;
    }

    public Inventory removeInventory(Inventory inventory) {
        getInventories().remove(inventory);
        inventory.setWarehouse(null);

        return inventory;
    }
}
