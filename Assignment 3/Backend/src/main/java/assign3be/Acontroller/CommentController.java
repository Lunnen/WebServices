package assign3be.Acontroller;

import assign3be.Bservice.CommentService;
import assign3be.Bservice.PostService;
import assign3be.Bservice.UserService;
import assign3be.Crepository.PostRepository;
import assign3be.Crepository.entity.CommentEntity;
import assign3be.Crepository.entity.PostEntity;
import assign3be.Crepository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepo;

    @PostMapping("/create/{postId}")
    public CommentEntity createComment(@RequestHeader("token") String token,
                                    @PathVariable long postId,
                                    @RequestBody String comment) {

        UserEntity user = userService.validate(token);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");

        PostEntity post = postService.getPostEntity(postId);
        if (post == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");

        CommentEntity newComment = commentService.createComment(user, post, comment);
        if (newComment == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Some error!");

        post.getComments().put(newComment, newComment.getId());
        postRepo.save(post);

        return newComment;
    }
    @DeleteMapping("/delete/{postId}/{commentId}")
    public String deleteComment(@RequestHeader("token") String token,
                                    @PathVariable long postId,
                                    @PathVariable long commentId,
                                    HttpServletResponse response) {

        UserEntity user = userService.validate(token);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");

        PostEntity post = postService.getPostEntity(postId);
        if (post == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");

        if (commentService.deleteComment(user, post, commentId) == 1) {
            return "Comment deleted";
        }
        response.setStatus(500);
        return "Something went wrong!";
    }

}
