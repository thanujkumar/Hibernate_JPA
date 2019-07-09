package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "PRODUCT_CATEGORIES")
@NamedQuery(name = "ProductCategory.findAll", query = "SELECT p FROM ProductCategory p")
public class ProductCategory extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "PRODUCT_CATEGORIES_SEQ_GENERATOR", sequenceName = "PRODUCT_CATEGORIES_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_CATEGORIES_SEQ_GENERATOR")
    @Column(name = "CATEGORY_ID", unique = true, nullable = false)
    private long categoryId;
}
