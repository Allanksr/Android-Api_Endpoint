package allanksr.com.api_endpoint.ui

import allanksr.com.api_endpoint.MainCoroutineRule
import allanksr.com.api_endpoint.TestDispatchers
import allanksr.com.api_endpoint.common.Constants
import allanksr.com.api_endpoint.common.Status
import allanksr.com.api_endpoint.getOrAwaitValueTest
import allanksr.com.api_endpoint.repositories.FakePromotionalRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var testDispatchers: TestDispatchers

    @Before
    fun setup() {
        testDispatchers = TestDispatchers()
        viewModel = MainViewModel(
            FakePromotionalRepository(),
            testDispatchers
        )
    }

    @Test
    fun `insert promotional code with empty field, returns error`() {
        viewModel.insertPromotionalItem("")

        val value = viewModel.endPointApiResponseStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert promotional code with too long string, returns error`() {
        val string = buildString {
            for (i in 1..Constants.maxPromoLength + 1) {
                append(1)
            }
        }
        viewModel.insertPromotionalItem(string)

        val value = viewModel.endPointApiResponseStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert promotional code with valid input, returns success`() {
        viewModel.insertPromotionalItem("promo_code")

        val value = viewModel.endPointApiResponseStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `counter running, returns success`() {
        viewModel.runningTime.value = true
        viewModel.counter(System.currentTimeMillis(), 604800000)
        testDispatchers.testDispatcher.apply {
            advanceTimeBy(1000)
            runCurrent()
            val value = viewModel.counterTime.getOrAwaitValueTest()
            assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
        }

    }

    @Test
    fun `counter invalid value, returns error`() {
        viewModel.runningTime.value = true
        viewModel.counter(0, 0)
        testDispatchers.testDispatcher.apply {
            advanceTimeBy(1000)
            runCurrent()
            val value = viewModel.counterTime.getOrAwaitValueTest()
            assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        }

    }

}













