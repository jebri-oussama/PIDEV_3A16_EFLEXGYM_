package gestion_produit;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Payment {

    public static void main(String[] args) {

        Stripe.apiKey = "sk_test_51OpMszJBIZnGaCSRFp9XiTyNaj3pXhiahFXKN680Y3qS9KQk0BoBDiCiM0oB0oyXufFRu6wPvo8mK23pq79ZpxZA00n44jF8AD";
        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
    }

