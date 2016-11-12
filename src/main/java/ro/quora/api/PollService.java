package ro.quora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.quora.api.model.Answer;
import ro.quora.api.model.Poll;
import ro.quora.api.repository.AnswerRepository;
import ro.quora.api.repository.PollRepository;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public Poll getById(long pollId) {
        return pollRepository.findOne(pollId);
    }

    public Poll save(Poll poll) {
        poll.getAnswers().forEach(answer -> answer.setPoll(poll));
        return pollRepository.save(poll);
    }

    @Transactional
    public Answer vote(Long answerId) {
        Answer answer = answerRepository.findOne(answerId);
        answer.setVotes(answer.getVotes() + 1);
        return answerRepository.save(answer);
    }


}
