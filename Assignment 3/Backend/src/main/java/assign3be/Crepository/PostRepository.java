package assign3be.Crepository;

import assign3be.Crepository.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long> {

    PostEntity getPostEntityById(long id);
    List<PostEntity> findAll();
}
