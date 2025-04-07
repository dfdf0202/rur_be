package kr.co.rur.core.api.support.response

import kr.co.rur.core.api.support.error.ErrorMessage
import kr.co.rur.core.api.support.error.ErrorType

class ApiResponse<S> private constructor(
    val result: kr.co.rur.core.api.support.response.ResultType,
    val data: S? = null,
    val totalCnt: Long? = null,
    val error: ErrorMessage? = null
) {
    companion object {
        fun success(): ApiResponse<Nothing> =
            ApiResponse(kr.co.rur.core.api.support.response.ResultType.SUCCESS)

        fun <S> success(data: S): ApiResponse<S> = ApiResponse(kr.co.rur.core.api.support.response.ResultType.SUCCESS, data)

        fun <S> success(data: S, totalCnt: Long): ApiResponse<S> = ApiResponse(kr.co.rur.core.api.support.response.ResultType.SUCCESS, data, totalCnt)

        fun error(error: ErrorType): ApiResponse<Nothing> = ApiResponse(kr.co.rur.core.api.support.response.ResultType.ERROR, error = ErrorMessage(error))

        fun error(error: ErrorType, errorData: Any): ApiResponse<Nothing> = ApiResponse(kr.co.rur.core.api.support.response.ResultType.ERROR, error = ErrorMessage(error, errorData))
    }
}