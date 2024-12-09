# Parkbnb: A Smart Parking Reservation and Management Solution

Parkbnb is a comprehensive parking management system designed to simplify parking reservations, improve efficiency for rentees and renters, and provide a seamless user experience. It addresses challenges in urban and high-traffic areas, offering features like parking spot search, geolocation services, secure payments, and user reviews.


## Contributors 👩🏻‍💻
- Keya Goswami    `Project Manager`
- Shreya Wanisha  `Tech Lead`
- Dharana Kashyap `Developer`
- Vanshi Patel    `Developer`
- Shriya Pratapwar `Designer`

## Tech Stack
***Front End***
- **React.js (Material UI):** Dynamic user interface.
- **Google Maps API:** Map integration for parking spot navigation.
- **Opencage Geocoding API:** Address-to-coordinates conversion.
- **Stripe Payment Integration:** Secure payment processing.

***Back End***
- **Java (Spring Boot):** Monolith architecture adhering to SOLID principles.
- **Spring Data JPA:** Database operations.
- **PostgreSQL:** Relational database for entity management
- **Security:** JWT for authentication and OAuth 2.0 for secure payment processing

## Key Functionalities
- **Parking Spot Management:** Add, edit, delete, and search for parking spots.
- **Geolocation Services:** Nearby parking spot detection using Google Maps
- **User Reviews and Ratings:** Feedback for parking locations.
- **Reservation Management:** Booking reservations.
- **Payment Processing:** Stripe integration for secure transactions.
- **Role-Based Dashboards:** Renter and rentee-specific features.

## Backend Architecture
***Services implemented include:***
- **BankDetailService:** Manages renters’ bank details.
- **CardService:** Handles rentees’ card details.
- **GeocodingService:** Converts addresses to latitude/longitude and vice versa.
- **ParkingLocationService:** Manages parking locations.
- **ParkingSpotService:** Operates individual parking spots.
- **PaymentService:** Facilitates payments during reservations.
- **ReservationService:** Central reservation management.
- **ReviewService:** Manages reviews and ratings.

## Database Schema
***Entities include:***
- Parking Locations and Spots
- Users (Renters and Rentees)
- Reservations
- Payments
- Reviews and Ratings

## Object - Oriented Principles
- Loose Coupling
- SOLID Principles
- Abstraction
- Inheritance
- Polymorphism
- Encapsulation
- Dependency Injection

## Project Contributions
***Frontend***
- Dharana Kashyap: Parking location pages, Google Maps integration, reviews, and dynamic forms for managing spots.
- Vanshi Patel: Profile sections, landing page, and reusable components.
- Shriya Pratapwar: Sign-in/sign-up, session handling, and password reset.
- Shreya Wanisha: Payment interface, card management, and receipts.
- Keya Goswami: Geolocation-based search and renter dashboard.

***Backend***
- Dharana Kashyap: Database setup, renter/rentee APIs, and image upload functionality.
- Vanshi Patel: Bank details and refund management.
- Shriya Pratapwar: Login, session handling, and reservations.
- Shreya Wanisha: Stripe integration for payments.
- Keya Goswami: Geolocation APIs and parking spot management.

## Future Scope
- Real-time notifications and alerts.
- Advanced analytics for renters.
- Enhanced traffic prediction for parking demand.
- Refunds







[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/6xRviSdT)
# Welcome to the course CSYE6200- Concepts of Object Oriented Design
> Northeastern University, College of Engineering


## Professor: Daniel Peters

### Requirements
1. Eclipse or VS Code or IntelliJ.

Note: If you are using Eclipse, please have git CLI installed on your system or GitHub Desktop to commit the code in this repository

### SetUp Instructions
1. Please clone the repository on your local system
2. For Eclipse Import the project as Existing Maven Project, For IntelliJ you can directlty open it using 'Get from VCS'.
4. All code should be pushed to the main branch
3. Ensure the GitHub actions are successful post push

Submissions will have deadlines, failed GitHub Actions would result in point deductions.

### References
1. Cloning a Repository: <https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository>
2. Any GitHub Setup: Please refer to the Git & GitHub Fundamentals Repository shared to you by your respective TA and refer the README.md section

Please reach out to your respective TA if you need any help in regards with submission/ GitHub

Author:
- Rohan Vasudev Ginde (ginde.r@northeastern.edu)
- Yesha Joshi (joshi.ye@northeastern.edu)

