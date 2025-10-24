package com.example.todolist;

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
    ArrayList<String> taskIds = new ArrayList<>();
    TaskAdapter taskAdapter;
    String currentUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1️⃣ Initialize components
        taskInput = findViewById(R.id.taskInput);
        addBtn = findViewById(R.id.addTaskBtn);
        taskListView = findViewById(R.id.taskListView);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // 2️⃣ Authentication check
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        currentUID = mAuth.getCurrentUser().getUid();

        // 3️⃣ Set up custom adapter
        taskAdapter = new TaskAdapter(this, taskList, taskIds);
        taskListView.setAdapter(taskAdapter);

        // 4️⃣ Add new task
        addBtn.setOnClickListener(v -> {
            String task = taskInput.getText().toString().trim();
            if (!task.isEmpty()) {
                Map<String, Object> taskMap = new HashMap<>();
                taskMap.put("title", task);
                taskMap.put("ownerUID", currentUID);
                taskMap.put("allowedUsersUIDs", Collections.singletonList(currentUID));

                db.collection("task_lists")
                        .add(taskMap)
                        .addOnSuccessListener(doc -> {
                            taskInput.setText("");
                            Toast.makeText(MainActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(MainActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                taskInput.setError("Task cannot be empty");
            }
        });

        // 5️⃣ Fetch and update tasks in real-time
        db.collection("task_lists")
                .whereArrayContains("allowedUsersUIDs", currentUID)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) return;
                    taskList.clear();
                    taskIds.clear();
                    if (snapshots != null) {
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String title = doc.getString("title");
                            if (title != null) {
                                taskList.add(title);
                                taskIds.add(doc.getId());
                            }
                        }
                        taskAdapter.notifyDataSetChanged();
                    }
                });
    }
}
