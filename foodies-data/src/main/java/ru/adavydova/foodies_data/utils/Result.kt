package ru.adavydova.foodies_data.utils

import android.os.Build
import androidx.annotation.RequiresExtension
import ru.adavydova.foodies_data.R


sealed class Result<T> {
    class Error<T>(val error: String) : Result<T>()
    class Success<T>(val data: T) : Result<T>()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun Throwable.handleThrowable(): String {
    return when (this) {
        is RemoteServiceHttpError -> {
            return when {
                isServerError -> R.string.remote_service_http_error.toString() + "(${httpStatusCode.code})"
                else -> R.string.remote_client_http_error.toString() + "(${httpStatusCode.code})"
            }
        }

        is NetworkError -> R.string.network_exception.toString()
        is UnexpectedError -> R.string.unexpected_error.toString()
        else -> R.string.unknown_error.toString()

    }
}

class UnexpectedError : Error() {

    override fun toString(): String {
        return "UnexpectedError"
    }
}

/**
 * Represents an error in which the server could not be reached.
 */
class NetworkError : Error() {

    override fun toString(): String {
        return "NetworkError"
    }
}

class RemoteServiceHttpError(val response: retrofit2.HttpException) : Error() {

    val httpStatusCode =
        HttpStatusCode.entries.firstOrNull { statusCode -> statusCode.code == response.code() }
            ?: HttpStatusCode.Unknown

    val isClientError: Boolean
        get() = response.code() in 400..499

    val isServerError: Boolean
        get() = response.code() in 500..599

    override fun toString(): String {
        return "RemoteServiceHttpError" +
                "\ncode: ${response.code()} (${httpStatusCode.name})" +
                "\nmessage: ${super.message}"
    }
}

enum class HttpStatusCode(val code: Int) {

    Unknown(-1),

    // Client Errors
    BadRequest(400),
    Unauthorized(401),
    PaymentRequired(402),
    Forbidden(403),
    NotFound(404),
    MethodNotAllowed(405),
    NotAcceptable(406),
    ProxyAuthenticationRequired(407),
    RequestTimeout(408),
    Conflict(409),
    Gone(410),
    LengthRequired(411),
    PreconditionFailed(412),
    PayloadTooLarge(413),
    UriTooLong(414),
    UnsupportedMediaType(415),
    RangeNotSatisfiable(416),
    ExpectationFailed(417),
    ImATeapot(418),
    MisdirectedRequest(421),
    UnprocessableEntity(422),
    Locked(423),
    FailedDependency(424),
    UpgradeRequired(426),
    PreconditionRequired(428),
    TooManyRequests(429),
    RequestHeaderFieldsTooLarge(431),
    UnavailableForLegalReasons(451),

    // Server Errors
    InternalServerError(500),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    HttpVersionNotSupported(505),
    VariantAlsoNegates(506),
    InsufficientStorage(507),
    LoopDetected(508),
    NotExtended(510),
    NetworkAuthenticationRequired(511);
}
