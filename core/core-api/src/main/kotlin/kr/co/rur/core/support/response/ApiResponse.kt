package kr.co.rur.core.support.response

import kr.co.rur.core.support.error.ErrorMessage
import kr.co.rur.core.support.error.ErrorType

class ApiResponse<S> private constructor(
    val result: ResultType,
    val data: S? = null,
    val totalCnt: Long? = null,
    val error: ErrorMessage? = null
) {
    companion object {
        fun success(): ApiResponse<Nothing> =
            ApiResponse(ResultType.SUCCESS)

        fun <S> success(data: S): ApiResponse<S> = ApiResponse(ResultType.SUCCESS, data)

        fun <S> success(data: S, totalCnt: Long): ApiResponse<S> = ApiResponse(ResultType.SUCCESS, data, totalCnt)

        fun error(error: ErrorType): ApiResponse<Nothing> = ApiResponse(ResultType.ERROR, error = ErrorMessage(error))

        fun error(error: ErrorType, errorData: Any): ApiResponse<Nothing> = ApiResponse(ResultType.ERROR, error = ErrorMessage(error, errorData))
    }
}