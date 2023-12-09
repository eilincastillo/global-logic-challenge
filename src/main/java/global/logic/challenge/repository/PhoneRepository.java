package global.logic.challenge.repository;

import global.logic.challenge.model.Phone;
import global.logic.challenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository  extends JpaRepository<Phone, Long> {
    List<Phone> findByUser(User user);
}
