package io.trosa.reddit4s

package object models
{
    /*
    ** Header token models for
    ** Oauth2 protocol
    */

    case class OauthTokens(
        access_token: String,
        scope: String
    )
}
