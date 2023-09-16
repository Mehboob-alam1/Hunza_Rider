package com.mehboob.hunzarider.activities;

import static com.mehboob.hunzarider.utils.HideKeyboard.hideKeyboard;
import static com.mehboob.hunzarider.utils.Utils.showSnackBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityDocumentBinding;
import com.mehboob.hunzarider.utils.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentActivity extends AppCompatActivity {
  private   ActivityDocumentBinding binding;

    private static final int pickImage = 1;

    private static final int pickNicFront = 2;
    private static final int pickNicBack = 21;
    private static final int pickVehicalPaper = 3;
    private static final int pickDrivingLicence = 4;

    private DatabaseReference mRef;
    private StorageReference storageReference;
    private Uri photoUri, nicFrontUri, nicBackUri, vehiclePaperUri, drivingLicenseUri;
    private SharedPref sharedPref;
    private ArrayList<String> urlStrings;
    private ArrayList<Uri> list = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDocumentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        sharedPref = new SharedPref(this);
        storageReference = FirebaseStorage.getInstance().getReference(Constants.DOCUMENTS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mRef= FirebaseDatabase.getInstance().getReference(Constants.RIDER).child(Constants.DOCUMENTS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        urlStrings = new ArrayList<>();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Uploading Documents");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait......");
        setImgs();

        binding.btnUPloadImage.setOnClickListener(v -> ImagePicker.with(DocumentActivity.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(pickImage));


        binding.btnUlploadNic.setOnClickListener(v -> ImagePicker.with(DocumentActivity.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(pickNicFront));
        binding.btnUlploadNicBack.setOnClickListener(v -> ImagePicker.with(DocumentActivity.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(pickNicBack));


        binding.btnVehicalPaper.setOnClickListener(v -> ImagePicker.with(DocumentActivity.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(pickVehicalPaper));


        binding.btnDrivingLicence.setOnClickListener(v -> ImagePicker.with(DocumentActivity.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(pickDrivingLicence));


        binding.btnback.setOnClickListener(v -> finish());

        binding.linearLayoutPayment.setOnClickListener(v -> {
            if (sharedPref.fetchMyPhoto() == " ") {
                Toast.makeText(DocumentActivity.this, "Your Image not added ", Toast.LENGTH_SHORT).show();
            } else if (sharedPref.fetchNicFront() == " ") {
                Toast.makeText(DocumentActivity.this, "Add your cnic front", Toast.LENGTH_SHORT).show();
            } else if (sharedPref.fetchNicBack() == " ") {
                Toast.makeText(DocumentActivity.this, "Add your cnic back", Toast.LENGTH_SHORT).show();
            } else if (sharedPref.fetchVehiclePaper() == " ") {
                Toast.makeText(DocumentActivity.this, "Vehicle papers are not added", Toast.LENGTH_SHORT).show();
            } else if (sharedPref.fetchDrivingLicense() == " " ) {
                Toast.makeText(DocumentActivity.this, "Driving license is not added", Toast.LENGTH_SHORT).show();
            } else {
                hideKeyboard(this);
                uploadDocuments(Uri.parse(sharedPref.fetchMyPhoto()), Uri.parse(sharedPref.fetchNicFront()), Uri.parse(sharedPref.fetchNicBack()), Uri.parse(sharedPref.fetchVehiclePaper()), Uri.parse(sharedPref.fetchDrivingLicense()));
            }

        });
    }

    private void uploadDocuments(Uri photoUri, Uri nicFrontUri, Uri nicBackUri, Uri vehiclePaperUri, Uri drivingLicenseUri) {

        list.add(photoUri);
        list.add(nicFrontUri);
        list.add(nicBackUri);
        list.add(vehiclePaperUri);
        list.add(drivingLicenseUri);
        mProgressDialog.show();
        for (int i = 0; i < list.size(); i++) {
            Uri IndividualImage = list.get(i);
            final StorageReference ImageName = storageReference.child("Images" + IndividualImage.getLastPathSegment());

            ImageName.putFile(IndividualImage).addOnSuccessListener(
                    taskSnapshot -> ImageName.getDownloadUrl().addOnSuccessListener(
                            uri -> {
                                urlStrings.add(String.valueOf(uri));
                                if (urlStrings.size() == list.size()) {
                                    storeLink(urlStrings);
                                }

                            }
                    )
            ).addOnFailureListener(e -> {
                mProgressDialog.dismiss();
            });
        }

    }

    private void storeLink(ArrayList<String> urlStrings) {

        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            hashMap.put("ImgLink" + i, urlStrings.get(i));

        }

        mRef.setValue(hashMap)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                showSnackBar(DocumentActivity.this,"Documents added");
                                sharedPref.setDocumentsCompleted(true);
                                addDocumentStatus(FirebaseAuth.getInstance().getCurrentUser().getUid());


                            }else{
                                showSnackBar(DocumentActivity.this,"Something went wrong");
                            }
                        }
                ).addOnFailureListener(e ->showSnackBar(DocumentActivity.this,e.getLocalizedMessage()));
        mProgressDialog.dismiss();


        list.clear();
    }

    @SuppressLint("SuspiciousIndentation")
    private void setImgs() {
        if (sharedPref.fetchMyPhoto() != " ")
            binding.uploadedImage.setImageURI(Uri.parse(sharedPref.fetchMyPhoto()));
        if (sharedPref.fetchNicFront() != " ")
            binding.uploadNic.setImageURI(Uri.parse(sharedPref.fetchNicFront()));
        if (sharedPref.fetchVehiclePaper() != " ")
            binding.vehicalsPaper.setImageURI(Uri.parse(sharedPref.fetchVehiclePaper()));
        if (sharedPref.fetchNicBack() != " ")
            binding.uploadNicBack.setImageURI(Uri.parse(sharedPref.fetchNicBack()));
        if (sharedPref.fetchDrivingLicense() != " ")
            binding.drivingLicence.setImageURI(Uri.parse(sharedPref.fetchDrivingLicense()));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && data != null) {
            photoUri = data.getData();
            sharedPref.saveMyPhotoUri(photoUri);
            binding.uploadedImage.setImageURI(Uri.parse(sharedPref.fetchMyPhoto()));

        }
        if (requestCode == 2 && data != null) {
            nicFrontUri = data.getData();
            sharedPref.saveNicFrontUri(nicFrontUri);
            binding.uploadNic.setImageURI(Uri.parse(sharedPref.fetchNicFront()));
        }
        if (requestCode == 3 && data != null) {
            vehiclePaperUri = data.getData();
            sharedPref.saveVehiclePaperUri(vehiclePaperUri);
            binding.vehicalsPaper.setImageURI(Uri.parse(sharedPref.fetchVehiclePaper()));
        }

        if (requestCode == pickNicBack && data != null) {
            nicBackUri = data.getData();
            sharedPref.saveNicBackUri(nicBackUri);
            binding.uploadNicBack.setImageURI(Uri.parse(sharedPref.fetchNicBack()));
        }

        if (requestCode == pickDrivingLicence && data != null) {
            drivingLicenseUri = data.getData();
            sharedPref.saveDrivingLicenseUri(drivingLicenseUri);
            binding.drivingLicence.setImageURI(Uri.parse(sharedPref.fetchDrivingLicense()));
        }


    }
    private void addDocumentStatus(String userId) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.RIDER);
        databaseReference.child("status")
                .child(userId)
                .child("documentsCompleted")
                .setValue(true).addOnCompleteListener(task -> {

                    if (task.isComplete() && task.isSuccessful()){
                        startActivity(new Intent(DocumentActivity.this,PaymentActivity.class));
                    }else{
                        showSnackBar(DocumentActivity.this,"Check internet connection");
                    }
                }).addOnFailureListener(e -> {
                    showSnackBar(DocumentActivity.this,e.getLocalizedMessage());
                });

    }
}