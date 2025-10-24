package com.example.todolist;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> taskList;
    private final ArrayList<String> taskIds;
    private final FirebaseFirestore db;

    public TaskAdapter(Context context, ArrayList<String> taskList, ArrayList<String> taskIds) {
        super(context, 0, taskList);
        this.context = context;
        this.taskList = taskList;
        this.taskIds = taskIds;
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);

        TextView taskTitle = convertView.findViewById(R.id.taskTitle);
        ImageButton editBtn = convertView.findViewById(R.id.editBtn);
        ImageButton deleteBtn = convertView.findViewById(R.id.deleteBtn);

        String title = taskList.get(position);
        String docId = taskIds.get(position);
        taskTitle.setText(title);

        // Edit button
        editBtn.setOnClickListener(v -> {
            EditText editText = new EditText(context);
            editText.setText(title);
            new android.app.AlertDialog.Builder(context)
                    .setTitle("Edit Task")
                    .setView(editText)
                    .setPositiveButton("Save", (d, w) -> {
                        String newTitle = editText.getText().toString().trim();
                        if (!newTitle.isEmpty()) {
                            db.collection("task_lists").document(docId)
                                    .update("title", newTitle)
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(context, "Task updated!", Toast.LENGTH_SHORT).show());
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Delete button
        deleteBtn.setOnClickListener(v -> {
            db.collection("task_lists").document(docId)
                    .delete()
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Task deleted!", Toast.LENGTH_SHORT).show());
        });

        return convertView;
    }
}
