package nz.org.cacophony.birdmonitor;

import org.junit.Before;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static nz.org.cacophony.birdmonitor.HelperCode.nowNavigateRight;
import static nz.org.cacophony.birdmonitor.HelperCode.nowNavigateRightTimes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteRecordingsTest extends TestBaseStartingOnSetupScreen {

    @Before
    public void setUpForDeleteAllRecordings() throws InterruptedException {
        nowNavigateRightTimes(2);
        HelperCode.signInPrimaryTestUser();
        nowNavigateRight();
        HelperCode.registerPhone(prefs);

        nowNavigateRightTimes(2); // takes you to Test RecordAndSaveOnPhone

        // Need to put phone into offline mode so it doesn't try to upload the recording
        prefs.setInternetConnectionMode("offline");

        onView(withId(R.id.btnRecordNow)).perform(click());

        onView(withId(R.id.btnFinished)).perform(click());

        onView(withId(R.id.btnAdvanced)).perform(click());
    }

    @Test
    public void deleteRecordingsTest() {
        int numberOfRecordingsBeforeDelete = Util.getNumberOfRecordings(targetContext);
        assertTrue(numberOfRecordingsBeforeDelete > 0);

        onView(withId(R.id.btnDeleteAllRecordings)).perform(click());
        HelperCode.dismissDialogWithYes();

        int numberOfRecordingsAfterDelete = Util.getNumberOfRecordings(targetContext);
        assertEquals(0, numberOfRecordingsAfterDelete);

        onView(withId(R.id.tvMessagesManageRecordings)).check(matches(withText("All recordings on the phone have been deleted.")));
    }
}
