# 🍽️ Restaurant Ordering System – Interim Progress

**SCSE1224 – Advanced Programming**  
**Semester 2, Session 2024/2025**  
**Lecturer:** Dr. Mohd Razak bin Samingan  
**Group:** 2 – Section 03

---

## 👥 Group Members

| Name               | Matric No.  |
|--------------------|-------------|
| Alfred Chin Zhan Hoong | A24CS0224 |
| Lee Ming Da        | A24CS0317   |
| Gan Rui En         | A24CS0249   |
| Zuo Boyu           | A24CS4045   |

---

## 📌 Table of Contents

1. [Timeline & Milestones](#calendar-timeline--milestones)
2. [Association Diagram](#link-association-diagram)
3. [Class Descriptions](#blue_book-class-descriptions)

---

## 📆 Timeline & Milestones

| Week | Date Range       | Milestone                          | Description |
|------|------------------|------------------------------------|-------------|
| 4    | 7–13 April        | Proposal Preparation               | - Form group and confirm topic<br>- Draft system overview, function list, and class diagram |
|      |                  | Initial Class Implementation       | - Design classes: `Customer`, `MenuItem`, `Food`, `Drink`, `Order`, `OrderItem`, etc. |
|      |                  | Basic Feature Integration          | - Develop: menu display, item search, order placement, checkout, and receipt |
|      |                  | File I/O & Admin Features          | - Implement: save/load functionality, admin login, item CRUD, sales reporting |
| 5    | 14–20 April       | Proposal Submission                | - Submit CLI prototype and Java class structure |
| 13   | 9–15 June         | Interim Submission                 | - Implementation of proposal<br>- Submit updated project plan, timeline, class diagram, partial working code |
| 15   | 23–29 June        | Testing & Finalization             | - Finalize features, test system, debug<br>- Submit video and final system |

---

## 🔗 Association Diagram

> _Refer to the visual diagram showing class relationships and member access types._

| Icon | Description     |
|------|-----------------|
| `-`  | private         |
| `#`  | protected       |
| `~`  | package-private |
| `+`  | public          |

---

## 📘 Class Descriptions

| Class     | Description |
|-----------|-------------|
| `Customer` | Stores customer details (name, phone, address). Can view menus, order items, view receipts, and search items. |
| `Admin` | Manages the system. Default password: `admin123`. Can add/edit/delete menu items and view sales reports. |
| `Order` | Represents a customer's full order including order date, payment method, and receipt generation. |
| `OrderItem` | Details an item in an order, including quantity. |
| `Menu` | Collection of available items (food & drink), viewable by customers and managed by admin. |
| `MenuItem` | A base item with `name`, `price`, `description`, and category (food or drink). |
| `Food` | Inherits `MenuItem`, specific to food-type items. |
| `Drink` | Inherits `MenuItem`, specific to drink-type items. |

---

## ✅ Status

This repository is currently in the **interim stage**. Basic functionality and core class implementations are being finalized. Further improvements and testing will continue into Week 15.
