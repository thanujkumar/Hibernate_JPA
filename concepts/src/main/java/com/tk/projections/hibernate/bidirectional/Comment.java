package com.tk.projections.hibernate.bidirectional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * one-to-many relationship is to use just @ManyToOne annotation on the child entity.
 * <p>
 * The second best way is to define a bidirectional association with a @OneToMany annotation on the parent side of the
 * relationship and a @ManyToOne annotation on the child side of the relationship. The bidirectional mapping has its
 * pros and cons.
 */
@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(doNotUseGetters = true)
@Table(name = "COMMENTS")
public class Comment extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;

    /**
     * @JoinColumn indicates the entity is the owner of the relationship and the corresponding table has a column with
     * a foreign key to the referenced table. mappedBy indicates the entity is the inverse of the relationship.
     */
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne
    //@JoinColumn(name = "post_id", nullable = false) //Foreign key to PostWrong table id column, not required as
    @JoinColumn(name = "p_id")
    // Posts has mappedBy
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
}
