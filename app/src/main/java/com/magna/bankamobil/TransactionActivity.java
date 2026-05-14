// Enes Yasin Topal tarafından yapıldı.
package com.magna.bankamobil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {

    private EditText etAmount;
    private TextView tvCurrentBalance;
    private String transactionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionType = getIntent().getStringExtra("TRANSACTION_TYPE");

        etAmount = findViewById(R.id.etAmount);
        tvCurrentBalance = findViewById(R.id.tvCurrentBalance);
        TextView tvTitle = findViewById(R.id.tvTitle);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        updateBalance();

        switch (transactionType) {
            case "DEPOSIT":
                tvTitle.setText("Para Yatır");
                btnConfirm.setText("Yatır");
                break;
            case "WITHDRAW":
                tvTitle.setText("Para Çek");
                btnConfirm.setText("Çek");
                break;
            case "TRANSFER":
                tvTitle.setText("Havale Yap");
                btnConfirm.setText("Gönder");
                break;
        }

        btnConfirm.setOnClickListener(v -> processTransaction());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void updateBalance() {
        tvCurrentBalance.setText(String.format(Locale.getDefault(), "%.2f TL", BankData.balance));
    }

    private void processTransaction() {
        String amountStr = etAmount.getText().toString().trim();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Lütfen tutar girin", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Geçersiz tutar", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amount <= 0) {
            Toast.makeText(this, "Tutar sıfırdan büyük olmalıdır", Toast.LENGTH_SHORT).show();
            return;
        }

        if (transactionType.equals("DEPOSIT")) {
            BankData.balance += amount;
            Toast.makeText(this, String.format(Locale.getDefault(), "%.2f TL yatırıldı", amount), Toast.LENGTH_SHORT).show();
        } else {
            if (amount > BankData.balance) {
                Toast.makeText(this, "Yetersiz bakiye!", Toast.LENGTH_SHORT).show();
                return;
            }
            BankData.balance -= amount;
            String msg = transactionType.equals("WITHDRAW") ? "çekildi" : "havale edildi";
            Toast.makeText(this, String.format(Locale.getDefault(), "%.2f TL " + msg, amount), Toast.LENGTH_SHORT).show();
        }

        updateBalance();
        etAmount.setText("");
    }
}
