package assign3be.Acontroller;

import assign3be.Bservice.PostService;
import assign3be.Bservice.UserService;
import assign3be.Crepository.entity.UserEntity;
import assign3be.datahandlers.DTO.PostDTO;
import assign3be.datahandlers.InputPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public @ResponseBody
    Collection<PostDTO> getAll() {

        Collection<PostDTO> result = postService.getPosts();
        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.OK, "Selected table is empty......");
        }

        return result;
    }

    @PostMapping("/add")
    public String createPost(
            @RequestHeader("token") String token,
            @RequestBody InputPost inputPost,
            HttpServletResponse response) {

        if(inputPost.getTitle() == null ||
        inputPost.getDescription() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input! Try again!");
        }

        int result = postService.createPost(token, inputPost);

        switch (result) {
            case 0:
                response.setStatus(404);
                return "No such user!";
            case 1:
                return "Post has been created";
            case 2:
                return "Description too long! 3000+ symbols!";
            default:
                response.setStatus(500);
                return "Something went wrong.";
        }
    }

    @GetMapping("/search/{inputSearch}")
    public Collection<PostDTO> search(
            @PathVariable("inputSearch") String inputSearch) {

        Collection<PostDTO> getResults = postService.getMatchingPostOrTitle(inputSearch);

        if(getResults.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nothing found");
        }
        return getResults;
    }

    @PatchMapping("/update/{id}")
    public String updatePost(
            @RequestHeader("token") String token,
            @PathVariable("id") long id,
            @RequestBody String description,
            HttpServletResponse response) {

        int result = postService.updatePost(
                token,
                id,
                description);

        switch(result){
            case 0: {
                response.setStatus(401);
                return "Access denied! Not your post!";
            }
            case 1: return "Post updated";
            case 2: {
                response.setStatus(404);
                return "Post with that ID doesn't exist!";
            }
            case 3: {
                response.setStatus(405);
                return "Nothing to change! Same values as before!";
            }
            default: {
                response.setStatus(500);
                return "Something went wrong!";
            }
        }
    }

    @GetMapping("/getMyVotedPosts/{id}")
    public int updatePost(
            @RequestHeader("token") String token,
            @PathVariable("id") int id) {

       return postService.getMyVotedPosts(token, id);
    }


    @DeleteMapping("/delete/{deletePost}")
    public String deletePost(
            @RequestHeader("token") String token,
            @PathVariable long deletePost,
            HttpServletResponse response
    ){
        switch(postService.deletePost(token, deletePost)){
            case 0: {
                response.setStatus(401);
                return "Access denied! Not your post!";
            }
            case 1: return "Post deleted";
            case 2: {
                response.setStatus(404);
                return "Post with that ID doesn't exist!";
            }
            case 3: {
                response.setStatus(401);
                return "User doesn't exist!";
            }
            default: {
                response.setStatus(500);
                return "Something went wrong!";
            }
        }
    }

    @PutMapping("/vote/{postId}")
    public String voteOnPost(@RequestHeader("token") String token,
                             @PathVariable long postId,
                             @RequestParam(value = "vote", defaultValue = "0") String vote,
                             HttpServletResponse response) {

        UserEntity user = userService.validate(token);
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");

        int result = postService.voteOnPost(user, postId, vote);
        switch (result) {
            case 3:
                response.setStatus(404);
                return "Post doesn't exist";
            case 2:
                response.setStatus(400);
                return "Invalid vote type!";
            case 1:
                return "Vote removed from post";
            case 0:
                return "Voted " +
                        (vote.equals("-1") ? "down" : "up") +" on post";
            default:
                response.setStatus(500);
                return "Something went wrong.";
        }
    }

}

