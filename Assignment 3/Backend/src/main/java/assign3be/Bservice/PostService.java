package assign3be.Bservice;

import assign3be.Crepository.PostRepository;
import assign3be.Crepository.entity.PostEntity;
import assign3be.Crepository.entity.UserEntity;
import assign3be.datahandlers.DTO.CommentDTO;
import assign3be.datahandlers.DTO.PostDTO;
import assign3be.datahandlers.InputPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepo;

    @Autowired
    CommentService commentService;

    public int createPost(@RequestHeader("token") String token,
                          @RequestBody InputPost inputPost) {

        UserEntity user = userService.validate(token);

        if (user == null) {
            return 0;
        }

        if(inputPost.getDescription().length() >= 3000){
            return 2;
        }

        inputPost.setCreator(user.getUsername());
        PostEntity newPost = new PostEntity();

        newPost.setTitle(inputPost.getTitle());
        newPost.setDescription(inputPost.getDescription());
        newPost.setCreator(user.getUsername());
        postRepo.save(newPost);
        return 1;

    }

    public Collection<PostDTO> getPosts() {

        return postRepo.findAll()
                .stream()
                .map(post -> new PostDTO(
                        post, getVoteValue(post),
                        (post.getComments()
                                .keySet()
                                .stream()
                                .map(CommentDTO::new)
                                .collect(Collectors.toList()
                        ))
                ))
                .collect( Collectors.toList() );
    }

    public int deletePost(String token, long id) {

        PostEntity postCheck = postRepo.getPostEntityById(id);
        if (postCheck == null) {
            return 2;
        }

        UserEntity userCheck = userService.validate(token);
        if (userCheck == null) {
            return 3; // User doesn't exist
        }
        if (!postCheck.getCreator().equals(userCheck.getUsername())) {
            return 0; // User has no rights
        }

        if(postCheck.getComments().size() != 0
                && commentService.deleteCommentsRelatedToPost(postCheck) == 6){

            return 4; //Couldn't delete
        }

        postRepo.delete(postCheck);
        return 1;// Success
    }

    public int updatePost(String token, long id, String description) {

        PostEntity postCheck = postRepo.getPostEntityById(id);
        if (postCheck == null) {
            return 2;
        }

        UserEntity userCheck = userService.validate(token);
        if (userCheck == null) {
            return 3; // User doesn't exist
        }
        if (!postCheck.getCreator().equals(userCheck.getUsername())) {
            return 0; // User has no rights
        }

        if (description != null && !description.equals(postCheck.getDescription())) {
            postCheck.setDescription(description);
            postRepo.save(postCheck);
            return 1; // Success
        }

        return 3;
    }

    public Collection<PostDTO> getMatchingPostOrTitle(String input) {

        return postRepo.findAll()
                .stream()
                .filter(x -> x.getDescription().toLowerCase().matches(".*" + input + ".*")
                        || x.getTitle().toLowerCase().matches(".*" + input + ".*"))
                .map(post -> new PostDTO(
                        post, getVoteValue(post),
                        (post.getComments()
                                .keySet()
                                .stream()
                                .map(CommentDTO::new)
                                .collect(Collectors.toList()
                                ))
                ))
                .collect( Collectors.toList() );
    }

    public int voteOnPost(UserEntity user, long postId, String vote) {
        PostEntity post = postRepo.getPostEntityById(postId);
        int voteValue = Integer.parseInt(vote);

        if (post == null)
            return 3;

        if (!vote.equals("-1") && !vote.equals("1"))
            return 2;

        if (post.getVotes().containsKey(user)) {
            boolean sameVote = post.getVotes().get(user) == voteValue;
            if (sameVote) {
                // If same value, remove existing. Else input new
                post.getVotes().remove(user);
                postRepo.save(post);
                return 1;
            } else {
                post.getVotes().put(user, voteValue);
                postRepo.save(post);
                return 0;
            }
        } else {
            post.getVotes().put(user, voteValue);
            postRepo.save(post);
            return 0;
        }
    }

    public int getVoteValue(PostEntity post) {
        return getUpVotes(post) - getDownVotes(post);
    }

    public int getUpVotes(PostEntity post) {
        return (int) post.getVotes().values().stream().filter(v -> v == 1).count();
    }

    public int getDownVotes(PostEntity post) {
        return (int) post.getVotes().values().stream().filter(v -> v == -1).count();
    }

    public int getMyVotedPosts(String token, int id) {

        UserEntity user = userService.validate(token);
        PostEntity chosenPost = postRepo.getPostEntityById(id);

        if(user == null || chosenPost == null){
            return 0;
        }

        if(chosenPost.getVotes().containsKey(user)){
            return chosenPost.getVotes().get(user);
        }
        return 0;
    }
    public PostEntity getPostEntity(long postId) {
        return postRepo.getPostEntityById(postId);
    }
}

