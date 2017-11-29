package io.trosa.reddit4s

import io.trosa.reddit4s.types.RedditToken

/*
** Factory client object
*/

object RedditClient
{
    def apply(token: RedditToken): RedditClient =
        new RedditClient(token)
}

/*
** Factory class reference
*/

class RedditClient(token: RedditToken)
{

}
