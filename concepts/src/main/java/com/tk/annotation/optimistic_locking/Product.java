package com.tk.annotation.optimistic_locking;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

//https://vladmihalcea.com/optimistic-locking-version-property-jpa-hibernate/
//https://vladmihalcea.com/the-anatomy-of-hibernate-dirty-checking/
//https://vladmihalcea.com/how-to-enable-bytecode-enhancement-dirty-checking-in-hibernate/

@Data
@Entity(name = "PRODUCT")
//@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    @SequenceGenerator(allocationSize = 1, sequenceName = "PRODUCT_SEQ", name = "product_seq_generator")
    private Long id;

    @Column(name = "NAME", length = 50)
    private String productName;

    @Column(name = "QUANTITY")
    private int quantity;

    @Version //Required for optimistic locking
    private Long version;
}
