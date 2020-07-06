package kalitero.software.noticiasargentinas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kalitero.software.noticiasargentinas.Controlador.Repositorio;
import kalitero.software.noticiasargentinas.Modelo.PaqueteNoticias;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

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
    }
}