# 🏥 Hospital Appointment Booking & Management System

A role-based appointment scheduling platform for clinics that solves a very concrete real-world problem: **preventing doctor double-booking** while giving patients a simple way to find and book a consultation slot.

Built with **Java Spring Boot**, **Spring Security + JWT (role-based)**, **MySQL**, and **React**.

## 🧩 The Problem

Small clinics often manage appointments over phone calls and paper registers, which leads to double-bookings and no-shows. This system gives patients self-service booking against a doctor's real weekly availability, while preventing any two appointments from overlapping for the same doctor.

## ✨ Features

- 🔐 Role-based auth (**PATIENT**, **DOCTOR**, **ADMIN**) with JWT
- 🩺 Doctor directory with specialization filter
- 📅 Doctor weekly availability windows (day + start/end time)
- ✅ **Conflict-safe booking**: a new appointment is rejected if it falls outside the doctor's availability window or overlaps an existing appointment (based on the doctor's consultation duration)
- 🔁 Appointment status lifecycle: `PENDING → CONFIRMED → COMPLETED` / `CANCELLED`
- 📋 Separate views for a patient's appointments vs. a doctor's schedule

## 🏗️ Architecture

```
React SPA ──JWT──▶ Spring Boot REST API ──JPA──▶ MySQL
                        │
        AppointmentService (availability + overlap checks)
```

## 🛠️ Tech Stack

Java 17 · Spring Boot 3 · Spring Security · Spring Data JPA · MySQL 8 · JWT (jjwt) · React 18 · Axios · Maven · npm

## 🚀 Getting Started

### Backend
```bash
cd backend
cp src/main/resources/application-example.properties src/main/resources/application.properties
# edit with your MySQL credentials
mvn spring-boot:run
```
Runs on `http://localhost:8081`.

### Frontend
```bash
cd frontend
npm install
npm start
```

## 📡 Key API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register as PATIENT or DOCTOR |
| POST | `/api/auth/login` | Log in, returns JWT with role claim |
| GET | `/api/doctors?specialization=` | Browse/filter doctors |
| POST | `/api/doctors/availability` | Doctor sets a weekly availability window |
| POST | `/api/appointments/book` | Patient books a slot (validated against overlaps) |
| GET | `/api/appointments/my` | Patient's own appointments |
| GET | `/api/appointments/doctor` | Doctor's schedule |
| PUT | `/api/appointments/{id}/status?status=` | Confirm / cancel / complete |

## 🗺️ Roadmap

- [ ] Email/SMS reminders before appointments
- [ ] Admin dashboard for clinic-wide scheduling
- [ ] Recurring availability templates
- [ ] Video consultation integration

## 👩‍💻 Author

**Divya Waghmare** — [LinkedIn](https://linkedin.com/in/divya-waghmare) · [GitHub](https://github.com/D25WR)

## 📄 License

MIT — see [LICENSE](LICENSE)
