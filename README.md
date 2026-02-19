# Adapter Design Pattern – Payment Processing Demonstration

A Java project demonstrating the **Adapter** structural design pattern applied to a real-world payment processing scenario.

## What Is the Adapter Pattern?

The Adapter pattern acts as a bridge between two incompatible interfaces. It wraps an existing class with a new interface so that it can work with code that expects a different interface — without modifying the original class.

Think of it like a power plug adapter: your device hasn't changed, but the adapter lets it connect to a different kind of outlet.

## Project Overview

This project simulates a payment processing system where different payment providers (e.g., PayPal, Stripe, or a legacy gateway) each expose their own unique API. Rather than rewriting your application logic every time a new provider is added, an **Adapter** is used to translate each provider's interface into a single common `PaymentProcessor` interface that your application understands.

### Key Components

**`PaymentProcessor` (Target Interface)**
The interface your application code depends on. Defines a standard method like `processPayment(double amount)`.

**Legacy/Third-Party Payment Services (Adaptees)**
Existing payment classes with their own method signatures that are incompatible with `PaymentProcessor` — for example, a `LegacyPaymentGateway` that exposes `makePayment(int amountInCents)`, or a `ThirdPartyPayPal` that uses `sendMoney(double amount, String currency)`.

**Adapters**
Wrapper classes that implement `PaymentProcessor` and internally delegate calls to the adaptee, handling any necessary translation (e.g., unit conversion, parameter mapping).

**`Main` / Client**
Demonstrates how the client can use any payment provider interchangeably through the common `PaymentProcessor` interface, unaware of the underlying implementation.

## How to Run

**Requirements:** Java 8+ and an IDE such as IntelliJ IDEA (an `.iml` project file is included).

1. Clone the repository:
   ```bash
   git clone https://github.com/MeCaveman/Adapter-Demonstration.git
   ```
2. Open the project in IntelliJ IDEA (or your preferred Java IDE).
3. Run the `Main` class located in the `src` directory.

## Project Structure

```
Adapter-Demonstration/
├── src/
│   ├── Main.java                  # Entry point / client code
│   ├── PaymentProcessor.java      # Target interface
│   ├── *Adapter.java              # Adapter class(es)
│   └── *Gateway / *Service.java   # Adaptee class(es)
├── out/                           # Compiled output
├── .idea/                         # IntelliJ project settings
└── AdapterDemonstration.iml       # IntelliJ module file
```

## Design Pattern Summary

| Role | Class |
|---|---|
| **Target** | `PaymentProcessor` (interface) |
| **Adaptee** | Legacy or third-party payment service |
| **Adapter** | Wraps adaptee, implements target interface |
| **Client** | `Main` — uses only the `PaymentProcessor` interface |

## Why Use the Adapter Pattern?

- **Open/Closed Principle** — Add new payment providers without modifying existing client code.
- **Single Responsibility** — Each adapter handles the translation for one specific provider.
- **Reusability** — Integrate third-party or legacy APIs without altering them.
