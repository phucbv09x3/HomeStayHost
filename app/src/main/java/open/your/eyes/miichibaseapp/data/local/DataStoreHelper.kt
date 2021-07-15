package open.your.eyes.miichibaseapp.data.local

interface DataStoreHelper {
    suspend fun saveUserName(name: String)

    suspend fun getUserName(): String
}