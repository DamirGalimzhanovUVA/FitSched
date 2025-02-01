<template>
  <div class="login-container">
    <h2 v-if="!isSignUp">Login</h2>
    <h2 v-else>Sign Up</h2>

    <form @submit.prevent="handleSubmit">
      <div class="input-group">
        <label for="email">Email:</label>
        <input v-model="email" type="email" required />
      </div>

      <div class="input-group">
        <label for="password">Password:</label>
        <input v-model="password" type="password" required />
      </div>

      <div v-if="isSignUp">
        <div class="input-group">
          <label for="name">Name:</label>
          <input v-model="name" type="text" required />
        </div>

        <h3>Workout Schedule</h3>
        <div v-for="(schedule, index) in schedules" :key="index" class="schedule-group">
          
          <!-- Multi-Select Dropdown for Days -->
          <div class="input-group">
            <label>Days:</label>
            <div class="dropdown" @click="toggleDropdown(index)">
              <div class="dropdown-header">
                <span v-if="schedule.days.length === 0">Select Days</span>
                <span v-else>{{ schedule.days.join(', ') }}</span>
              </div>
              <ul v-if="activeDropdown === index" class="dropdown-menu">
                <li v-for="day in daysOfWeek" :key="day" @click.stop="toggleDay(schedule, day)">
                  <input type="checkbox" :checked="schedule.days.includes(day)" />
                  {{ day }}
                </li>
              </ul>
            </div>
          </div>

          <!-- Start Time Dropdown -->
          <div class="input-group">
            <label>Start Time:</label>
            <select v-model="schedule.startTime">
              <option v-for="time in timeIntervals" :key="time" :value="time">
                {{ time }}
              </option>
            </select>
          </div>

          <span>-</span>

          <!-- End Time Dropdown -->
          <div class="input-group">
            <label>End Time:</label>
            <select v-model="schedule.endTime">
              <option v-for="time in timeIntervals" :key="time" :value="time">
                {{ time }}
              </option>
            </select>
          </div>

          <!-- Description Field -->
          <div class="input-group">
            <label>Description:</label>
            <input v-model="schedule.description" type="text" placeholder="Workout Description" required />
          </div>

          <!-- Workout Type Dropdown -->
          <div class="input-group">
            <label>Workout Type:</label>
            <select v-model="schedule.workout">
              <option value="Basketball">Basketball</option>
              <option value="Tennis">Tennis</option>
              <option value="Weights">Weights</option>
              <option value="General Cardio">General Cardio</option>
            </select>
          </div>

          <button type="button" class="remove-btn" @click="removeSchedule(index)">❌</button>
        </div>

        <button type="button" class="add-btn" @click="addSchedule">➕ Add Schedule</button>
      </div>

      <button type="submit">{{ isSignUp ? 'Sign Up' : 'Login' }}</button>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>

    <p>
      <span v-if="!isSignUp">Don't have an account?</span>
      <span v-else>Already have an account?</span>
      <button class="toggle-btn" @click="isSignUp = !isSignUp">
        {{ isSignUp ? 'Login here' : 'Sign up here' }}
      </button>
    </p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isSignUp: false,
      email: '',
      password: '',
      name: '',
      schedules: [],
      activeDropdown: null, // Tracks which dropdown is open
      daysOfWeek: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
      timeIntervals: this.generateTimeIntervals(),
      errorMessage: ''
    };
  },
  methods: {
    handleSubmit() {
      if (this.isSignUp) {
        this.handleSignUp();
      } else {
        this.handleLogin();
      }
    },
    handleLogin() {
      const storedUser = JSON.parse(localStorage.getItem('user'));

      if (storedUser && storedUser.email === this.email && storedUser.password === this.password) {
        alert("Login successful!");
      } else {
        this.errorMessage = "Invalid credentials";
      }
    },
    handleSignUp() {
      if (this.email && this.password && this.name && this.schedules.length > 0) {
        const newUser = {
          email: this.email,
          password: this.password,
          name: this.name,
          schedules: this.schedules
        };
        localStorage.setItem('user', JSON.stringify(newUser));
        alert(`Account created for ${this.name}! You can now log in.`);
        this.isSignUp = false;
      } else {
        this.errorMessage = "All fields and at least one schedule are required!";
      }
    },
    addSchedule() {
      this.schedules.push({
        workout: "Basketball",
        startTime: "06:00 AM",
        endTime: "07:00 AM",
        days: [],
        description: ""
      });
    },
    removeSchedule(index) {
      this.schedules.splice(index, 1);
    },
    toggleDropdown(index) {
      this.activeDropdown = this.activeDropdown === index ? null : index;
    },
    toggleDay(schedule, day) {
      const index = schedule.days.indexOf(day);
      if (index === -1) {
        schedule.days.push(day);
      } else {
        schedule.days.splice(index, 1);
      }
    },
    generateTimeIntervals() {
      let times = [];
      let hour = 0;
      let minutes = 0;

      while (hour < 24) {
        let ampm = hour < 12 ? "AM" : "PM";
        let formattedHour = hour % 12 || 12;
        let formattedMinutes = minutes === 0 ? "00" : "15";
        times.push(`${formattedHour}:${formattedMinutes} ${ampm}`);

        minutes += 15;
        if (minutes >= 60) {
          minutes = 0;
          hour++;
        }
      }
      return times;
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  text-align: center;
}

.input-group {
  margin-bottom: 15px;
  text-align: left;
}

input, select {
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 8px;
  margin-top: 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #38a172;
}

.error {
  color: red;
  margin-top: 10px;
}

.schedule-group {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 5px;
  background: #f9f9f9;
  border-radius: 5px;
  margin-bottom: 5px;
  justify-content: space-between;
}

.remove-btn {
  background-color: red;
}

.add-btn {
  background-color: blue;
}
</style>