<template>
  <div class="page-container">
    <h2>Find Workout Partners</h2>

    <div v-if="matchingUsers.length > 0">
      <ul>
        <li v-for="user in matchingUsers" :key="user.email">
          <p>{{ user.name }} - {{ formatSchedule(user.schedules) }}</p>
          <button @click="sendRequest(user.email)">Send Request</button>
        </li>
      </ul>
    </div>

    <button @click="logout">Logout</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentUser: null,
      allUsers: [],
      matchingUsers: []
    };
  },
  mounted() {
    this.loadUsers();
    this.findMatchingUsers();
  },
  methods: {
    loadUsers() {
      this.currentUser = JSON.parse(localStorage.getItem('user'));
      this.allUsers = JSON.parse(localStorage.getItem('allUsers')) || [];

      if (!this.currentUser) {
        window.location.href = "login.html"; // 🔄 Redirect to login if not logged in
      }
    },
    findMatchingUsers() {
      if (!this.currentUser) return;
      
      let requests = JSON.parse(localStorage.getItem("requests")) || [];
      this.matchingUsers = this.allUsers.filter(user => {
        if (user.email === this.currentUser.email) return false;
        return requests.some(req => req.from === this.currentUser.email && req.to === user.email && req.status === "accepted");
      });
    },
    sendRequest(email) {
      let requests = JSON.parse(localStorage.getItem("requests")) || [];
      requests.push({ from: this.currentUser.email, to: email, status: "pending" });
      localStorage.setItem("requests", JSON.stringify(requests));
      alert("Request sent!");
    },
    logout() {
      localStorage.removeItem("user");
      window.location.href = "login.html"; // 🔄 Redirect to login
    }
  }
};
</script>