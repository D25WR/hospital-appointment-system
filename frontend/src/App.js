import React, { useEffect, useState } from "react";
import api from "./services/api";

/**
 * Minimal single-page demo: login, browse doctors, and book an appointment.
 * See README for the full role-based flow (patient / doctor / admin).
 */
export default function App() {
  const [doctors, setDoctors] = useState([]);
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [form, setForm] = useState({ email: "", password: "" });

  useEffect(() => {
    if (token) api.get("/doctors").then((res) => setDoctors(res.data));
  }, [token]);

  const login = async (e) => {
    e.preventDefault();
    const { data } = await api.post("/auth/login", form);
    localStorage.setItem("token", data.token);
    setToken(data.token);
  };

  if (!token) {
    return (
      <div className="auth-box">
        <h2>🏥 MediCare Login</h2>
        <form onSubmit={login}>
          <input placeholder="Email" onChange={(e) => setForm({ ...form, email: e.target.value })} />
          <input type="password" placeholder="Password" onChange={(e) => setForm({ ...form, password: e.target.value })} />
          <button type="submit">Login</button>
        </form>
      </div>
    );
  }

  return (
    <div className="page-container">
      <h2>Available Doctors</h2>
      <ul className="doctor-list">
        {doctors.map((d) => (
          <li key={d.id}>{d.user?.fullName} — {d.specialization}</li>
        ))}
      </ul>
    </div>
  );
}
