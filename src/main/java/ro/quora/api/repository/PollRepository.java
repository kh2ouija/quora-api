package ro.quora.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quora.api.model.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

}
