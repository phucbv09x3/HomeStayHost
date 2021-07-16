package com.kujira.hosthomestay.data.model.response

class ErrorResponse : Throwable {
    var errorMessage: String? = null
    var code: Int = 0

    constructor(message: String?) {
        if (message == null) {
            this.errorMessage = ERROR
        } else {
            this.errorMessage = message
        }
    }

    constructor(message: String?, code: Int = 0) {
        this.code = code
        if (message == null) {
            this.errorMessage = ERROR
        } else {
            this.errorMessage = message
        }
    }

    override fun getLocalizedMessage(): String? {
        return errorMessage ?: ERROR
    }

    companion object {
        const val ERROR = "Có lỗi xảy ra"
        fun defaultError(message: String = ERROR): ErrorResponse {
            return ErrorResponse(message)
        }
    }

}