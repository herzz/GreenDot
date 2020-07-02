package com.zahirherz.fibtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private boolean showTimeToCalculate = false;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewInputNumber.setText(String.valueOf(currentNote.getInputNumber()));
        holder.textViewFibNumber.setText(String.valueOf(currentNote.getFibNumber()));
        holder.textViewTimeToCalculate.setText(String.valueOf(currentNote.getTimeToCalculate()));

        if (showTimeToCalculate) {
            holder.textViewTimeToCalculate.setVisibility(View.VISIBLE);
            holder.textViewFibNumber.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewTimeToCalculate.setVisibility(View.INVISIBLE);
            holder.textViewFibNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        // update list of notes in recyclerview
        notifyDataSetChanged();
    }

    public boolean showTimeToCalculate(boolean show) {
        showTimeToCalculate = show;
        return showTimeToCalculate;
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewInputNumber;
        private TextView textViewFibNumber;
        private TextView textViewTimeToCalculate;
        private TextView textiewTotalTime;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewInputNumber = itemView.findViewById(R.id.text_view_input_number);
            textViewFibNumber = itemView.findViewById(R.id.text_view_fib_number);
            textViewTimeToCalculate = itemView.findViewById(R.id.text_view_time_to_calculate);
            textiewTotalTime = itemView.findViewById(R.id.text_view_main_total_time);
        }
    }
}
