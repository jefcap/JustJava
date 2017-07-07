package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Get Customer Name
        EditText customerNameView = (EditText) findViewById(R.id.customer_name);
        String customerName = customerNameView.getText().toString();
        Log.i("MainActivity.java", "Customer name: "+ customerName);

        //Get checkbox state
        Boolean hasWhippedCream = ((CheckBox) findViewById(R.id.whipped_cream_checkbox)).isChecked();
        Boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
//        Log.i("MainActivity.java", "Has whipped cream: "+ hasWhippedCream);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        //String priceMessage = "Total: $" + price + "\n Thank you!";
        //displayMessage(createOrderSummary(price,hasWhippedCream, hasChocolate, customerName));
        //Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
        String orderSummary = createOrderSummary(price,hasWhippedCream, hasChocolate, customerName);

        //Test with intent to use with google maps
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri geoLocation = Uri.parse("geo:47.6, -122.3");
//        intent.setData(geoLocation);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, customerName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(Boolean hasWhippedCream, Boolean hasChocolate) {
        int unityPrice = 5;

        if (hasWhippedCream) {
            unityPrice += 1;
        }
        if (hasChocolate) {
            unityPrice += 2;
        }
        int price = quantity * unityPrice;

        return price;
    }

    /**
     * Generate order summary
     *
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String customerName) {
        String priceMessage = "Name: " + customerName;
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
