package edu.vn.networkingassignmentfinal.activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.vn.networkingassignmentfinal.R;
import edu.vn.networkingassignmentfinal.models.ViewAllModel;

public class UpdateProductActivity extends AppCompatActivity {
    EditText name, price, description, rating, type;
    Button btnUpdate;
    ViewAllModel viewAllModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
//        final Object object = getIntent().getSerializableExtra("product");
//
//        if(object instanceof ViewAllModel){
//            viewAllModel = (ViewAllModel) object;
//        }

        name = findViewById(R.id.productName);
        price = findViewById(R.id.productPrice);
        description = findViewById(R.id.productDec);
        type = findViewById(R.id.productType);
        rating = findViewById(R.id.productRating);
        btnUpdate = findViewById(R.id.btnUpdate);

        name.setText(viewAllModel.getName());
        price.setText(viewAllModel.getPrice()+"");
        description.setText(viewAllModel.getDescription());
        type.setText(viewAllModel.getType());
        rating.setText(viewAllModel.getRating());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProduct();
            }
        });

    }

    private void updateProduct(){
        String strName = name.getText().toString();
        String strPrice = price.getText().toString();
        String strDec = price.getText().toString();
        String strType = type.getText().toString();
        String strRating = rating.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("name", strName);
        data.put("price", strPrice);
        data.put("description", strDec);
        data.put("type", strType);
        data.put("rating", strRating);


    }
}