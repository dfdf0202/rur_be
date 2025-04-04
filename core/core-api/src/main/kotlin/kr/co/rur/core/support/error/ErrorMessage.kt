package kr.co.rur.core.support.error

class ErrorMessage(
    val code: String,
    val message: String,
    val data: Any? = null
) {
    constructor(errorType: ErrorType) : this(
        code = errorType.code.name,
        message = errorType.message
    )

    constructor(errorType: ErrorType, data: Any) : this(
        code = errorType.code.name,
        message = errorType.message,
        data = data
    )
}