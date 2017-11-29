package io.trosa.reddit4s

import akka.actor.ActorSystem
import io.trosa.reddit4s.models.OauthTokens
import java.io

import scala.concurrent.Future

package object oauth
{
    def oauthchallenge(clientId: String, clientSecret: String, code: String,
        redirectUri: Option[String] = None)
        (implicit system: ActorSystem): Future[OauthTokens] =
    {
        val _res_params: Seq[(String, io.Serializable)] = Seq(
            "client_id" -> clientId,
            "client_secret" -> clientSecret,
            "code" -> code,
            "redirect_uri" -> redirectUri
        )
        ???
    }
}
