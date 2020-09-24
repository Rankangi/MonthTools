package fr.pcmaintenance.monthtools;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

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
    private AlertDialog alert;
    private FirebaseFirestore db;
    private List<String> month;
    private int transactionID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeAchat = findViewById(R.id.listeachat);

        db = FirebaseFirestore.getInstance();

        month = new ArrayList<String>();
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.dialog_add_transaction, null))
                        .setPositiveButton("Sauvegarder", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                TextView oceane = alert.findViewById(R.id.oceane);
                                TextView mathieu = alert.findViewById(R.id.mathieu);
                                TextView montant = alert.findViewById(R.id.montant);
                                Switch sw = alert.findViewById(R.id.switchUser);
                                Map<String, Object> newTrans = new HashMap<>();
                                newTrans.put("Date", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                                if (sw.isChecked()){
                                    transactions.add(new Transaction(Calendar.getInstance().get(Calendar.DAY_OF_MONTH), String.valueOf(mathieu.getText()), String.valueOf(montant.getText())));
                                    newTrans.put("User", String.valueOf(mathieu.getText()));
                                    newTrans.put("Montant", String.valueOf(montant.getText()));
                                }else{
                                    transactions.add(new Transaction(Calendar.getInstance().get(Calendar.DAY_OF_MONTH), String.valueOf(oceane.getText()), String.valueOf(montant.getText())));
                                    newTrans.put("User", String.valueOf(oceane.getText()));
                                    newTrans.put("Montant", String.valueOf(montant.getText()));
                                }

                                db.collection("Transactions").document(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))).collection(month.get(Calendar.getInstance().get(Calendar.MONTH))).document(String.valueOf(transactionID))
                                        .set(newTrans)
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
                                transactionID++;
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.cancel();
                            }
                        });
                alert = builder.create();
                alert.show();
                Switch sw = alert.findViewById(R.id.switchUser);
                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        TextView oceane = alert.findViewById(R.id.oceane);
                        TextView mathieu = alert.findViewById(R.id.mathieu);
                        if (!b){
                            oceane.setTextColor(Color.parseColor("#FF00DDFF"));
                            mathieu.setTextColor(Color.parseColor("#828282"));
                        }else{
                            oceane.setTextColor(Color.parseColor("#828282"));
                            mathieu.setTextColor(Color.parseColor("#FF00DDFF"));
                        }
                    }
                });
            }
        });


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
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void genererTransactions(List<DocumentSnapshot> listDoc) {
        transactions = new ArrayList<Transaction>();
        for (DocumentSnapshot doc : listDoc) {
            transactions.add(new Transaction((long)doc.get("Date"), String.valueOf(doc.get("User")), String.valueOf(doc.get("Montant"))));
            transactionID++;
        }
    }

}