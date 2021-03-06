package com.tk.projections.hibernate.unidirection.child_to_parent;

import com.tk.projections.hibernate.bidirectional.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "POSTS_PARENT2")
public class PostParentC extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String title;
}
