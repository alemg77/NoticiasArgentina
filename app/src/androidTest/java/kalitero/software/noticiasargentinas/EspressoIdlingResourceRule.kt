package kalitero.software.noticiasargentinas

import androidx.test.espresso.IdlingRegistry
import kalitero.software.noticiasargentinas.util.EspressoIdlingTask
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class EspressoIdlingResourceRule : TestWatcher(){


    private val idlingResource = EspressoIdlingTask.countingIdlingResource

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }
}



