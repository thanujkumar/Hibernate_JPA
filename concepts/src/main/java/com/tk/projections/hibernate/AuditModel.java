package com.tk.projections.hibernate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * <ul>
 * <li>You can use a database update trigger that performs the change on a database level. Most DBAs will suggest this
 * approach because it’s easy to implement on a database level. But Hibernate needs to perform an additional query to retrieve the generated values from the database.
 * <li>You can use an entity lifecycle event to update the timestamp attribute of the entity before Hibernate performs the update.
 * <li>You can use an additional framework, like Hibernate Envers, to write an audit log and get the update timestamp from there.
 * <li>You can use the Hibernate-specific @CreationTimestamp and @UpdateTimestamp annotations and let Hibernate trigger the required updates.
 * </ul>
 */

@MappedSuperclass
@ToString
@EqualsAndHashCode(doNotUseGetters = true)
@Data
public abstract class AuditModel {

    @Column(name = "CREATED_DT")
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DT")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

}
