package assign3be.Crepository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Entity(name = "posts")
public class PostEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String description;

    @Column(nullable = false)
    private String creator;

    @ElementCollection
    @Column
    private Map<CommentEntity, Long> comments;

    @ElementCollection
    @Column
    private Map<UserEntity, Integer> votes;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createDate;


}

