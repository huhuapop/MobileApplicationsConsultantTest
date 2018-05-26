package com.mobileapplicationsconsultanttest;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.SharedPreferences;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    private static final String FAKE_STRING_Title = "Harry Potter: Complete 8-Film Collection (DVD, 2011, 8-Disc Set)";
    @Test
    public void book_getTitle_isCorrect() throws Exception {
        //check getTitle function is correct
        Book book =new Book("Harry Potter: Complete 8-Film Collection (DVD, 2011, 8-Disc Set)","http://i.ebayimg.com/00/$(KGrHqV,!g0E6ZCwQ)wpBOuWbUNB,g~~_6.JPG?set_id=89040003C1","Bob McCabe");
        String result = book.getTitle();
        assertThat(result, is(FAKE_STRING_Title));
    }

    private static final String FAKE_STRING_ImageURL = "http://i.ebayimg.com/00/$(KGrHqV,!g0E6ZCwQ)wpBOuWbUNB,g~~_6.JPG?set_id=89040003C1";
    @Test
    public void book_getImageURL_isCorrect() throws Exception {
        //check getImageURL function is correct
        Book book =new Book("Harry Potter: Complete 8-Film Collection (DVD, 2011, 8-Disc Set)","http://i.ebayimg.com/00/$(KGrHqV,!g0E6ZCwQ)wpBOuWbUNB,g~~_6.JPG?set_id=89040003C1","Bob McCabe");
        String result = book.getImageURL();
        assertThat(result, is(FAKE_STRING_ImageURL));
    }


}