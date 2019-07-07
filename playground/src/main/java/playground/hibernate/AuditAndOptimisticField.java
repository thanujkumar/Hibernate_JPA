package playground.hibernate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * You can use a database update trigger that performs the change on a database level. Most DBAs will suggest this
 * approach because it’s easy to implement on a database level. But Hibernate needs to perform an additional query to
 * retrieve the generated values from the database.
 * <ul>
 * <li>You can use an entity lifecycle event to update the timestamp attribute of the entity before Hibernate performs the update.
 * <li>You can use an additional framework, like Hibernate Envers, to write an audit log and get the update timestamp from there.
 * <li>You can use the Hibernate-specific @CreationTimestamp and @UpdateTimestamp annotations and let Hibernate trigger the required updates.
 * </ul>
 */

@MappedSuperclass
@Data
@EqualsAndHashCode(doNotUseGetters = true)
public abstract class AuditAndOptimisticField {

    @Column(name = "CREATED_BY", nullable = false, length = 16)
    private String createdBy;

    @Column(name = "CREATED_TS", nullable = false)
    @CreationTimestamp
    private Timestamp createdTs;

    @Column(name = "MODIFIED_BY", length = 16)
    private String modifiedBy;

    @Column(name = "MODIFIED_TS")
    @UpdateTimestamp
    private Timestamp modifiedTs;

    @Version
    @Column(name = "VERSION", nullable = false, precision = 18)
    private BigDecimal version;

}
