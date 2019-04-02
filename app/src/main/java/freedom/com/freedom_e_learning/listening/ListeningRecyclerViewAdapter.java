package freedom.com.freedom_e_learning.listening;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import freedom.com.freedom_e_learning.R;
import freedom.com.freedom_e_learning.model.listening.ListeningQuestion;

public class ListeningRecyclerViewAdapter extends RecyclerView.Adapter<ListeningRecyclerViewAdapter.RecyclerViewHolder> {


    Context context;
    ArrayList<ListeningQuestion> listeningQuestions;

    public ListeningRecyclerViewAdapter(Context context, ArrayList<ListeningQuestion> listeningQuestions) {
        this.context = context;
        this.listeningQuestions = listeningQuestions;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_listening_fragment1, viewGroup, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(v);
        return recyclerViewHolder;
    }

    // Set questions
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int position) {
        recyclerViewHolder.txtQuestion.setText(listeningQuestions.get(position).getQuestion());
        recyclerViewHolder.radAnswer1.setText(listeningQuestions.get(position).getAnswers().get(0));
        recyclerViewHolder.radAnswer2.setText(listeningQuestions.get(position).getAnswers().get(1));
        recyclerViewHolder.radAnswer3.setText(listeningQuestions.get(position).getAnswers().get(2));
        recyclerViewHolder.radAnswer4.setText(listeningQuestions.get(position).getAnswers().get(3));
    }

    @Override
    public int getItemCount() {
        return listeningQuestions.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuestion;
        private RadioButton radAnswer1;
        private RadioButton radAnswer2;
        private RadioButton radAnswer3;
        private RadioButton radAnswer4;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtListeningQuestion);
            radAnswer1 = itemView.findViewById(R.id.radListeningAnswer1);
            radAnswer2 = itemView.findViewById(R.id.radListeningAnswer2);
            radAnswer3 = itemView.findViewById(R.id.radListeningAnswer3);
            radAnswer4 = itemView.findViewById(R.id.radListeningAnswer4);

        }
    }
}