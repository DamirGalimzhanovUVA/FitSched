<template>
  <div class="login-container">
    <h2>Sign Up</h2>
    <input v-model="name" placeholder="Full Name">
    <input v-model="email" placeholder="Email">
    <input v-model="password" type="password" placeholder="Password">
    <h3>Set Your Schedule</h3>
    <Schedule 
      v-for="(schedule, index) in schedules" 
      :key="index" 
      :schedule="schedule"
      :availableDays="availableDays"
      :timeSlots="timeSlots"
      @update-schedule="updateSchedule(index, $event)"
      @remove-schedule="removeSchedule(index)"
      @remove-day="removeDay(index, $event)"
    />

    <button @click="addSchedule" class="add-btn">➕ Add Schedule</button>
    <button @click="handleSignUp">Register</button>
    <button @click="goToLogin">Back to Login</button>
  </div>
</template>

<script>
import Schedule from './Schedule.vue';

export default {
  components: { Schedule },

  data() {
    return {
      name: "",
      email: "",
      password: "",
      schedules: [],
      availableDays: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
      timeSlots: [
        "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45",
        "08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45",
        "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
        "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45",
        "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45",
        "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45",
        "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45",
        "20:00", "20:15", "20:30", "20:45", "21:00", "21:15", "21:30", "21:45",
        "22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45"
      ]
    };
  },
  methods: {
    addSchedule() {
      this.schedules.push({ days: [], startTime: "", endTime: "", workout: "", description: "" });
    },
    updateSchedule(index, updatedSchedule) {
      this.schedules[index] = updatedSchedule;
    },
    removeSchedule(index) {
      this.schedules.splice(index, 1);
    },
    removeDay(scheduleIndex, day) {
      this.schedules[scheduleIndex].days = this.schedules[scheduleIndex].days.filter(d => d !== day);
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
        schedules: this.schedules
      };

      users.push(newUser);
      localStorage.setItem("allUsers", JSON.stringify(users));

      alert("Account created successfully! Redirecting to the main app...");
      window.location.href = "main_app.html";
    },
    goToLogin() {
      window.location.href = "login.html";
    }
  }
};
</script>