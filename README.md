# Adapter Design Pattern – Payment Processing Demonstration

A Java project demonstrating the **Adapter** structural design pattern applied to a real-world payment processing scenario.

## What Is the Adapter Pattern?

The Adapter pattern acts as a bridge between two incompatible interfaces. It wraps an existing class with a new interface so that it can work with code that expects a different interface — without modifying the original class.

Think of it like a power plug adapter: your device hasn't changed, but the adapter lets it connect to a different kind of outlet.

## Project Overview

This project simulates a payment processing system where different payment providers (e.g., PayPal, Stripe, or a legacy gateway) each expose their own unique API. Rather than rewriting your application logic every time a new provider is added, an **Adapter** is used to translate each provider's interface into a single common `PaymentByCard` interface that your application understands.

### Key Components

**`PaymentByCard`**
The interface your application code depends on. Defines a standard method like `processPayment(PaymentInfo paymentInfo)`.

**Legacy/Third-Party Payment Services (Adaptees)**
Existing payment classes with their own method signatures that are incompatible with `PaymentByCard` — for example, a `PaymentAPI` that exposes `charge(String token, double amount)`.

**Adapters**
Wrapper classes that implement `PaymentByCard` and internally delegate calls to the adaptee, handling any necessary translation (e.g., unit conversion, parameter mapping).

**`App` / Client**
Demonstrates how the client can use any payment provider interchangeably through the common `PaymentByCard` interface, unaware of the underlying implementation.

## Design Pattern Summary

| Role | Class                                                       |
|---|-------------------------------------------------------------|
| **Target** | `PaymentByCard` (interface)                                 |
| **Adaptee** | Legacy or third-party payment service `PaymentAPI`          |
| **Adapter** | Wraps adaptee, implements target interface `PaymentAdapter` |
| **Client** | `App` — uses only the `PaymentByCard` interface          |

## Why Use the Adapter Pattern?

- **Open/Closed Principle** — Add new payment providers without modifying existing client code.
- **Single Responsibility** — Each adapter handles the translation for one specific provider.
- **Reusability** — Integrate third-party or legacy APIs without altering them.
