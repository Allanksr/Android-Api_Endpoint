package allanksr.com.api_endpoint

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

@Suppress("DEPRECATION")
class TestDispatchers: DispatcherProvider {
    val testDispatcher = TestCoroutineDispatcher()
    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
}