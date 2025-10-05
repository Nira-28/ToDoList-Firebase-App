package com.example.todolist;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import com.example.todolist.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    EditText taskInput;
    Button addBtn;
    ListView taskListView;
    ArrayList<String> taskList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String currentUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1️⃣ Always-safe initializations
        taskInput = findViewById(R.id.taskInput);
        addBtn = findViewById(R.id.addTaskBtn);
        taskListView = findViewById(R.id.taskListView);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // 2️⃣ Critical authentication check
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return; // Prevents rest of onCreate from executing
        }

        // 3️⃣ Code that depends on a logged-in user
        currentUID = mAuth.getCurrentUser().getUid();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        addBtn.setOnClickListener(v -> {
            String task = taskInput.getText().toString().trim();
            if (!task.isEmpty()) {
                Map<String, Object> taskMap = new HashMap<>();
                taskMap.put("title", task);
                taskMap.put("ownerUID", currentUID);
                taskMap.put("allowedUsersUIDs", Collections.singletonList(currentUID));

                db.collection("task_lists")
                        .add(taskMap)
                        .addOnSuccessListener(doc -> taskInput.setText(""))
                        .addOnFailureListener(e ->
                                Toast.makeText(MainActivity.this, "Failed to add task: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        db.collection("task_lists")
                .whereArrayContains("allowedUsersUIDs", currentUID)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) return; // Handle Firestore errors here
                    taskList.clear();
                    if (snapshots != null) {
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String title = doc.getString("title");
                            if (title != null) taskList.add(title);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
