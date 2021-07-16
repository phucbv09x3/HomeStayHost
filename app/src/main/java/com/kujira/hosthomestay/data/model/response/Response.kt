package open.hosthomestay.data.model.response

import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {
    @SerializedName("success")
    var status: Int? = null

    @SerializedName("data")
    var data: T? = null

    @SerializedName("errors")
    var error: ErrorResponse? = null

    val isSuccess: Boolean
        get() {
            return status == 1
        }

}