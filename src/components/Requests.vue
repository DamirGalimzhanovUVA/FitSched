<template>
  <div class="page-container">
    <h2>📩 Requests</h2>

    <!-- Pending Requests -->
    <div v-if="pendingRequests.length > 0">
      <h3>Pending Requests</h3>
      <ul>
        <li v-for="(request, index) in pendingRequests" :key="index">
          <p><strong>{{ request.from }}</strong> wants to join your workout</p>
          <p>🏋️ Type: {{ request.workoutType }}</p>
          <p>📅 Schedule: {{ formatSchedule(request.schedule) }}</p>
          <button @click="acceptRequest(index)">✅ Accept</button>
          <button @click="rejectRequest(index)">❌ Reject</button>
        </li>
      </ul>
    </div>

    <!-- Accepted Requests -->
    <div v-if="acceptedRequests.length > 0">
      <h3>Accepted Requests</h3>
      <ul>
        <li v-for="(request, index) in acceptedRequests" :key="index">
          <p><strong>{{ request.from }}</strong> is now your workout partner! 🎉</p>
          <p>🏋️ Type: {{ request.workoutType }}</p>
          <p>📅 Schedule: {{ formatSchedule(request.schedule) }}</p>
          <p>📧 Contact: <strong>{{ request.contact }}</strong></p> <!-- Contact now visible -->
        </li>
      </ul>
    </div>

    <div v-if="pendingRequests.length === 0 && acceptedRequests.length === 0">
      <p>No pending or accepted requests.</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      requests: []
    };
  },
  computed: {
    pendingRequests() {
      return this.requests.filter(req => req.status === "pending");
    },
    acceptedRequests() {
      return this.requests.filter(req => req.status === "accepted");
    }
  },
  mounted() {
    this.loadRequests();
  },
  methods: {
    loadRequests() {
      let storedRequests = JSON.parse(localStorage.getItem("requests"));
      
      if (!storedRequests || storedRequests.length === 0) {
        // Mock data with contacts (hidden until accepted)
        storedRequests = [
          {
            from: "Alex Johnson",
            contact: "alex.johnson@example.com",
            workoutType: "Strength Training",
            schedule: [
              { day: "Monday", start: "6:00 AM", end: "7:30 AM" },
              { day: "Wednesday", start: "6:30 PM", end: "8:00 PM" }
            ],
            status: "pending"
          },
          {
            from: "Jordan Lee",
            contact: "jordan.lee@example.com",
            workoutType: "HIIT",
            schedule: [
              { day: "Tuesday", start: "5:30 PM", end: "7:00 PM" },
              { day: "Thursday", start: "5:30 PM", end: "7:00 PM" }
            ],
            status: "pending"
          },
          {
            from: "Samantha Rivera",
            contact: "samantha.rivera@example.com",
            workoutType: "Cardio & Strength Mix",
            schedule: [
              { day: "Monday", start: "7:00 PM", end: "8:30 PM" },
              { day: "Friday", start: "6:00 AM", end: "7:30 AM" }
            ],
            status: "pending"
          }
        ];
        localStorage.setItem("requests", JSON.stringify(storedRequests));
      }

      this.requests = [...storedRequests];
    },
    formatSchedule(schedule) {
      return schedule.map(s => `${s.day}: ${s.start} - ${s.end}`).join(", ");
    },
    acceptRequest(index) {
      this.requests[index].status = "accepted";
      localStorage.setItem("requests", JSON.stringify(this.requests));
      alert(`You accepted the request from ${this.requests[index].from}!`);
      this.loadRequests();
    },
    rejectRequest(index) {
      this.requests[index].status = "rejected";
      localStorage.setItem("requests", JSON.stringify(this.requests));
      alert(`You rejected the request from ${this.requests[index].from}.`);
      this.loadRequests();
    }
  }
};
</script>