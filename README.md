# 🥕 Nectar – Online Grocery Application

Nectar is an Android mobile application that provides a modern and user-friendly online grocery shopping experience.  
The app allows users to browse grocery products, explore categories, view product details, and manage data efficiently using modern Android development tools.

---

## 📱 App Overview

Nectar is designed to simulate a real-world online grocery store where users can:
- Browse grocery products
- Explore products by category
- View detailed product information
- Search for products easily
- Select their location (country and city)

The application focuses on simplicity, clean UI design, and efficient data handling.

---

## 🎨 UI Design (Figma)

The UI design of the application is inspired by and based on the following Figma design:

🔗 **Figma Design Link:**  
https://www.figma.com/design/7cEfdtg8i80OH56cFUsLXL/Online-Groceries-App-UI--Community---Copy-

This design was used as a reference for building the app layout, color scheme, and overall user experience.

---

## ✨ Main Features

- **Splash Screen**
    - Displays application branding and logo

- **Location Selection**
    - Select country and city using a public REST API
    - Location is saved locally for future use

- **Home Screen**
    - Displays featured products and best-selling items
    - Shows user location
    - Quick access to categories

- **Explore & Categories**
    - Browse products by category (Fruits, Vegetables, Beverages, etc.)

- **Product Details**
    - Product image, price, description, and category

- **Search Functionality**
    - Search for products by name

- **Admin Dashboard**
    - Add, update, and delete products
    - Add and manage categories

---

## 🛠 Technologies Used

- Kotlin
- Android Jetpack Compose
- MVVM Architecture
- Room Database
- Retrofit
- StateFlow
- WorkManager
- Navigation Component

---

## 🌐 API Used

- **Public REST API**
- **Endpoint:**  
  https://countriesnow.space/api/v0.1/countries
- **Purpose:**
    - Fetch countries and cities
    - Used for location selection and personalization

---

## 💾 Local Data Persistence

- Room Database is used to store:
    - Products
    - Categories
    - Users
- CRUD operations implemented (Create, Read, Update, Delete)
- Data persists across app restarts
- Relationship between Products and Categories

---

## 🔄 Background Work

- Background tasks implemented using WorkManager
- Periodic data synchronization
- Constraints applied:
    - Network connectivity required
    - Battery must not be low

---

## ▶️ How to Run the Project

Follow these steps to run the application locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
