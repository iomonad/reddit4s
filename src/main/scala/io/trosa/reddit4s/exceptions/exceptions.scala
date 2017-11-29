package io.trosa.reddit4s.exceptions

object exceptions
{
    /*
    ** Custom exceptions for
    ** invalid response types
    */

    case class ResponseInvalidException(
        status_code: Int,
        body: String
    ) extends Exception(s"Wrong response code: $status_code, body: $body")

    /*
    ** Generic API error exception
    ** with string code.
    */

    case class RedditApiError(
        _code: String
    ) extends Exception(_code)
}
