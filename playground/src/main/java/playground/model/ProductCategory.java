package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "PRODUCT_CATEGORIES")
//@NamedQuery(name = "ProductCategory.findAll", query = "SELECT p FROM ProductCategory p")
public class ProductCategory extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "PRODUCT_CATEGORIES_SEQ_GENERATOR", sequenceName = "PRODUCT_CATEGORIES_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_CATEGORIES_SEQ_GENERATOR")
    @Column(name = "CATEGORY_ID", unique = true, nullable = false)
    private long categoryId;

    @Column(name = "CATEGORY_NAME", nullable = false, length = 255)
    private String categoryName;

    //bi-directional many-to-one association to Product
    @OneToMany(mappedBy = "productCategory")
    private List<Product> products;//Product table will have category_id

    public Product addProduct(Product product) {
        getProducts().add(product);
        product.setProductCategory(this);

        return product;
    }

    public Product removeProduct(Product product) {
        getProducts().remove(product);
        product.setProductCategory(null);

        return product;
    }
}
