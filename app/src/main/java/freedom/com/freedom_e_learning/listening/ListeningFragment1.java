package freedom.com.freedom_e_learning.listening;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import freedom.com.freedom_e_learning.R;
import freedom.com.freedom_e_learning.model.listening.ListeningQuestion;


public class ListeningFragment1 extends Fragment {


    private ImageView btnPlay;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private SeekBar seekBar;
    private Runnable runnable;
    private Handler handler;
    private TextView time;

    private String audioUrl;
    private int save;

    ArrayList<ListeningQuestion> listeningQuestions;

    private RecyclerView recyclerView;
    private ListeningRecyclerViewAdapter listeningRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listening_fragment1, container, false);

        setControl(view);
        setEvents();
        Audiobar();
        return view;

    }

    public void setControl(View view) {


        mediaPlayer = new MediaPlayer();
        progressDialog = new ProgressDialog(getActivity());
        btnPlay = view.findViewById(R.id.btnPlay);
        seekBar = view.findViewById(R.id.seekBar);
        time = view.findViewById(R.id.Time);
        handler = new Handler();


        recyclerView = view.findViewById(R.id.listening_fragment1_recycler);
    }

    public void setEvents() {
        getListeningData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // Tạo adaper cho recyclerview cho mấy câu trắc nghiệm của listening
        listeningRecyclerViewAdapter = new ListeningRecyclerViewAdapter(getContext(), listeningQuestions);
        recyclerView.setAdapter(listeningRecyclerViewAdapter);
    }

    public void getListeningData() {
        listeningQuestions = (ArrayList<ListeningQuestion>) getArguments().getSerializable("Listening_questions");
        audioUrl = getArguments().getString("Listening_audio");
    }


    private void Audiobar() {
        mediaPlayer = MediaPlayer.create(this.getContext(), R.raw.dancin);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(mediaPlayer.getDuration());
                changeseekBar();

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {

                    mediaPlayer.seekTo(i);
                    //changeseekBar();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_24dp);
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play_circle_outline_24dp);
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_24dp);
                    changeseekBar();
                }

            }
        });
    }

    private void changeseekBar() {
        save = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(save);
        final String currentTimer = miliSecondsToTimer(mediaPlayer.getCurrentPosition());
        final String totalTimer = miliSecondsToTimer(mediaPlayer.getDuration());
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeseekBar();
                    time.setText(currentTimer + "/" + totalTimer);
                }
            };
            handler.postDelayed(runnable, 0);
        }
//        else {
//                    mediaPlayer.pause();
//                    runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            changeseekBar();
//                            time.setText(currentTimer + "/" + totalTimer);
//                        }
//                    };
//                    handler.postDelayed(runnable,0);
//                }


    }

    public String miliSecondsToTimer(long miliseconds) {
        String finalTimerString = "";
        String secondsString;

        int hours = (int) (miliseconds / (1000 * 60 * 60));
        int minutes = (int) (miliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((miliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        return finalTimerString;
    }
}
