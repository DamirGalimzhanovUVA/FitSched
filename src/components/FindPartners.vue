<template>
  <div class="page-container">
    <h2>Find Workout Partners</h2>

    <div v-if="matchingUsers.length > 0">
      <ul>
        <li v-for="user in matchingUsers" :key="user.email">
          <p><strong>{{ user.name }}</strong></p>
          <p>{{ formatSchedule(user.schedules) }}</p>
          <p><em>{{ user.description }}</em></p>
          <button @click="sendRequest(user.email)">Send Request</button>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No workout partners available.</p>
    </div>

    <button @click="logout">Logout</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentUser: null,
      matchingUsers: []
    };
  },
  mounted() {
    this.loadUsers();
    this.mockMatchingUsers(); // Using mock data for now
  },
  methods: {
    loadUsers() {
      this.currentUser = JSON.parse(localStorage.getItem("user"));

      if (!this.currentUser) {
        window.location.href = "login.html"; // 🔄 Redirect to login if not logged in
      }
    },
    mockMatchingUsers() {
      // Simulated list of gym partners
      this.matchingUsers = [
        {
          name: "Alex Johnson",
          email: "alex.johnson@example.com",
          schedules: [
            { day: "Monday", start: "6:00 AM", end: "7:30 AM" },
            { day: "Wednesday", start: "6:30 PM", end: "8:00 PM" }
          ],
          description: "Early bird lifter, focusing on strength training."
        },
        {
          name: "Jordan Lee",
          email: "jordan.lee@example.com",
          schedules: [
            { day: "Tuesday", start: "5:30 PM", end: "7:00 PM" },
            { day: "Thursday", start: "5:30 PM", end: "7:00 PM" }
          ],
          description: "Passionate about HIIT and endurance workouts."
        },
        {
          name: "Samantha Rivera",
          email: "samantha.rivera@example.com",
          schedules: [
            { day: "Monday", start: "7:00 PM", end: "8:30 PM" },
            { day: "Friday", start: "6:00 AM", end: "7:30 AM" }
          ],
          description: "Loves a mix of cardio and strength training."
        },
        {
          name: "David Chen",
          email: "david.chen@example.com",
          schedules: [
            { day: "Wednesday", start: "7:00 AM", end: "8:30 AM" },
            { day: "Saturday", start: "10:00 AM", end: "11:30 AM" }
          ],
          description: "Enjoys powerlifting and improving mobility."
        }
      ];
    },
    formatSchedule(schedules) {
      return schedules.map(s => `${s.day}: ${s.start} - ${s.end}`).join(", ");
    },
    sendRequest(email) {
      let requests = JSON.parse(localStorage.getItem("requests")) || [];
      requests.push({ from: this.currentUser.email, to: email, status: "pending" });
      localStorage.setItem("requests", JSON.stringify(requests));
      alert(`Request sent to ${email}!`);
    },
    logout() {
      localStorage.removeItem("user");
      window.location.href = "login.html"; // 🔄 Redirect to login
    }
  }
};
</script>