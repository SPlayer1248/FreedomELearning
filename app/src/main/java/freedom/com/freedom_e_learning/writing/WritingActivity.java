package freedom.com.freedom_e_learning.writing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import freedom.com.freedom_e_learning.Constants;
import freedom.com.freedom_e_learning.DatabaseService;
import freedom.com.freedom_e_learning.R;
import freedom.com.freedom_e_learning.model.writing.Writing;
import freedom.com.freedom_e_learning.reading.ReadingFragmentAdapter;

public class WritingActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Writing writing;

    DatabaseService databaseService = DatabaseService.getInstance();
    DatabaseReference readingReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        // Set up cái thanh toolbar đó
        mToolbar = findViewById(R.id.WritingToolbar);
        mToolbar.setTitle("Writing");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromFirebase();
    }

    public void getDataFromFirebase() {

        String topic = (String) getIntent().getStringExtra(String.valueOf(R.string.TOPIC_ID));
        readingReference = databaseService.getDatabase().child(Constants.TOPIC_NODE).child(topic).child(Constants.WRITTING_NODE);
        readingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Nhận data từ node reading lưu vào model reading
                writing = dataSnapshot.getValue(Writing.class);

                // Set mấy cái tab trong listening nè
                TabLayout tabLayout = (TabLayout) findViewById(R.id.reading_tab_layout);
                tabLayout.addTab(tabLayout.newTab().setText(""));
                tabLayout.addTab(tabLayout.newTab().setText(""));
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_description_24dp);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_adjust_24dp);

                // Set fragment nè
                final ViewPager viewPager = findViewById(R.id.reading_pager);
                WritingFragmentAdapter adapter = new WritingFragmentAdapter(getSupportFragmentManager(), writing);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
