<template>
  <div class="page-container">
    <h2>📩 Requests</h2>

    <!-- Pending Requests -->
    <div v-if="pendingRequests.length > 0">
      <h3>Pending Requests</h3>
      <ul>
        <li v-for="(request, index) in pendingRequests" :key="index">
          <p>{{ request.from }} wants to join your workout</p>
          <button @click="acceptRequest(index)">✅ Accept</button>
          <button @click="rejectRequest(index)">❌ Reject</button>
        </li>
      </ul>
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
    approvedRequests() {
      return this.requests.filter(req => req.status === "accepted");
    }
  },
  mounted() {
    this.loadRequests();
  },
  methods: {
    loadRequests() {
      let storedRequests = JSON.parse(localStorage.getItem("requests")) || [];
      this.requests = [...storedRequests];
    },
    acceptRequest(index) {
      this.requests[index].status = "accepted";
      localStorage.setItem("requests", JSON.stringify(this.requests));
      alert("You accepted the request!");
      this.loadRequests();
    },
    rejectRequest(index) {
      this.requests[index].status = "rejected";
      localStorage.setItem("requests", JSON.stringify(this.requests));
      alert("You rejected the request!");
      this.loadRequests();
    }
  }
};
</script>
