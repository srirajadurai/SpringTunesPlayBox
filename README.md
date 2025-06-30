# SpringTunesPlayBox
🧩 Design

SpringTunes is designed as a secure and responsive full-stack web application. Below is an overview of its core components:
📌 Architecture

    Backend: Spring Boot with MVC architecture

    Frontend: Thymeleaf templates with a dark-themed responsive UI

    Database: MySQL for storing user data and downloaded song info

    Security: Spring Security handles authentication and admin-only authorization

🔐 Key Features

    Admin Authentication: Only admin users can access the platform after login

    YouTube Integration: Uses the YouTube Data API to search videos based on user input

    MP3 Download & Preview: Admin can download songs as MP3 and preview them directly in the app

    User & Song Management: Admins can view and manage user and song entries in the database

    Modern UI: Intuitive, minimal dark mode interface for better usability

🔁 Flow Summary

[ Login as Admin ]
        ↓
[ Search Song (YouTube API) ]
        ↓
[ Fetch & Convert to MP3 ]
        ↓
[ Preview / Download ]
        ↓
[ Store Song Data in MySQL ]

⚠️ DISCLAIMER — READ CAREFULLY

    🚫 This project is strictly for educational and learning purposes only.

    ❗ Do not use SpringTunes for any commercial or public use.

    📛 Downloading and converting content from YouTube violates YouTube's Terms of Service.

    ⚖️ Using this tool in a live/public/commercial context may result in legal consequences.

    ✅ The intention is purely to demonstrate full-stack application development and API integration.
