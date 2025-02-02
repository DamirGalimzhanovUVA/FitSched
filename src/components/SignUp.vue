<template>
    <div class="login-container">
      <h2>Sign Up</h2>
      <input v-model="name" placeholder="Full Name">
      <input v-model="email" placeholder="Email">
      <input v-model="password" type="password" placeholder="Password">
      
      <h3>Workout Preferences</h3>
      <textarea v-model="description" placeholder="Describe your workout style"></textarea>
  
      <h3>Set Your Schedule</h3>
      <div v-for="(schedule, index) in schedules" :key="index" class="schedule-group">
        <select v-model="schedule.days" multiple>
          <option value="Monday">Monday</option>
          <option value="Tuesday">Tuesday</option>
          <option value="Wednesday">Wednesday</option>
          <option value="Thursday">Thursday</option>
          <option value="Friday">Friday</option>
          <option value="Saturday">Saturday</option>
          <option value="Sunday">Sunday</option>
        </select>
        <select v-model="schedule.startTime">
          <option v-for="time in timeSlots" :key="time" :value="time">{{ time }}</option>
        </select>
        -
        <select v-model="schedule.endTime">
          <option v-for="time in timeSlots" :key="time" :value="time">{{ time }}</option>
        </select>
        <select v-model="schedule.workout">
          <option>Basketball</option>
          <option>Tennis</option>
          <option>Weights</option>
          <option>General Cardio</option>
        </select>
        <button @click="removeSchedule(index)" class="remove-btn">❌</button>
      </div>
  
      <button @click="addSchedule" class="add-btn">➕ Add Schedule</button>
      <button @click="handleSignUp">Register</button>
      <button @click="goToLogin">Back to Login</button>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        name: "",
        email: "",
        password: "",
        description: "",
        schedules: [],
        timeSlots: [
          "06:00 AM", "06:15 AM", "06:30 AM", "06:45 AM",
          "07:00 AM", "07:15 AM", "07:30 AM", "07:45 AM",
          "08:00 AM", "08:15 AM", "08:30 AM", "08:45 AM",
          "09:00 AM", "09:15 AM", "09:30 AM", "09:45 AM",
          "10:00 AM", "10:15 AM", "10:30 AM", "10:45 AM",
          "11:00 AM", "11:15 AM", "11:30 AM", "11:45 AM",
          "12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM",
          "01:00 PM", "01:15 PM", "01:30 PM", "01:45 PM",
          "02:00 PM", "02:15 PM", "02:30 PM", "02:45 PM",
          "03:00 PM", "03:15 PM", "03:30 PM", "03:45 PM",
          "04:00 PM", "04:15 PM", "04:30 PM", "04:45 PM",
          "05:00 PM", "05:15 PM", "05:30 PM", "05:45 PM",
          "06:00 PM", "06:15 PM", "06:30 PM", "06:45 PM",
          "07:00 PM", "07:15 PM", "07:30 PM", "07:45 PM",
          "08:00 PM", "08:15 PM", "08:30 PM", "08:45 PM",
          "09:00 PM", "09:15 PM", "09:30 PM", "09:45 PM",
        ]
      };
    },
    methods: {
      addSchedule() {
        this.schedules.push({ days: [], startTime: "", endTime: "", workout: "" });
      },
      removeSchedule(index) {
        this.schedules.splice(index, 1);
      },
      handleSignUp() {
        if (!this.email || !this.password || !this.name) {
          alert("Please fill in all required fields.");
          return;
        }
  
        let users = JSON.parse(localStorage.getItem("allUsers")) || [];
        if (users.some(user => user.email === this.email)) {
          alert("User with this email already exists.");
          return;
        }
  
        let newUser = {
          name: this.name,
          email: this.email,
          password: this.password,
          description: this.description,
          schedules: this.schedules
        };
  
        users.push(newUser);
        localStorage.setItem("allUsers", JSON.stringify(users));
  
        alert("Account created successfully! Redirecting to login...");
        window.location.href = "index.html"; // 🔄 Redirect to Login
      },
      goToLogin() {
        window.location.href = "index.html"; // 🔄 Redirect to Login Page
      }
    }
  };
  </script>