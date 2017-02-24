package com.ljdc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import com.ljdc.database.DBHelper;

public class AndroidTestCaseTest extends AndroidTestCase {

    Context context;

    public void setUp() throws Exception {
        super.setUp();

        context = new MockContext();

        setContext(context);

//        assertNotNull(context);

    }

    // Fake failed test
    public void testSomething()  {
//        assertEquals(false, true);
        System.out.println("ggg");
        System.out.println(context);

        SQLiteDatabase db = new DBHelper(context).getReadableDatabase();

        db.execSQL("INSERT INTO user (nickname) VALUES (\"jasonzou\");");
        db.execSQL("INSERT INTO user (nickname) VALUES (\"安抚\");");
        db.execSQL("INSERT INTO user (nickname) VALUES (\"阿斯蒂芬\");");
    }
}