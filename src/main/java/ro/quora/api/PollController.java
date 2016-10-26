package ro.quora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quora.api.model.Poll;

@RestController
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private HashidHelper hashidHelper;

    @GetMapping(value = "/polls/{hash}")
    public Poll getPoll(@PathVariable("hash") String hash) {
        long id = hashidHelper.decode(hash);
        return pollService.getById(id);
    }

    @PostMapping(value = "/polls")
    public String createPoll(@RequestBody Poll poll) {
        poll = pollService.save(poll);
        return hashidHelper.encode(poll.getId());
    }

}
