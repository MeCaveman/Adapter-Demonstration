# Adapter Design Pattern — Payment System Demo

## What is the Adapter Pattern?

Imagine you're traveling abroad and your phone charger doesn't fit the wall socket. You use a **plug adapter** — a small device that sits in between, converting one interface into another. You don't rewire your charger or the wall socket; the adapter handles the translation.

The **Adapter design pattern** does exactly this in software. It acts as a bridge between two incompatible interfaces, allowing them to work together without modifying either one.

> **In short:** The Adapter wraps an existing class (the *Adaptee*) and exposes it through a different interface (the *Target*) that the client expects.

### The Four Players

| Role | Description |
|---|---|
| **Target** | The interface the client wants to use |
| **Adaptee** | The existing class with a different, incompatible interface |
| **Adapter** | The bridge class that implements the Target and internally uses the Adaptee |
| **Client** | The code that uses the Target interface |

---

## Project Overview

This project demonstrates the Adapter pattern through a real-world payment processing scenario:

- Your application processes payments using **card details** (`PaymentByCard` interface).
- The third-party payment library works using **tokens** (`PaymentByToken` interface).
- These two interfaces are incompatible — so a `PaymentAdapter` is used to bridge them.

### Project Structure

```
src/
├── PaymentByCard.java      # Target interface (what the app expects)
├── PaymentByToken.java     # Adaptee interface (what the payment API provides)
├── PaymentAPI.java         # Adaptee (the third-party payment service)
├── PaymentInfo.java        # Data model holding card details
├── PaymentAdapter.java     # The Adapter — bridges card → token
└── App.java                # Client — entry point
```

---

## Code Walkthrough

### 1. `PaymentInfo.java` — The Data Model

This is a simple data container that holds the card information a customer would provide.

```java
public class PaymentInfo {
    private String cardNumber;
    private String expiry;
    private String cvv;
    private double amount;

    public PaymentInfo(String cardNumber, String expiry, String cvv, double amount) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
        this.amount = amount;
    }

    // Getters...
}
```

Nothing fancy here — it just bundles the payment data so it can be passed around cleanly.

---

### 2. `PaymentByCard.java` — The Target Interface

This is the interface **your application** is built around. The client code only knows about this interface and expects all payment processing to go through it.

```java
public interface PaymentByCard {
    void processPayment(PaymentInfo paymentInfo);
}
```

Simple and clean: give it card details, it processes the payment. This is what the client wants to call.

---

### 3. `PaymentByToken.java` — The Adaptee Interface

This is the interface that the **third-party payment library** exposes. It operates completely differently — it first requires you to create a token from card details, then charges against that token.

```java
public interface PaymentByToken {
    String createToken(String cardNumber, String expiry, String cvv);
    void charge(String token, double amount);
}
```

Notice the mismatch: `PaymentByCard` wants one method call with a `PaymentInfo` object, while `PaymentByToken` requires two separate calls with individual strings. This incompatibility is exactly what the Adapter solves.

---

### 4. `PaymentAPI.java` — The Adaptee (Third-Party Service)

This is the concrete implementation of the token-based payment API. Think of it as the external library you'd install from a vendor.

```java
public class PaymentAPI implements PaymentByToken {

    @Override
    public String createToken(String cardNumber, String expiry, String cvv) {
        System.out.println("Generating Token....");
        return "tok_" + cardNumber.substring(cardNumber.length() - 4) + expiry + cvv;
    }

    @Override
    public void charge(String token, double amount) {
        System.out.println("Charging $" + amount + " using token " + token);
        System.out.println("Payment Successful");
    }
}
```

The `createToken` method generates a mock token by combining the last 4 digits of the card, partial expiry, and partial CVV (e.g., `tok_111226`). The `charge` method then uses that token to complete the transaction. You wouldn't change this class — it's an external dependency.

---

### 5. `PaymentAdapter.java` — The Adapter ⭐

This is the heart of the pattern. The adapter implements the `PaymentByCard` interface (what the client expects) and internally delegates the work to the `PaymentByToken` implementation (what the API provides).

```java
public class PaymentAdapter implements PaymentByCard {

    private PaymentByToken paymentByToken;
    private PaymentAPI paymentAPI;

    public PaymentAdapter(PaymentAPI paymentAPI) {
        this.paymentAPI = paymentAPI;
        this.paymentByToken = new PaymentAPI();
    }

    @Override
    public void processPayment(PaymentInfo paymentInfo) {
        String token = paymentByToken.createToken(
            paymentInfo.getCardNumber(),
            paymentInfo.getExpiry(),
            paymentInfo.getCvv()
        );

        paymentAPI.charge(token, paymentInfo.getAmount());
    }
}
```

**What it does step by step:**
1. Receives a `PaymentInfo` object (the card-based interface the client uses).
2. Extracts card details and calls `createToken()` on the token API — translating the card info into a token.
3. Calls `charge()` using that token and the amount.

The client never knows any of this is happening. From its perspective, it just called `processPayment()` and the payment went through.

---

### 6. `App.java` — The Client

This is where everything comes together. Notice how clean and simple the client code is:

```java
public class App {
    public static void main(String[] args) {
        String cardNumber = "1234567891011112";
        String expiry = "02/26";
        String cvv = "123";
        double amount = 1533.50;

        PaymentInfo paymentInfo = new PaymentInfo(cardNumber, expiry, cvv, amount);

        PaymentByCard paymentByCard = new PaymentAdapter(new PaymentAPI());

        paymentByCard.processPayment(paymentInfo);
    }
}
```

The client creates a `PaymentAdapter` injected with a `PaymentAPI`, and assigns it to a `PaymentByCard` variable. From that point on, the client only interacts with the `PaymentByCard` interface — it has no knowledge of tokens, `PaymentAPI`, or the two-step process happening underneath.

**Expected output:**
```
Generating Token....
Charging $1533.5 using token tok_111202/26123
Payment Successful
```

---

## How the Pieces Fit Together

```
Client (App)
    │
    │  calls processPayment(PaymentInfo)
    ▼
PaymentByCard interface  ◄── PaymentAdapter (implements this)
                                    │
                                    │  internally calls createToken() then charge()
                                    ▼
                             PaymentByToken interface  ◄── PaymentAPI (implements this)
```

The adapter sits in the middle, invisible to the client, translating one interface into another.

---

## Why Use the Adapter Pattern?

**Without the Adapter**, your client code would have to know about the `PaymentByToken` interface, manually call `createToken()`, store the result, then call `charge()`. This leaks implementation details everywhere and makes your app tightly coupled to the third-party API.

**With the Adapter**, your application stays decoupled. If the payment provider changes their API tomorrow, you only update the adapter — not every place in your codebase that handles payments.

It also makes your code more testable: you can swap in a mock adapter during tests without touching business logic.

---
