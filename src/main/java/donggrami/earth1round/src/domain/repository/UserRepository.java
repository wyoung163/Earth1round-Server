package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByPersonalId(Long PersonalId);
    User findByPersonalId(Long personalId);
    @Query("SELECT u.personalId FROM User u WHERE u.user_id = :user_id")
    Long getPersonalIdByUserId(@Param("user_id") Long user_id);
}
