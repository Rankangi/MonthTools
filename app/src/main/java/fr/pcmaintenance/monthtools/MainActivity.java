package fr.pcmaintenance.monthtools;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.pcmaintenance.monthtools.Adapter.TransactionAdapter;
import fr.pcmaintenance.monthtools.Modele.Transaction;

public class MainActivity extends AppCompatActivity {

    private ListView listeAchat;
    private List<DocumentSnapshot> listDoc;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeAchat = findViewById(R.id.listeachat);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<String> month = new ArrayList<String>();
        month.add("Janvier");
        month.add("Février");
        month.add("Mars");
        month.add("Avril");
        month.add("Mai");
        month.add("Juin");
        month.add("Juillet");
        month.add("Août");
        month.add("Septembre");
        month.add("Octobre");
        month.add("Novembre");
        month.add("Décembre");

        TextView mois = findViewById(R.id.Mois);
        mois.setText(month.get(Calendar.getInstance().get(Calendar.MONTH)));


        db.collection("Transactions").document(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))).collection(month.get(Calendar.getInstance().get(Calendar.MONTH))).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    listDoc = task.getResult().getDocuments();
                    genererTransactions(listDoc);
                    TransactionAdapter adapter = new TransactionAdapter(MainActivity.this, transactions);
                    listeAchat.setAdapter(adapter);
                }
            }
        });


        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Date", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        user.put("User", "Océane");
        user.put("Montant", -50);
        // Add a new document with a generated ID
        db.collection("Transactions").document("2020").collection("Septembre").document("1")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + "1");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void genererTransactions(List<DocumentSnapshot> listDoc) {
        transactions = new ArrayList<Transaction>();
        for (DocumentSnapshot doc : listDoc) {
            transactions.add(new Transaction((long)doc.get("Date"), String.valueOf(doc.get("User")), String.valueOf(doc.get("Montant"))));
        }
    }

}