package pet.backend.restserver.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.UUID;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "BASE_ENTITY")
public class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
 //   @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;
    private String className;
    @Deprecated
    public BaseEntity(){}

    @JsonCreator
    public BaseEntity(String id){
        this.id = UUID.fromString(id);
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
