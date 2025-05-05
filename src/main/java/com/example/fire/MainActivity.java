package com.example.fire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonAdd;
    private ListView listViewUsers;

    private FirebaseFirestore db;
    private CollectionReference usersRef;

    private ArrayList<String> userList = new ArrayList<>();
    private ArrayList<String> userIds = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewUsers = findViewById(R.id.listViewUsers);

        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listViewUsers.setAdapter(adapter);

        // Criar usuário
        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (!name.isEmpty()) {
                User user = new User(name);
                usersRef.add(user)
                        .addOnSuccessListener(documentReference -> {
                            editTextName.setText("");
                            Toast.makeText(this, "Usuário adicionado", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> Log.w("Firestore", "Erro ao adicionar", e));
            }
        });

        // Ler usuários em tempo real
        usersRef.addSnapshotListener((snapshots, error) -> {
            if (error != null) {
                Log.w("Firestore", "Erro ao escutar mudanças.", error);
                return;
            }

            userList.clear();
            userIds.clear();

            if (snapshots != null) {
                for (QueryDocumentSnapshot doc : snapshots) {
                    User user = doc.toObject(User.class);
                    user.setId(doc.getId());  // Atribui o ID ao usuário
                    userList.add(user.getName());
                    userIds.add(user.getId());
                }
            }

            adapter.notifyDataSetChanged();
        });

        // Atualizar nome do usuário ao clicar
        listViewUsers.setOnItemClickListener((parent, view, position, id) -> {
            String docId = userIds.get(position);
            String currentName = userList.get(position);

            // Dialogo para editar nome
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Editar Nome");

            final EditText input = new EditText(this);
            input.setText(currentName);
            builder.setView(input);

            builder.setPositiveButton("Atualizar", (dialog, which) -> {
                String newName = input.getText().toString().trim();
                if (!newName.isEmpty()) {
                    usersRef.document(docId)
                            .update("name", newName)
                            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Nome atualizado", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Log.w("Firestore", "Erro ao atualizar", e));
                }
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        // Deletar usuário ao pressionar
        listViewUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            String docId = userIds.get(position);

            new AlertDialog.Builder(this)
                    .setTitle("Remover Usuário")
                    .setMessage("Deseja remover este usuário?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        usersRef.document(docId)
                                .delete()
                                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Usuário deletado", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Log.w("Firestore", "Erro ao deletar", e));
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });
    }
}
