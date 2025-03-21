package hello.springjpa.fetch_join;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ChildA> childAs = new ArrayList<>();

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ChildB> childBs = new ArrayList<>();

    protected Parent() {}
    public Parent(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public List<ChildA> getChildAs() { return childAs; }
    public List<ChildB> getChildBs() { return childBs; }
}