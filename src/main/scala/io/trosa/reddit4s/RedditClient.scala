package io.trosa.reddit4s

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, Uri}
import io.trosa.reddit4s.models.OauthTokens
import io.trosa.reddit4s.oauth.{_q_params, _sanitize_params, _segmentize}
import io.trosa.reddit4s.exceptions._
import io.trosa.reddit4s.types.RedditToken
import java.io

import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import play.api.libs.json.{Format, JsValue, Json}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.duration._

/*
** Factory client object
*/

object RedditClient
{
    /*
    ** Base request for connection reuse
    ** ~ The immutable model HTTP request model.
    */

    private val _base_request: HttpRequest =
        HttpRequest(uri = Uri(s"https://reddit.com/api"))

    /*
    ** Json case class formats for current namespace.
    */

    private[reddit4s] implicit val _format_token = Json.format[OauthTokens]

    /*
    ** Oauth exchange challenge
    */

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
        val r = apicall(_q_params(_segmentize(_base_request, "oauth.access"), _sanitize_params(_res_params)))
        r.map(_.as[OauthTokens])(system.dispatcher)
    }

    /*
    ** Http API call to decoded json.
    */

    private def apicall(request: HttpRequest)
        (implicit system: ActorSystem): Future[JsValue] =
    {
        implicit val mat: ActorMaterializer = ActorMaterializer()
        implicit val ec: ExecutionContextExecutor = system.dispatcher
        Http().singleRequest(request) flatMap
        {
            case response if response.status.intValue equals 200 =>
                response.entity.toStrict(10 seconds) map { identity =>
                    val _parsed_data  = Json.parse(identity.data.decodeString("UTF-8"))
                    (_parsed_data \ "ok").as[Boolean] match
                    {
                        case true => _parsed_data
                        case false => throw RedditApiError((_parsed_data \ "error").as[String])
                    }
                }
            case response =>
                response.entity.toStrict(10.seconds).map  { entity =>
                    throw ResponseInvalidException(response.status.intValue,
                        entity.data.decodeString("UTF-8"))
                }
        }
    }

    /*
    ** Decode Json
    */

    private def extract[T](jsFuture: Future[JsValue], field: String)
        (implicit system: ActorSystem, fmt: Format[T]): Future[T] =
            jsFuture.map(js => (js \ field).as[T])(system.dispatcher)

    def apply(token: RedditToken): RedditClient =
        new RedditClient(token)
}

/*
** Factory class reference
*/

class RedditClient(token: RedditToken)
{

}
