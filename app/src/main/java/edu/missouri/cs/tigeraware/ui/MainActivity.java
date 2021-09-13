package edu.missouri.cs.tigeraware.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.storage.database.AppDatabase;
import org.researchstack.backbone.storage.database.sqlite.DatabaseHelper;
import org.researchstack.backbone.storage.file.EncryptionProvider;
import org.researchstack.backbone.storage.file.FileAccess;
import org.researchstack.backbone.storage.file.PinCodeConfig;
import org.researchstack.backbone.storage.file.SimpleFileAccess;
import org.researchstack.backbone.storage.file.UnencryptedProvider;
import org.researchstack.backbone.ui.PinCodeActivity;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.adapter.MainPagerAdapter;
import edu.missouri.cs.tigeraware.model.Survey;
import edu.missouri.cs.tigeraware.util.SurveyUtil;

/**
 * Main Activity
 *
 * @author Siyang Liu
 */
public class MainActivity extends PinCodeActivity {

    private ViewPager mViewPager;
    // ViewPager's adapter and relevant stuff
//    private PagerAdapter mAdapter;
    List<SurveyFragment> mTabs;
    // Indicate the selected page index
    private int currentIndex = 0;

    // Bottom tabs
    private List<LinearLayout> mBottomTabs;

    private DatabaseReference mDatabase;

    // Surveys
    private List<Survey> mSurveys;

    // get方法
    public List<Survey> getSurveys() {
        return mSurveys;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);

        initUI();

        retrieveData();

        // 这个真的是个坑，不加上，就要崩
        prepareResearchStackAuth();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call the to make sure the first tab is selected
        resetTabs();
    }

    private void prepareResearchStackAuth() {
        // Customize your pin code preferences
        PinCodeConfig pinCodeConfig = new PinCodeConfig(); // default pin config (4-digit, 1 min lockout)

        // Customize encryption preferences
        EncryptionProvider encryptionProvider = new UnencryptedProvider(); // No pin, no encryption

        // If you have special file handling needs, implement FileAccess
        FileAccess fileAccess = new SimpleFileAccess();

        // If you have your own custom database, implement AppDatabase
        AppDatabase database = new DatabaseHelper(this,
                DatabaseHelper.DEFAULT_NAME,
                null,
                DatabaseHelper.DEFAULT_VERSION);

        StorageAccess.getInstance().init(pinCodeConfig, encryptionProvider, fileAccess, database);
    }

    @Override
    public void onDataReady() {
        super.onDataReady();
    }


    // 保留
    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentIndex = position;
            resetTabs();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void initUI() {
        // Get all the tabs
        mBottomTabs = new ArrayList<>();
        LinearLayout surveyTab = (LinearLayout) findViewById(R.id.surveys_tab_item);
        surveyTab.setOnTouchListener(mTabTouchListener);
        LinearLayout userSurveyTab = (LinearLayout) findViewById(R.id.user_surveys_tab_item);
        userSurveyTab.setOnTouchListener(mTabTouchListener);
        mBottomTabs.add(surveyTab);
        mBottomTabs.add(userSurveyTab);

        mTabs = new ArrayList<>();
        MainSurveysTab surveysTab = new MainSurveysTab();
        MainUserSurveysTab userSurveysTab = new MainUserSurveysTab();
        mTabs.add(surveysTab);
        mTabs.add(userSurveysTab);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), mTabs);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(pageChangeListener);
//        // Call the to make sure the first tab is selected
//        resetTabs();
    }

    private OnTouchListener mTabTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                // Get the selected Index
                int newIndex = -1;
                for (int i = 0; i < mBottomTabs.size(); i++) {
                    if (view == mBottomTabs.get(i)) {
                        newIndex = i;
                        break;
                    }
                }
                // If find the new index
                if (newIndex != -1 && newIndex != currentIndex) {
                    currentIndex = newIndex;
                    mViewPager.setCurrentItem(newIndex, false);
                    resetTabs();
                }
            }

            return false;
        }
    };


    /**
     * Reset all the tab buttons and texts to normal states
     */
    private void resetTabs() {
        ImageButton tabImageButton = null;
        TextView tabTextView = null;
        for (int i = 0; i < mBottomTabs.size(); i++) {
            tabImageButton = (ImageButton) mBottomTabs.get(i).findViewById(R.id.tabImageButton);
            tabTextView = (TextView) mBottomTabs.get(i).findViewById(R.id.tabTextView);
            if (i == currentIndex) {
                tabImageButton.setPressed(true);
                tabTextView.setTextColor(getResources().getColor(R.color.colorNormalText));
            } else {
                tabImageButton.setPressed(false);
                tabTextView.setTextColor(getResources().getColor(R.color.colorTabNormal));
            }
        }
    }

    /**
     * Retrieve data from firebase for surveys, then update ListView
     */
    void retrieveData() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("blueprints").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSurveys = SurveyUtil.parseSurveys(dataSnapshot);
                for (SurveyFragment fragment : mTabs) {
                    fragment.onDataChange(mSurveys);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
