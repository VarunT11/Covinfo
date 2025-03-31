# 🩸 CovInfo: COVID-19 Tracker (Android App)

CovInfo is an Android application that tracks and visualizes real-time COVID-19 data from around the world, with a particular focus on India. It displays stats at global, country, state, and district levels through clean tables and charts. The backend is powered by Django and hosted on Heroku.

---

## 🔍 Overview

This mobile app was developed during the COVID-19 pandemic to help users access daily case updates, news, and trends. It fetches data using public REST APIs and provides filterable views for better insight.

---

## ✨ Features

- 🌐 Real-time COVID-19 stats for **200+ countries** and **Indian states/districts**
- 📈 Interactive graphs for **7-day, 30-day, and all-time** data trends
- 🔹 Sortable tables for state-wise and district-wise comparisons
- 📰 Live COVID-19 news headlines pulled from external APIs
- 🌟 Smooth and responsive UI using Android chart libraries

---

## 🛠️ Tech Stack

- **Frontend:** Android (Java)
- **Architecture:** MVVM
- **Data Visualization:** Custom charting libraries
- **API Integration:** Public REST APIs for global and India-specific data
- **Backend:** Django (via [Covid-Stats-Backend](https://github.com/VarunT11/Covid-Stats-Backend)) hosted on Heroku

---

## 📖 Key Screens

- Country-level Summary View
- India State & District Dashboard
- Trend Charts (7-day / 30-day / All-time)
- News Feed Section

---

## 📂 Repository Structure

```
Covinfo/
├── app/
│   ├── activities/
│   ├── adapters/
│   ├── models/
│   ├── network/
│   └── utils/
├── res/
│   ├── layout/
│   └── values/
├── AndroidManifest.xml
└── build.gradle
```

---

## 👤 Author

**Varun Tiwari**  
[GitHub](https://github.com/VarunT11) | [LinkedIn](https://linkedin.com/in/iamvt11)

---
This project was built for educational and awareness purposes during the COVID-19 pandemic.

> Note: This app relies on third-party APIs that may change or be deprecated.
