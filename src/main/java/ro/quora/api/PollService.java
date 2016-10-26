package ro.quora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quora.api.model.Poll;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    public Poll getById(long pollId) {
        return pollRepository.findOne(pollId);
    }

    public Poll save(Poll poll) {
        poll.getAnswers().forEach(answer -> answer.setPoll(poll));
        return pollRepository.save(poll);
    }


}
