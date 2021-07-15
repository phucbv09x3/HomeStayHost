package open.hosthomestay.data.local

interface DataStoreHelper {
    suspend fun saveUserName(name: String)

    suspend fun getUserName(): String
}