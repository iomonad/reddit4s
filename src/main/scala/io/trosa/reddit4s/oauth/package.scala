package io.trosa.reddit4s

import akka.http.scaladsl.model.{HttpRequest, Uri}

package object oauth
{
    /*
    ** Add parameters to the headers.
    */

    def _q_params(request: HttpRequest,
        queryParams: Seq[(String,String)]): HttpRequest =
            request.withUri(request.uri.withQuery(
                Uri.Query(request.uri.query() ++ queryParams: _*)))

    /*
    ** Sanitize headers before request
    */

    def _sanitize_params(params: Seq[(String,Any)]):
        Seq[(String,String)] =
    {
        var p = Seq[(String,String)]()
        params.foreach {
            case (k, Some(v)) => p :+= (k -> v.toString)
            case (k, None) =>
            case (k, v) => p :+= (k -> v.toString)
        }
        p
    }

    def _segmentize(request: HttpRequest, segment: String): HttpRequest =
        request.withUri(request.uri.withPath(request.uri.path + segment))
}
