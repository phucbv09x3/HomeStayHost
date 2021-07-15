package open.your.eyes.miichibaseapp.data

import open.your.eyes.miichibaseapp.data.local.DataStoreHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val dataStoreHelper: DataStoreHelper,
) : DataManager {
    override suspend fun saveUserName(name: String) {
        dataStoreHelper.saveUserName(name)
    }

    override suspend fun getUserName(): String {
        return dataStoreHelper.getUserName()
    }
}