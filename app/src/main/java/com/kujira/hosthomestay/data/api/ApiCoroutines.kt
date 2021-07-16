package open.hosthomestay.data.api

import open.hosthomestay.data.model.GitResponse
import open.hosthomestay.data.model.response.BaseResponse
import open.hosthomestay.ui.login.LoginViewModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by OpenYourEyes on 4/4/21
 */
interface ApiCoroutines {
    @GET("search/repositories")
    suspend fun getRepoGit(
        @Query("q") q: String = "trending",
        @Query("sort") stars: String = "stars"
    ): GitResponse

    @POST("api/login")
    fun login(): BaseResponse<LoginViewModel?>
}