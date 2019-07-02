package com.tk.projections.hibernate.unidirection.child;

import com.tk.projections.hibernate.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//https://vladmihalcea.com/a-beginners-guide-to-jpa-hibernate-entity-state-transitions/
//https://vladmihalcea.com/the-anatomy-of-hibernate-dirty-checking/

/**
 * <link>https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/</link>
 *
 * <p>
 * Problems with bidirectional one-to-many mapping
 * <ul>
 * <li>A bidirectional mapping tightly couples the many-side of the relationship to the one-side.
 * <li>If you load comments via the post entity, you won’t be able to limit the number of comments loaded. That
 * essentially means that you won’t be able to paginate. You won’t be able to sort them based on different properties.
 * You can define a default sorting order using @OrderColumn annotation but that will have performance implications.
 * </ul>
 * </p>
 * <p>When can I use a bidirectional one-to-many mapping</p>
 * A bidirectional one-to-many mapping might be a good idea if the number of child entities is limited.
 */
@Entity
@Data
@Table(name = "POSTS")
public class Post extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    @Lob
    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
