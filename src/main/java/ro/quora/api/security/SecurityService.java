package ro.quora.api.security;

import ro.quora.api.model.Poll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Alex P on 10/26/16.
 */
public interface SecurityService {

    /**
     * Based on the poll's security configuration, return 'true' if the user is allowed to vote in the poll,
     * 'false' otherwise.
     */
    boolean allowVote(Poll poll, HttpServletRequest request);


    /**
     * Adds a cookie unique for this poll on the http session.
     */
    void registerCookie(Poll poll, HttpServletResponse response);


}
