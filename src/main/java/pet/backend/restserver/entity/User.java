package pet.backend.restserver.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.ApplicationContext;
import pet.backend.restserver.deserializer.CustomFileEntityDeserializer;
import pet.backend.restserver.repository.FileEntityRepository;

import javax.persistence.*;
import java.util.Date;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "USER_ENTITY")
public class User extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture")
    @JsonDeserialize(using = CustomFileEntityDeserializer.class)
    FileEntity picture;
    String name;
    String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    public User() {
    }

    public FileEntity getPicture() {
        return picture;
    }

    public void setPicture(FileEntity picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PrePersist
    protected void onCreate(){
    }
    @PreUpdate
    protected void onUpdate(){
    }


    @Override
    public String toString() {
        return "User{" +
                "picture=" + picture +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
