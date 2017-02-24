package com.ljdc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import com.ljdc.app.App;
import com.ljdc.database.DBHelper;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends InstrumentationTestCase {


    Context context;

    public void setUp() throws Exception {
        super.setUp();

        context = new MockContext();

        assertNotNull(context);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }


    public void testSomething() {

        assertEquals(false, true);
    }

    //According to Zainodis annotation only for legacy and not valid with gradle>1.1:
    //@Test
    public void testAddEntry() {
        // Here i have my new database wich is not connected to the standard database of the App
    }

    public void test() {
//        context = new MockContext().getApplicationContext();
//        context = getTestContext();
        context = App.getCtx();
        System.out.println("ggg");
        System.out.println(context);

        SQLiteDatabase db = new DBHelper(context).getReadableDatabase();

        db.execSQL("INSERT INTO user (nickname) VALUES (\"jasonzou\");");
        db.execSQL("INSERT INTO user (nickname) VALUES (\"安抚\");");
        db.execSQL("INSERT INTO user (nickname) VALUES (\"阿斯蒂芬\");");
    }

    /*public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }*/

    public void testQuery() throws Exception {

/*        List users = DBHelper.getHelper(getInstrumentation().getContext()).getDao(UserServer.class).queryForAll();
        Log.d("ExampleUnitTest", "users.size():" + users.size());*/
    }
/*    public void testInsert() throws Exception {

        SQLiteDatabase db = DBHelper.getHelper(getInstrumentation().getContext()).getReadableDatabase();
        db.execSQL("insert into user (nickname) values(\"jasonzou\")");
        db.execSQL("insert into user (nickname) values(\"jasonzou\")");
        db.execSQL("insert into user (nickname) values(\"jasonzou\")");
    }*/


 /*   public void testDao() throws Exception {
        WordLibServer word = new WordLibServer();
        word.word = "word:";
        Lib1EnglishGrand4CoreServer lib1 = new Lib1EnglishGrand4CoreServer();
        lib1.wordLibServer = word;
        DBHelper.getHelper(getInstrumentation().getContext()).getDao(WordLibServer.class).create(word);
        DBHelper.getHelper(getInstrumentation().getContext()).getDao(Lib1EnglishGrand4CoreServer.class).create(lib1);

    }*/


}