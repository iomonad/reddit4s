package io.trosa.reddit4s

import akka.http.scaladsl.model.{HttpRequest, Uri}
import io.trosa.reddit4s.types.RedditToken

/*
** Factory client object
*/

object RedditClient
{
    /*
    ** Base request for connection reuse
    */

    private val baserequest: HttpRequest =
        HttpRequest(uri = Uri(s"https://reddit.com/api"))

    def apply(token: RedditToken): RedditClient =
        new RedditClient(token)
}

/*
** Factory class reference
*/

class RedditClient(token: RedditToken)
{

}
