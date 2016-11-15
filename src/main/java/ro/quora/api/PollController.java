package ro.quora.api;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.quora.api.model.Ballot;
import ro.quora.api.model.Poll;
import ro.quora.api.security.SecurityService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HashidHelper hashidHelper;


    @GetMapping(value = "/polls/{hash}")
    public Ballot getBallot(@PathVariable("hash") String hash, HttpServletRequest request) {
        Poll poll = getPollByHash(hash);
        return new Ballot(poll, !securityService.allowVote(poll, request));
    }

    @PostMapping(value = "/polls")
    public String createPoll(@RequestBody Poll poll) {
        poll = pollService.save(poll);
        return hashidHelper.encode(poll.getId());
    }

    @PostMapping(value = "/polls/{hash}/votes")
    public ResponseEntity submitVote(@PathVariable("hash") String hash,
                                     @RequestBody List<Long> answerIds,
                                     HttpServletRequest request, HttpServletResponse response) {
        Poll poll = getPollByHash(hash);
        if(!securityService.allowVote(poll, request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you already voted");
        }

        if (answerIds.size() > 1 && !poll.isMultipleChoice()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("multiple votes cast for single option poll");
        }

        poll = pollService.recordVotes(poll, answerIds);
        securityService.registerCookie(poll, response);

        return ResponseEntity.ok(new Ballot(poll, true));
    }


    private Poll getPollByHash(@PathVariable("hash") String hash) {
        Long id = hashidHelper.decode(hash);
        return pollService.getById(id);
    }

}
