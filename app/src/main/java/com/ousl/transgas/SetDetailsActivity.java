package com.ousl.transgas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ousl.transgas.model.UserModel;

import java.util.ArrayList;

public class SetDetailsActivity extends AppCompatActivity {
    TextView userId;
    Button confirmBtn;
    EditText rName, rAddress, rPhone, cardNumber, ExpireDate, CVC;
    RadioGroup radioGroup;
    RadioButton radioBtn;
    String currentUserDetails;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_details);
        getSupportActionBar().hide();

        radioGroup = findViewById(R.id.radioGroup);

        // Parse Database Data
        userId = findViewById(R.id.userIdText);
        rName = findViewById(R.id.rNameEditText);
        rAddress = findViewById(R.id.rAddressEditText);
        rPhone = findViewById(R.id.rPhoneEditText);
        cardNumber = findViewById(R.id.cardNumberEditText);
        ExpireDate = findViewById(R.id.expirationEditText);
        CVC = findViewById(R.id.cvcEditText);

        // Receive Pushed Database Data
        currentUserDetails = getIntent().getStringExtra("current_user_data");
        DB = new DBHelper(this);

        // Function Place Order
        confirmBtn = findViewById(R.id.confirmButton);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usedId = userId.getText().toString();
                String usedName = rName.getText().toString();
                String usedAddress = rAddress.getText().toString();
                String usedPhone = rPhone.getText().toString();
                String orderMethod = getOrderMethod(); // Get the selected order method
                String usedCardNumber = cardNumber.getText().toString();
                String usedExpireDate = ExpireDate.getText().toString();
                String usedCVC = CVC.getText().toString();

                // Function Validate Inputs
                boolean checkInputNormal = validateInputsNormal(usedName, usedAddress, usedPhone);
                boolean checkInputCards = validateInputsCards(usedName, usedAddress, usedPhone, usedCardNumber, usedExpireDate, usedCVC);

                if (checkInputNormal) {
                    if (orderMethod.isEmpty() || usedName.isEmpty() || usedAddress.isEmpty() || usedPhone.isEmpty()) {
                        Toast.makeText(SetDetailsActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        if (orderMethod.equals("Cash On Delivery")) {
                            Boolean addDeliveryDetail1 = DB.addDeliveryDetailsWithoutCard(usedId, usedName, usedPhone, usedAddress, orderMethod);
                            if (addDeliveryDetail1) {
                                Toast.makeText(SetDetailsActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SetDetailsActivity.this, OrderSuccessActivity.class);
                                intent.putExtra("current_user_data", currentUserDetails);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SetDetailsActivity.this, "Order Failed", Toast.LENGTH_LONG).show();
                            }
                        } else if (orderMethod.equals("Card Payment")) {
                            if (checkInputCards) {
                                if (usedCardNumber.isEmpty() || usedExpireDate.isEmpty() || usedCVC.isEmpty()) {
                                    Toast.makeText(SetDetailsActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                                } else {
                                    Boolean addDeliveryDetail2 = DB.addDeliveryDetailsWithCard(usedId, usedName, usedPhone, usedAddress, orderMethod, usedCardNumber, usedExpireDate, usedCVC);
                                    if (addDeliveryDetail2) {
                                        Toast.makeText(SetDetailsActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SetDetailsActivity.this, OrderSuccessActivity.class);
                                        intent.putExtra("current_user_data", currentUserDetails);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SetDetailsActivity.this, "Order Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        RadioButton cardPaymentRadioButton = findViewById(R.id.cdRButton);
        final LinearLayout cardDetailsLayout = findViewById(R.id.cardDetailsLayout);
        cardPaymentRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardNumber.setVisibility(View.VISIBLE);
                    cardDetailsLayout.setVisibility(View.VISIBLE);
                } else {
                    cardNumber.setVisibility(View.GONE);
                    cardDetailsLayout.setVisibility(View.GONE);
                }
            }
        });


        getUserDetails();
    }

    // Function to get the selected order method from the radio group
    private String getOrderMethod() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioBtn = findViewById(selectedId);
        return radioBtn.getText().toString();
    }

    // Function Payment Toast
    public void payment(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioBtn = findViewById(radioId);
        Toast.makeText(this, "" + radioBtn.getText() + " is Selected", Toast.LENGTH_SHORT).show();
    }

    // Function Get Current User Details
    public void getUserDetails() {
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<UserModel> aList = dbHelper.getCurrentUserDetails(currentUserDetails);
        UserModel userModel = aList.get(0);
        userId.setText(userModel.getUsed_id());
        rName.setText(userModel.getUsed_name());
        rAddress.setText(userModel.getUsed_address());
        rPhone.setText(userModel.getUsed_phone());
    }

    // Function for Validate Inputs with Card
    private Boolean validateInputsCards(String usedName, String usedAddress, String usedPhone, String usedCardNumber, String usedExpireDate, String usedCVC) {
        if (usedName.isEmpty()) {
            rName.requestFocus();
            rName.setError("Receiver Name Required");
            return false;
        } else if (!usedName.matches("[a-zA-Z ]+")) {
            rName.requestFocus();
            rName.setError("Alphabetic Letters Only");
            return false;
        } else if (usedPhone.isEmpty()) {
            rPhone.requestFocus();
            rPhone.setError("Mobile Number Required");
            return false;
        } else if (!usedPhone.matches("0[0-9]{9}")) {
            rPhone.requestFocus();
            rPhone.setError("Enter 10 Digit Number Starts with 0");
            return false;
        } else if (usedAddress.isEmpty()) {
            rAddress.requestFocus();
            rAddress.setError("Address Required");
            return false;
        } else if (!usedAddress.matches("[a-zA-Z 0-9,.-]+")) {
            rAddress.requestFocus();
            rAddress.setError("Address Invalid");
            return false;
        } else if (usedCardNumber.isEmpty()) {
            cardNumber.requestFocus();
            cardNumber.setError("Card Number Required");
            return false;
        } else if (!usedCardNumber.matches("\\d{16}")) {
            cardNumber.requestFocus();
            cardNumber.setError("Invalid Card Number");
            return false;
        } else if (usedExpireDate.isEmpty()) {
            ExpireDate.requestFocus();
            ExpireDate.setError("Card Expire Date Required");
            return false;
        } else if (!usedExpireDate.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            ExpireDate.requestFocus();
            ExpireDate.setError("Invalid Expire Date (MM/YY)");
            return false;
        } else if (usedCVC.isEmpty()) {
            CVC.requestFocus();
            CVC.setError("Card CVC Required");
            return false;
        } else if (!usedCVC.matches("\\d{3}")) {
            CVC.requestFocus();
            CVC.setError("Invalid CVC");
            return false;
        } else {
            return true;
        }
    }

    // Function for Validate Inputs Normal
    private Boolean validateInputsNormal(String usedName, String usedAddress, String usedPhone) {
        if (usedName.isEmpty()) {
            rName.requestFocus();
            rName.setError("Receiver Name Required");
            return false;
        } else if (!usedName.matches("[a-zA-Z ]+")) {
            rName.requestFocus();
            rName.setError("Alphabetic Letters Only");
            return false;
        } else if (usedPhone.isEmpty()) {
            rPhone.requestFocus();
            rPhone.setError("Mobile Number Required");
            return false;
        } else if (!usedPhone.matches("0[0-9]{9}")) {
            rPhone.requestFocus();
            rPhone.setError("Enter 10 Digit Number Starts with 0");
            return false;
        } else if (usedAddress.isEmpty()) {
            rAddress.requestFocus();
            rAddress.setError("Address Required");
            return false;
        } else if (!usedAddress.matches("[a-zA-Z 0-9,.-]+")) {
            rAddress.requestFocus();
            rAddress.setError("Address Invalid");
            return false;
        } else {
            return true;
        }
    }
}
