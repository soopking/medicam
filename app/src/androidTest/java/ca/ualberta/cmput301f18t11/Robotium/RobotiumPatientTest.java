package ca.ualberta.cmput301f18t11.Robotium;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.activities.CreateUserActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.LoginActivity;

import ca.ualberta.cmput301f18t11.medicam.activities.PatientProblemActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.PatientRecordActivity;
import ca.ualberta.cmput301f18t11.medicam.activities.createProblemActivity;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RobotiumPatientTest {
    private String test_uid;
    private String randomTestIndex = String.valueOf(ThreadLocalRandom.current().nextInt(0,101));

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);
    private Solo solo;

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                loginActivityActivityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddProblem() throws Exception {
        test_uid = "testing_patient_user";
        String title = "Can't feel foot";
        String desc = "Since my amputation I can't feel my foot.";


        solo.unlockScreen();

        //sign in a new user
        solo.enterText(0,test_uid);
        solo.clickOnView(solo.getView(R.id.sign_in_button));
        //If the login was successful the next screen should be the PatientProblemActivity
        solo.assertCurrentActivity("Expect PatientProblemActivity", PatientProblemActivity.class);


        //Create a problem, P1
        solo.clickOnView(solo.getView(R.id.floatingActionButton));
        solo.assertCurrentActivity("Expect CreateProblemActivity", createProblemActivity.class);
        solo.enterText(0, title+randomTestIndex);
        solo.enterText(1, desc+randomTestIndex);
        solo.clickOnView(solo.getView(R.id.createProblemButton));

        //Find recently created problem
        boolean foundProblem = solo.searchText("title");
        assertTrue("Problem created successfully",foundProblem);
    }

//    @Test
//    public void testEditProblem() throws Exception {
//        test_uid = "testing_patient_user";
//
//        solo.unlockScreen();
//
//        //sign in a new user
//        solo.enterText(0,test_uid);
//        solo.clickOnView(solo.getView(R.id.sign_in_button));
//        //If the login was successful the next screen should be the PatientProblemActivity
//        solo.assertCurrentActivity("Expect PatientProblemActivity", PatientProblemActivity.class);
//
//        //Edit first problem in the list
//        solo.clickInList(1);
//        solo.assertCurrentActivity("Expect PatientRecordActivity", PatientRecordActivity.class);
//        solo.clickOnView(solo.getView(R.id.edit));
//
//
//    }
//
//    @Test
//    public void testDeleteProblem() throws Exception {}
}
