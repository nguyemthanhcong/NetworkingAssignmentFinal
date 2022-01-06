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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;

import edu.vn.networkingassignmentfinal.R;
import edu.vn.networkingassignmentfinal.models.ViewAllModel;

public class NewProductFragment extends Fragment {

    EditText name, description,price, type, rating;
    ImageView imageView;
    Button btnAddImage, btnUpload;
    List<ViewAllModel> viewAllModelList;
    ViewAllModel viewAllModel;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    FirebaseAuth auth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_product, container, false);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        name = root.findViewById(R.id.add_product_name);
        description= root.findViewById(R.id.add_product_dec);
        price = root.findViewById(R.id.add_product_price);
        type = root.findViewById(R.id.add_product_type);
        rating = root.findViewById(R.id.add_product_rating);
        imageView = root.findViewById(R.id.new_image);
        btnAddImage = root.findViewById(R.id.btnChooseImage);
        btnUpload = root.findViewById(R.id.btnUpload);



        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadNewProduct();
            }
        });




        return root;
    }

    private void uploadNewProduct() {

        String strName = name.getText().toString();
        String strDec = description.getText().toString();
        int strPrice = Integer.parseInt(price.getText().toString());
        String strType = type.getText().toString();
        String strRating = rating.getText().toString();

        HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("name", strName);
        cartMap.put("description", strDec);
        cartMap.put("price", strPrice);
        cartMap.put("type", strType);
        cartMap.put("rating", strRating);

        firestore.collection("AllProduct").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null){
            Uri productUri = data.getData();
            imageView.setImageURI(productUri);
            Glide.with(getContext()).load(productUri).into(imageView);

            StorageReference reference = storage.getReference().child("new_product").child(FirebaseAuth.getInstance().getUid());

            reference.putFile(productUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        }



    }
}
