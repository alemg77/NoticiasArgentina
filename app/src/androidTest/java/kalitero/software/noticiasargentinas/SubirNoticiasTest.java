package kalitero.software.noticiasargentinas;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Test;
import org.junit.runner.RunWith;

import kalitero.software.noticiasargentinas.Vista.SubirNoticias.SubirNoticias;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)
public class SubirNoticiasTest {

    @Test
    public void TestSubirNoticias() {

        // Declaro que actividad voy a iniciar
        ActivityTestRule<SubirNoticias> activityRule = new ActivityTestRule<>(
                SubirNoticias.class,
                true,     // initialTouchMode
                false);   // launchActivity. False to customize the intent

        // Inicio la actividad
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        activityRule.launchActivity(intent);


        // Verificacion que se ve un objeto
        onView(withId(R.id.fragmentLayoutSubirNoticia)).check(matches(isDisplayed()));

        // Ve
        onView(withId(R.id.fragmentDetalleBarrialtextView)).check(matches(isDisplayed()));

        onView(withId(R.id.editTextTextoNoticia)).
                perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        onView(withId(R.id.editTextTituloNoticia)).
                perform(typeText("123456789q"));


    }
}