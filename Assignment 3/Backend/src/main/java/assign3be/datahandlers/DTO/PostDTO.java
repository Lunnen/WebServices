package assign3be.datahandlers.DTO;

import assign3be.Crepository.entity.CommentEntity;
import assign3be.Crepository.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class PostDTO implements Serializable {
    private static final String MY_TIME_ZONE="Europe/Stockholm";

    private long id;
    private String title;
    private String description;
    private String creator;

    @JsonProperty("vote_value")
    private int voteValue;

    @JsonProperty("create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone=MY_TIME_ZONE)
    private Date createDate;

    private List<CommentDTO> comments;

    public PostDTO(PostEntity post, int voteValue, List<CommentDTO> comments) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.creator = post.getCreator();
        this.voteValue = voteValue;
        this.createDate = post.getCreateDate();
        this.comments = comments;
    }
}

