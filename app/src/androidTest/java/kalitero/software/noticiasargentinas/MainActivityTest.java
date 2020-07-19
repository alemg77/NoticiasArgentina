package kalitero.software.noticiasargentinas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kalitero.software.noticiasargentinas.Controlador.Repositorio;
import kalitero.software.noticiasargentinas.modelo.PaqueteNoticias;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent


    @Rule
    public EspressoIdlingResourceRule espressoIdlingResoureRule;

    @Test
    public void Test1() {
        Intent intent = new Intent();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PaqueteNoticias paqueteNoticias = Repositorio.getInstancia(targetContext).traerTodoRoom();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaqueteNoticias.class.toString(), paqueteNoticias);
        intent.putExtras(bundle);
        activityRule.launchActivity(intent);

        onView(allOf(withId(R.id.FragmentRecyclerViewNoticiasCompactas), isDisplayed()))
                .perform(scrollToPosition(10));

        onView(allOf(withId(R.id.FragmentRecyclerViewNoticiasCompactas), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));

        onView(withId(R.id.activityMainContenedorFragment)).perform(swipeLeft());
        onView(withId(R.id.activityMainContenedorFragment)).perform(swipeLeft());
        onView(withId(R.id.activityMainContenedorFragment)).perform(swipeLeft());
        onView(withId(R.id.activityMainContenedorFragment)).perform(swipeUp());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.MainActivityToolbar),
                                        0),
                                1),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Noticia Barriales"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        /*
        onView(withId(R.id.activityMainContenedorFragment)).perform(swipeUp());
        onView(withId(R.id.activityMainContenedorFragment)).perform(swipeUp());
        onView(withId(R.id.activityMainContenedorFragment)).perform(swipeUp());

         */

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}