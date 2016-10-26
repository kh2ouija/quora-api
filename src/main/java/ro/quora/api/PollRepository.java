package ro.quora.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.quora.api.model.Poll;

@Repository
public interface PollRepository extends CrudRepository<Poll, Long> {}
