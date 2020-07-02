package com.zahirherz.fibtest;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText editTextInputNumber;
    private TextView textViewTotalTime;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInputNumber = findViewById(R.id.edit_text_input_number);
        textViewTotalTime = findViewById(R.id.text_view_main_total_time);

        handleInputNumber();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); // this is only if size doesn't change

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // update recyclerView
                adapter.setNotes(notes);
            }
        });
        noteViewModel.getTotalTime().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (noteViewModel.getTotalTime().getValue() != null) {
                    textViewTotalTime.setText(Double.toString(noteViewModel.getTotalTime().getValue()));
                }
            }
        });
    }

    private void handleInputNumber() {
        editTextInputNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    addNote();
                    // Close Keyboard
                    InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextInputNumber.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void addNote() {
        String input = editTextInputNumber.getText().toString();
        int inputNumber;
        if (input.trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            inputNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            inputNumber = 0;
            Toast.makeText(this, "Number too Large. 9 digit maximum", Toast.LENGTH_SHORT).show();
        }

        long startTime = System.nanoTime();
        int fibNumber = findFib(inputNumber);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        double seconds = (double) duration / 1_000_000_000.0;

        Note note = new Note(inputNumber, fibNumber, seconds, 4);
        noteViewModel.insert(note);
        editTextInputNumber.getText().clear();

        if (noteViewModel.getTotalTime().getValue() != null) {
            textViewTotalTime.setText(Double.toString(noteViewModel.getTotalTime().getValue()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                SummaryFragment summaryFragment = new SummaryFragment();
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, summaryFragment).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int findFib(int n) {
        int a = 0, b = 1, c;
        if (n == 0)
            return a;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

}