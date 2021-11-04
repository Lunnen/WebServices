package assign3be.Crepository;

import assign3be.Crepository.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity getUserEntityByUsernameEquals(String username);
    UserEntity getUserEntityByTokenEquals(String token);
}
