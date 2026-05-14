// Enes Yasin Topal tarafından yapıldı.
package com.magna.bankamobil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBalance = findViewById(R.id.tvBalance);

        findViewById(R.id.btnDeposit).setOnClickListener(v -> startTransaction("DEPOSIT"));
        findViewById(R.id.btnWithdraw).setOnClickListener(v -> startTransaction("WITHDRAW"));
        findViewById(R.id.btnTransfer).setOnClickListener(v -> startTransaction("TRANSFER"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvBalance.setText(String.format(Locale.getDefault(), "%.2f TL", BankData.balance));
    }

    private void startTransaction(String type) {
        Intent intent = new Intent(this, TransactionActivity.class);
        intent.putExtra("TRANSACTION_TYPE", type);
        startActivity(intent);
    }
}