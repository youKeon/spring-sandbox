package hello.springjpa.fetch_join;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ChildB {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent;

    protected ChildB() {}
    public ChildB(String name, Parent parent) {
        this.name = name;
        this.parent = parent;
    }
}