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
        
        <!-- Multi-Select for Days of the Week -->
        <div class="multi-select">
          <div class="selected-options" @click="toggleDropdown(index)">
            <span v-if="schedule.days.length === 0">Select Days</span>
            <span v-for="day in schedule.days" :key="day" class="selected-day">
              {{ day }} <button @click.stop="removeDay(index, day)">❌</button>
            </span>
          </div>
          <div v-if="dropdowns[index]" class="dropdown">
            <label v-for="day in availableDays" :key="day">
              <input type="checkbox" :value="day" v-model="schedule.days"> {{ day }}
            </label>
          </div>
        </div>
  
        <!-- Start Time Dropdown (Military Time) -->
        <select v-model="schedule.startTime">
          <option v-for="time in timeSlots" :key="time" :value="time">{{ time }}</option>
        </select>
  
        <span>-</span>
  
        <!-- End Time Dropdown (Military Time) -->
        <select v-model="schedule.endTime">
          <option v-for="time in timeSlots" :key="time" :value="time">{{ time }}</option>
        </select>
  
        <!-- Workout Type Dropdown -->
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
        dropdowns: [],
        availableDays: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
        timeSlots: [
          "06:00", "06:15", "06:30", "06:45",
          "07:00", "07:15", "07:30", "07:45",
          "08:00", "08:15", "08:30", "08:45",
          "09:00", "09:15", "09:30", "09:45",
          "10:00", "10:15", "10:30", "10:45",
          "11:00", "11:15", "11:30", "11:45",
          "12:00", "12:15", "12:30", "12:45",
          "13:00", "13:15", "13:30", "13:45",
          "14:00", "14:15", "14:30", "14:45",
          "15:00", "15:15", "15:30", "15:45",
          "16:00", "16:15", "16:30", "16:45",
          "17:00", "17:15", "17:30", "17:45",
          "18:00", "18:15", "18:30", "18:45",
          "19:00", "19:15", "19:30", "19:45",
          "20:00", "20:15", "20:30", "20:45",
          "21:00", "21:15", "21:30", "21:45",
          "22:00", "22:15", "22:30", "22:45",
          "23:00", "23:15", "23:30", "23:45"
        ]
      };
    },
    methods: {
      addSchedule() {
        this.schedules.push({ days: [], startTime: "", endTime: "", workout: "" });
        this.dropdowns.push(false);
      },
      removeSchedule(index) {
        this.schedules.splice(index, 1);
        this.dropdowns.splice(index, 1);
      },
      removeDay(scheduleIndex, day) {
        this.schedules[scheduleIndex].days = this.schedules[scheduleIndex].days.filter(d => d !== day);
      },
      toggleDropdown(index) {
        this.dropdowns = this.dropdowns.map((val, i) => (i === index ? !val : false));
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
        window.location.href = "index.html";
      },
      goToLogin() {
        window.location.href = "index.html";
      }
    }
  };
  </script>
  
  <style scoped>
  /* Ensure dropdowns display correctly */
  .multi-select {
    position: relative;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 6px;
    background: white;
    width: 180px;
    cursor: pointer;
  }
  
  .selected-options {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
  }
  
  .selected-day {
    background: #42b983;
    color: white;
    padding: 4px;
    border-radius: 4px;
    display: flex;
    align-items: center;
  }
  
  .selected-day button {
    background: transparent;
    border: none;
    color: white;
    cursor: pointer;
    margin-left: 5px;
  }
  
  .dropdown {
    position: absolute;
    top: 100%;
    left: 0;
    background: white;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 5px;
    width: 100%;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2);
    z-index: 10;
  }
  
  .dropdown label {
    display: block;
    cursor: pointer;
    padding: 4px;
  }
  
  .dropdown label:hover {
    background: #f0f0f0;
  }
  </style>