package edu.vn.networkingassignmentfinal.activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import edu.vn.networkingassignmentfinal.R;

public class ResetPasswordActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText edtEmail;
    TextView tvBack;
    Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtEmail = findViewById(R.id.edtEmail);
        tvBack = findViewById(R.id.tvBack);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        auth = FirebaseAuth.getInstance();

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(ResetPasswordActivity.this,
                                    "Please check your Email", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(ResetPasswordActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}