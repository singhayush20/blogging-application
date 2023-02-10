package com.ayushsingh.bloggingapplication.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;
    @Column(name = "title", length = 50, nullable = false, unique = true)
    private String categoryName;
    @Column(name = "description", nullable = false)
    private String categoryDescription;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // one category can have many posts
    // this list is mapped by the category member of the post (mapping on category)
    // cascade = CascadeType.ALL: This ensures that the posts are removed if the
    // category is removed
    // Defines the set of cascadable operations that are propagated to the
    // associated entity. The value cascade=ALL is equivalent to cascade={PERSIST,
    // MERGE, REMOVE, REFRESH, DETACH}
    private List<Post> posts = new ArrayList<>();

    // set of users who follow the category
    // @ManyToMany(mappedBy = "categories",cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY) 
    @JoinTable(name = "user_category", // the name of the table which manages this relationship
            // specify the join columns in that table
            joinColumns = @JoinColumn(
                    /*
                     * name of the join column
                     * The name of the foreign key column which stores the user id
                     * The table in which it is found depends upon the context.
                     */
                    name = "category_id",
                    /*
                     * The name of the primary key in the
                     * user table which works as the foreign key in this table
                     * column.
                     */
                    referencedColumnName = "categoryId"),
            /*
             * The foreign key columns of the join table which reference the primary table
             * of the entity that does not own the association. (I.e. the inverse side of
             * the association).
             */
            inverseJoinColumns = @JoinColumn(
                    /*
                     * The name of the foreign key column. The table in which it is found depends
                     * upon the context. The column which keeps the role id in this table
                     */
                    name = "user_id",

                    /*
                     * The name of the column referenced by this foreign key column. The primary key
                     * in the category table working as the foreign key in this table
                     */
                    referencedColumnName = "id"))
    Set<User> users = new HashSet<>();

    // A bidirectional association mapping consists of an owning and a referencing
    // part. The owning part defines the association, and the referencing part
    // reuses the existing mapping. For a many-to-many association, both parts might
    // seem identical. But they are handled differently if you remove an entity. If
    // you remove an entity that owns the association, Hibernate also removes all
    // its associations. But it doesn’t do that if you delete an entity that
    // references the entity. You then need to remove all associations yourself
    // before you can remove the entity.
    @PreRemove
    public void removeUsers() {
        System.out.println("Removing users for " + this.categoryName + " before deleting");
        this.users=null;
        this.users=new HashSet<>();
    }
}
