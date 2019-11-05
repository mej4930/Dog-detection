package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NextActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Alarm> mArrayList;
    private AlarmAdapter mAdapter;
    private int count = -1;

    private PlayerView exoPlayerView;
    private SimpleExoPlayer player;

    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private Long playbackPosition = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);


        RecyclerView mRecyclerView = findViewById(R.id.recycler1);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();
        mAdapter = new AlarmAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        exoPlayerView = findViewById(R.id.exoPlayerView);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String email = intent.getExtras().getString("email");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String time1 = format1.format(date);
                count++;
                Alarm data = new Alarm(time1, "이상현상 발생");
                mArrayList.add(data);
                mAdapter.notifyDataSetChanged();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_title = nav_header_view.findViewById(R.id.nav_header_title);
        TextView nav_header_subtitle = nav_header_view.findViewById(R.id.nav_header_subtitle);

        nav_header_title.setText(name);
        nav_header_subtitle.setText(email);





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }


    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(this.getApplicationContext());
            exoPlayerView.setPlayer(player);
        }
        //String sample = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4";
        String sample = "https://video-weaver.sel03.hls.ttvnw.net/v1/playlist/CscE6mKElcItJoiOk3tJ7h3SyIbnAs0BXW9rGLTgiSDgc" +
                "AWUhb9btBinMc2RV8HgEbo9XZntIFqCKXGql5z15rS96CtXNnO18nqf39n_6ZPxg-nMQQsP8SYJ4rnOxn4KyXihaN-bJrLaFy8nKaeBq9z0" +
                "VVSKMUF1fTG5nJV4WfejzXpGgDb1HMmlvDCHHT-JMidvhsxPT2c7wGyNup1NGKR7FGi8z_HCYLMeSMS7KfHQJ8qFkbLb6oDbmMiCJDtdwvHp6" +
                "C8HGdr6LspaeAE-imgutRZ7Dh02tx7fDWxaucDySfSbQ9H615EuCWUqabrMuP8uZwE7fVHgNlF6ptYESWLrZURiLtyH_X2ti1GXlkj8zkp52Heia" +
                "No-U2eC_UIECsY6RMhC36Hiapz3hHdNcUMzVOL8yyGXAsdqJGiInGu_8Lf98R6KJ6xq6C0NN5sI4Lm_Og3x3i6jDXojVglaukuP3dud2dI8GoY4nk" +
                "r8qORyISOVYU1UHm4x1VMGsm3dBkQ8YiwP1oUQ91ZjJFDfFXrGAh5F17ogWP3OoKesnsvQL1g41nknROo-y-9cvfkMbgeJAqpmrmjxzl8No8" +
                "_1hAW5eyGepMxWoK4Q7vZYIDN9o5gBVXp1EsAtmu59FmcwyYUSf5qWkUGN_mIZjhl0S-tcimQlaUOsajtpNFrZK3QxO0-5FtMnDJbjydxiI" +
                "1dlm5mwlZrf0IeezBGdhR1BFDWFRGnDNHJ8bCDy-UwM-hi-R8uDWMAGpuxiuBgWdmMSKS8c6xDGjEhFnxIQQhqtudPz2DCH9qBHmSepbRoMihyn3Qc6L0wMFP5z.m3u8";
        MediaSource mediaSource = buildMediaSource(Uri.parse(sample));

        player.prepare(mediaSource, true, false);

        player.setPlayWhenReady(playWhenReady);
    }

    private MediaSource buildMediaSource(Uri uri) {
        String userAgent = Util.getUserAgent(this, "blackJin");

        if (uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")) {
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);

        } else if (uri.getLastPathSegment().contains("m3u8")) {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);

        } else {
            return new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(this, userAgent)).createMediaSource(uri);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();

            exoPlayerView.setPlayer(null);
            player.release();
            player = null;
        }
    }

}
