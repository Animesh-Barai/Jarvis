package databases.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jarvis.TodoDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FirebaseDataAdd {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    CollectionReference refTodo, refWallet,  refJournal, refReminder;
    DocumentReference refUser;

    public FirebaseDataAdd(FirebaseAuth mAuth){
        this.mAuth = mAuth.getInstance();
       setup();
       setupCacheSize();
        initializeRef();
    }

    public void setup() {
        // [START get_firestore_instance]
        db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }

    public void setupCacheSize() {
        // [START fs_setup_cache]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
        // [END fs_setup_cache]
    }
    private void initializeRef(){
        refTodo = db.collection("user").document(mAuth.getUid().toString()).collection("todo");
        refReminder = db.collection("user").document(mAuth.getUid().toString()).collection("reminder");
        refJournal = db.collection("user").document(mAuth.getUid().toString()).collection("journal");
        refWallet = db.collection("user").document(mAuth.getUid().toString()).collection("wallet");
        refUser = db.collection("user").document(mAuth.getUid().toString());
    }

    public void addTodoInFireBaseRecent(List <TodoDetails> tasks){

        Iterator i = tasks.iterator();
        int increment = 1;
        while(i.hasNext()){
          //  Map<String, Object> task = new HashMap<>();
            //task.put(("task" + increment).toString(), i.next());

            //List<TodoDetails> task = Arrays.asList();
            refTodo.document("recent").collection("todo")
                    .document(("task" + increment))
                    .set(i.next()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("addTodoFireBase", "Successfull");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("addTodoFireBase", "Failed");
                }
            });
            increment++;
        }
    }

    public void addTodoInFireBaseOld(ArrayList<TodoDetails> tasks){
        Map<String, Object> task = new HashMap<>();
        Iterator i = tasks.iterator();
        int increment = 1;
        while(i.hasNext()) {
            //task.put(.toString(), i.next());
            refTodo.document("old").collection("todo")
                    .document(("task" + increment))
                    .set(i.next()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("addTodoFireBase", "Successfull");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("addTodoFireBase", "Failed");
                }
            });
            increment++;
        }
    }

// There must be a photo // We may have to create a class for user?

    public void addUserDetails(String name){
        Map<String , Object> data = new HashMap<>();
        data.put("name ", name);
        refUser.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("addUserFireBase", "Successfull");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("addUserFireBase", "Faild");
            }
        });
    }


    public void addReminderInFireBaseCurrent(ArrayList<TodoDetails> tasks){
        Map<String, Object> task = new HashMap<>();
        Iterator i = tasks.iterator();
        int increment = 1;
        while(i.hasNext()) {
           // task.put(("reminder" + increment).toString(), i.next());

            refReminder.document("reminder" + increment).set(i.next())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("addReminderFireBase", "Successfull");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("addreminderFireBase", "Failed");
                }
            });
            increment++;
        }
    }

}