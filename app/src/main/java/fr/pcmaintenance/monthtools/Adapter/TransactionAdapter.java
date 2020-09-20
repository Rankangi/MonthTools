package fr.pcmaintenance.monthtools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.pcmaintenance.monthtools.Modele.Transaction;
import fr.pcmaintenance.monthtools.R;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    //tweets est la liste des models à afficher
    public TransactionAdapter(Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_transaction,parent, false);
        }

        TransactionViewHolder viewHolder = (TransactionViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TransactionViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Transaction transaction = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(transaction.getPseudo());
        viewHolder.price.setText(transaction.getPrice());

        return convertView;
    }

    private class TransactionViewHolder{
        public TextView pseudo;
        public TextView price;
    }
}
