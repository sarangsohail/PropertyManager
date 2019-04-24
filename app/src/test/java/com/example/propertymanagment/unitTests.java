package com.example.propertymanagment;


import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class loginTest {

    public static final String FAKE_STRING = "Login was successful";
    Boolean activeConnection = true;
    @Mock
    Context mMockContext;

    @Test
    public void isLoginSuccessful() {

        LoginActivity loginObjectTest = new LoginActivity();

        String validate = loginObjectTest.validateLogin("CgMaoRVSjHY4OGUHFZ5yiKg4PO83");

        assertThat(validate, is(FAKE_STRING));
    }

//   @Test
//   public boolean networkConnTest(){
//
//        video_streaming_activity video_streaming_activityTest
//                = new video_streaming_activity();
//
//       String result = video_streaming_activityTest.validateTest(true);
//       
//       assertThat(result, is(activeConnection));
//       
//   }
}