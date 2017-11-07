package edu.usc.cesr.ema_uas;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SettingsUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void timeSeparation() throws Exception {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

//        start.set(Calendar.YEAR, start.get(Calendar.YEAR) - 1);
//        end.set(Calendar.YEAR, start.get(Calendar.YEAR) + 1);
//
//        List<Survey> surveys = Settings.buildSurveysPro(start, end);
//        for(int i = 1; i < surveys.size(); i++){
//            Survey olderSurvey = surveys.get(i -1);
//            Survey newerSurvey = surveys.get(i);
//            long diff =(newerSurvey.getDate().getTimeInMillis() - olderSurvey.getDate().getTimeInMillis())/ (60 * 1000);
//            Log.d("TT", "SettingsUnitTest: " + diff);
//            if(diff < Constants.MIN_TIME_BETWEEN_SURVEYS_PRO) fail("Min diff not reached at " + i);
//        }
    }
}