package com.todolist.crespi.todolist.app.BLACKBOX_RandomDrivenTests;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.todolist.crespi.todolist.R;
import com.todolist.crespi.todolist.app.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BLACKBOX_RandomDateSearch {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void bLACKBOX_RandomDescriptionSearch() {

        int i = 0;

        while (i < 5000) {

            i += 1000;

            RandomString randomString = new RandomString(i);

            ViewInteraction actionMenuItemView = onView(
                    allOf(withId(R.id.sortBtn), withContentDescription("Show Options"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.action_bar),
                                            1),
                                    1),
                            isDisplayed()));
            actionMenuItemView.perform(click());

            Log.d("Test", "before spinner");

            ViewInteraction spinner = onView(
                    allOf(childAtPosition(
                            childAtPosition(
                                    withId(android.R.id.custom),
                                    0),
                            0),
                            isDisplayed()));
            spinner.perform(click());

            onView(withText(Matchers.containsString("date"))).inRoot(isPlatformPopup()).perform(click());

            ViewInteraction editText = onView(
                    allOf(childAtPosition(
                            childAtPosition(
                                    withId(android.R.id.custom),
                                    0),
                            1),
                            isDisplayed()));
            editText.perform(click());

            ViewInteraction editText2 = onView(
                    allOf(childAtPosition(
                            childAtPosition(
                                    withId(android.R.id.custom),
                                    0),
                            1),
                            isDisplayed()));
            editText2.perform(replaceText(randomString.nextString()), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(android.R.id.button1), withText("Add"),
                            childAtPosition(
                                    allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                            childAtPosition(
                                                    withClassName(is("android.widget.LinearLayout")),
                                                    3)),
                                    3),
                            isDisplayed()));
            appCompatButton.perform(click());
        }

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

