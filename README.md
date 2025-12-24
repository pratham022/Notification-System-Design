# 📢 Notification System — Ultimate Low Level Design (LLD) Guide

This README serves as a **single source of truth** for understanding, revising, and explaining a **scalable Notification System LLD** during SDE‑2 interviews.

If you read **only this file**, you should be able to:
- Explain every class and interface confidently
- Justify every design decision
- Extend the system live during an interview
- Answer follow‑up design questions calmly

---

## 1️⃣ Problem Statement (Interview Framing)

Design a notification system that:
- Supports multiple channels (Email, SMS)
- Can send notifications immediately
- Can schedule notifications with a delay
- Is extensible for future channels (Push, WhatsApp)
- Is clean, testable, and follows SOLID principles

**Constraints (Implicit in interviews):**
- System should be extensible
- Avoid if‑else / switch on channels
- Avoid tight coupling
- Design should scale conceptually

---

## 2️⃣ Scope Clarification (Very Important)

This design focuses on:
- Low Level Design (classes, interfaces, responsibilities)
- Clean object‑oriented principles
- Interview‑friendly simplicity

This design **does NOT** implement:
- Real schedulers (Quartz / cron)
- Actual Kafka producers/consumers
- External APIs (Twilio, SendGrid)

These are **discussed conceptually**, not coded.

---

## 3️⃣ High‑Level LLD Flow

```
Client
  |
  v
NotificationDispatcher
  |
  v
NotificationSenderFactory
  |
  v
Concrete Sender (Email / SMS)
```

### Why this flow?
- Client should not know channel logic
- Dispatcher should not know concrete implementations
- Factory hides object creation
- Senders contain channel‑specific behavior

---

## 4️⃣ Core Domain Model — Notification

### What is Notification?

`Notification` represents **what needs to be delivered**, not **how**.

It contains only **essential data**:
- Channel (EMAIL / SMS)
- Content (message body)
- Recipient (email / phone)

### Why is Notification an interface?
- Enables polymorphism
- Allows multiple implementations
- Avoids channel‑specific conditionals

### Why separate EmailNotification and SMSNotification?
- Each notification type may evolve independently
- Validation rules may differ
- Payload shape may differ later

---

## 5️⃣ Sending vs Scheduling (Design Decision)

### Immediate Send
```java
send(Notification notification)
```

Used when the message should be delivered immediately.

### Scheduled Send
```java
schedule(Notification notification, long delaySeconds)
```

Used when delivery should occur in the future.

### Why use `delaySeconds`?
- Simple
- Interview‑friendly
- Avoids DateTime complexity
- Can be easily replaced later

**Implementation logic:**
```
scheduledTime = currentTime + delaySeconds
```

---

## 6️⃣ Channel‑Specific Senders

Each channel has its own sender:
- EmailNotificationSender
- SMSNotificationSender

### Why separate sender classes?
- Each channel integrates with different providers
- Each channel has different retry logic
- Each channel has different rate limits

### Responsibilities of a Sender
- Format message
- Handle scheduling
- Send message
- Log meaningful information

### What senders should NOT do
- Choose channel
- Create notifications
- Route requests

---

## 7️⃣ NotificationSenderFactory (Factory Pattern)

### Purpose
Centralized place to map:
```
Channel → Concrete Sender
```

### Why Factory?
- Avoids `if(channel == EMAIL)`
- Encapsulates object creation
- Makes system extensible

### How extension works
To add WhatsApp:
1. Create WhatsAppNotificationSender
2. Register it in the factory
3. Done

No dispatcher logic changes.

---

## 8️⃣ NotificationDispatcher (Orchestrator)

### Role
Acts as the **single entry point** for notification operations.

### Responsibilities
- Accept Notification
- Ask factory for sender
- Delegate send/schedule

### Why Dispatcher exists
- Keeps client simple
- Centralizes routing logic
- Prevents duplication

### What Dispatcher does NOT know
- Email internals
- SMS internals
- Scheduling logic

---

## 9️⃣ SOLID Principles — Deep Dive

---

### ✅ S — Single Responsibility Principle (SRP)

**Definition:**  
A class should have only one reason to change.

### How SRP is applied

| Class | Responsibility |
|-----|---------------|
| Notification | Holds data |
| EmailNotificationSender | Email sending |
| SMSNotificationSender | SMS sending |
| NotificationDispatcher | Routing |
| Factory | Object creation |

### Interview Explanation
If Email sending changes, only EmailNotificationSender changes.  
No ripple effect.

---

### ✅ O — Open/Closed Principle (OCP)

**Definition:**  
Open for extension, closed for modification.

### How OCP is applied
- Add new channels via new classes
- Existing logic remains untouched

### Interview Explanation
System grows horizontally without breaking existing code.

---

### ✅ L — Liskov Substitution Principle (LSP)

**Definition:**  
Subtypes must be replaceable for their base type.

### How LSP is applied
- Dispatcher works with Notification interface
- Any Notification subtype behaves correctly

### Interview Explanation
Polymorphism works without surprises.

---

### ✅ I — Interface Segregation Principle (ISP)

**Definition:**  
Clients should not depend on interfaces they don’t use.

### How ISP is applied
- NotificationSender → send only
- ScheduledNotificationSender → send + schedule

### Interview Explanation
Channels can opt‑out of scheduling cleanly.

---

### ✅ D — Dependency Inversion Principle (DIP)

**Definition:**  
Depend on abstractions, not concrete implementations.

### How DIP is applied
- Dispatcher depends on Factory interface
- Factory returns Sender interface

### Interview Explanation
Enables mocking, testing, and future replacement.

---

## 🔟 Design Patterns Used (Explained)

### Factory Pattern
Decouples creation logic from usage.

### Strategy Pattern
Each sender is a strategy for sending notifications.

### Dependency Injection
Factory injected via constructor.

### Command Pattern (Implicit)
Notification represents an executable command.

---

## 1️⃣1️⃣ Scalability (Conceptual — Interview Discussion)

### Message Queues
- Kafka / SQS decouple producer & consumers
- Enables retries and buffering

### Channel‑wise Scaling
- Scale Email independently from SMS

### Retry & DLQ
- Failed messages retried
- Permanent failures go to DLQ

---

## 1️⃣2️⃣ Consistency & Reliability

### Delivery Guarantee
- At‑least‑once delivery
- Idempotency avoids duplicates

### Consistency Model
- Eventual consistency
- Async by nature

---

## 1️⃣3️⃣ Common Interview Follow‑Ups

**Q: How do you prevent duplicate notifications?**  
A: Idempotency key + dedup store.

**Q: How to add rate limiting?**  
A: Per‑channel sender layer.

**Q: How to schedule millions of notifications?**  
A: Scheduler DB + worker polling.

---

## 🏁 Final Takeaway

This design:
- Is simple but powerful
- Is extensible
- Is interview‑friendly
- Demonstrates strong fundamentals

If you can explain this README confidently,  
you can handle **any LLD notification question**.

---

💡 *This file is intentionally verbose — treat it as your personal LLD handbook.*
