package assign3be.Crepository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity(name="users")
public class UserEntity implements Serializable  {

    @Id
    @GeneratedValue
    private long id;

    @Column(length=50, unique = true, nullable = false)
    private String username;

    @Column(length=50, nullable = false)
    private String password;

    @Column
    private String token;

}

