package assign3be.Bservice;

import assign3be.Crepository.CommentRepository;
import assign3be.Crepository.PostRepository;
import assign3be.Crepository.entity.CommentEntity;
import assign3be.Crepository.entity.PostEntity;
import assign3be.Crepository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepo;

    @Autowired
    CommentRepository commentRepo;

    public CommentEntity createComment(UserEntity user, PostEntity post, String comment) {

        CommentEntity newComment = new CommentEntity();
        newComment.setCreator(user.getUsername());
        newComment.setPostId(post.getId());
        newComment.setComment(comment);

        commentRepo.save(newComment);
        return newComment;
    }

    public int deleteComment(UserEntity user, PostEntity post, long commentId) {

        /*
        SOLUTION can be found here :
        https://stackoverflow.com/questions/50235478/how-to-remove-a-key-from-a-hashmap-using-the-value/50235834#50235834
         */

        List<CommentEntity> keys = post.getComments()
                .keySet()
                .stream()
                .filter(entry -> entry.getId() == commentId)
                .collect(Collectors.toList());

        if(keys.size() != 0) {
            for (CommentEntity key : keys) {
                post.getComments().remove(key, commentId);
            }
        } else {
            return 0;
        }

        postRepo.save(post);
        commentRepo.deleteById(commentId);
        return 1;
    }
    public int deleteCommentsRelatedToPost(PostEntity post) {
        /*  How to remove a key from a hashmap using the value
            https://stackoverflow.com/a/50235834
        */
        Map<CommentEntity, Long> comments = post.getComments();
        List<CommentEntity> keys = post.getComments().keySet().stream()
                .filter(entry -> entry.getPostId() == post.getId())
                .collect(Collectors.toList());

        if(keys.size() != 0) {
            for (CommentEntity key : keys) {
                post.getComments().remove(key, key.getId());
                commentRepo.deleteById(key.getId());
            }
        } else {
            return 6;
        }
        return 1;
    }

}
