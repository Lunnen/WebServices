package assign3be.Crepository;

import assign3be.Crepository.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    CommentEntity getCommentEntitiesById(long id);

    CommentEntity deleteById(long id);
}
