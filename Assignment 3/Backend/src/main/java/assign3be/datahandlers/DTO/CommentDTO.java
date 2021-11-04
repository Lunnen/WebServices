package assign3be.datahandlers.DTO;

import assign3be.Crepository.entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private static final String MY_TIME_ZONE="Europe/Stockholm";

    private long id;
    private String comment;
    private String creator;

    @JsonProperty("create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone=MY_TIME_ZONE)
    private Date createDate;

    public CommentDTO(CommentEntity input) {
        this.id = input.getId();
        this.comment = input.getComment();
        this.creator = input.getCreator();
        this.createDate = input.getCreateDate();
    }
}
