<template>
    <div class="w-96 mx-auto my-12 p-6 border border-gray-300 rounded-lg text-center">
      <h2 class="text-xl font-bold mb-4">Find Workout Partners</h2>
  
      <div v-if="matchingUsers.length > 0">
        <ul>
          <li v-for="user in matchingUsers" :key="user.email" class="flex justify-between items-center border-b py-2">
            <div>
              <p class="font-semibold">{{ user.name }}</p>
              <p class="text-sm">Schedule: {{ formatSchedule(user.schedules) }}</p>
            </div>
            <button @click="sendRequest(user.email)" class="bg-blue-500 text-white px-3 py-1 rounded-md">
              Send Request
            </button>
          </li>
        </ul>
      </div>
  
      <div v-else>
        <p>No matching users found.</p>
      </div>
  
      <button @click="logout" class="bg-red-500 text-white px-3 py-2 rounded-md mt-4">Logout</button>
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
  
        // Ensure the current user is saved in allUsers
        const existingUser = this.allUsers.find(user => user.email === this.currentUser.email);
        if (!existingUser) {
          this.allUsers.push(this.currentUser);
          localStorage.setItem('allUsers', JSON.stringify(this.allUsers));
        }
      },
      findMatchingUsers() {
        if (!this.currentUser) return;
        
        this.matchingUsers = this.allUsers.filter(user => {
          if (user.email === this.currentUser.email) return false; // Skip self
  
          return user.schedules.some(schedule => 
            this.currentUser.schedules.some(mySchedule => 
              mySchedule.workout === schedule.workout &&
              mySchedule.startTime === schedule.startTime &&
              mySchedule.endTime === schedule.endTime &&
              mySchedule.days.some(day => schedule.days.includes(day))
            )
          );
        });
      },
      sendRequest(email) {
        let requests = JSON.parse(localStorage.getItem('requests')) || [];
        requests.push({ from: this.currentUser.email, to: email });
        localStorage.setItem('requests', JSON.stringify(requests));
        alert("Request sent!");
      },
      formatSchedule(schedules) {
        return schedules.map(s => `${s.workout} (${s.startTime} - ${s.endTime}) on ${s.days.join(", ")}`).join(" | ");
      },
      logout() {
        localStorage.removeItem('user');
        this.$router.push('/');
      }
    }
  };
  </script>