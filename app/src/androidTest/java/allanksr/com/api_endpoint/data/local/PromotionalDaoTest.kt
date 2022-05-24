package allanksr.com.api_endpoint.data.local

import allanksr.com.api_endpoint.data.remote.local.PromotionalDao
import allanksr.com.api_endpoint.data.remote.local.PromotionalItem
import allanksr.com.api_endpoint.data.remote.local.PromotionalItemDatabase
import allanksr.com.api_endpoint.di.ApiModule
import allanksr.com.api_endpoint.getOrAwaitValueAndroidTest
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class PromotionalDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: PromotionalItemDatabase
    private lateinit var dao: PromotionalDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.promotionalDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPromotionalItem() = runTest {

        val promotionalItem = PromotionalItem("604800000", id = 1)
        dao.insertPromotionalItem(promotionalItem)

        val allShoppingItems = dao.observeAllPromotionalItems().getOrAwaitValueAndroidTest()

        assertThat(allShoppingItems).contains(promotionalItem)
    }

    @Test
    fun deletePromotionalItem() = runTest {
        val promotionalItem = PromotionalItem("604800000", id = 1)
        dao.insertPromotionalItem(promotionalItem)
        dao.deletePromotionalItem(promotionalItem)

        val allShoppingItems = dao.observeAllPromotionalItems().getOrAwaitValueAndroidTest()

        assertThat(allShoppingItems).doesNotContain(promotionalItem)
    }

}













