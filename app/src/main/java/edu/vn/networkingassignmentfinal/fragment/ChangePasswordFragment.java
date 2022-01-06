package edu.vn.networkingassignmentfinal.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.vn.networkingassignmentfinal.R;
import edu.vn.networkingassignmentfinal.activiti.LoginActivity;
import edu.vn.networkingassignmentfinal.models.UserModel;

public class ChangePasswordFragment extends Fragment {

    EditText edtNewPass;
    Button changePassword;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        edtNewPass = root.findViewById(R.id.newPassword);
        changePassword = root.findViewById(R.id.change_password_button);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }

            }
        };

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && !edtNewPass.getText().toString().trim().equals("")){
                    if (edtNewPass.getText().toString().trim().length() < 6){
                        edtNewPass.setError("Password must be at least 6 characters");
                    }else {
                        user.updatePassword(edtNewPass.getText().toString().trim()).addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getActivity(), "Change Password Success", Toast.LENGTH_SHORT).show();
                                            signOut();
                                        }else{
                                            Toast.makeText(getActivity(), "Failed",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                } else if
                (edtNewPass.getText().toString().trim().equals("")) {
                    edtNewPass.setError("Password is empty");
                }
            }
        });

        return root;
    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
