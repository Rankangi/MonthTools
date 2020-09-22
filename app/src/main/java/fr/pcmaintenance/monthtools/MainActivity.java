package fr.pcmaintenance.monthtools;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.pcmaintenance.monthtools.Adapter.TransactionAdapter;
import fr.pcmaintenance.monthtools.Modele.Transaction;

public class MainActivity extends AppCompatActivity {

    private ListView listeAchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeAchat = findViewById(R.id.listeachat);

        List<Transaction> transactions = genererTransactions();

        TransactionAdapter adapter = new TransactionAdapter(MainActivity.this, transactions);
        listeAchat.setAdapter(adapter);

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


        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1814);

// Add a new document with a generated ID
        db.collection("Transactions")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
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

    private List<Transaction> genererTransactions() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction(8,"Océane","50"));
        transactions.add(new Transaction(9,"Mathieu","-50"));
        return transactions;
    }

}