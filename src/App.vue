<template>
  <div>
    <Login v-if="!isLoggedIn" @login-success="isLoggedIn = true" />
    <div v-if="isLoggedIn">
      <h2>Welcome to the Dashboard</h2>
      <button @click="isLoggedIn = false">Logout</button>
    </div>
  </div>
</template>

<script>
import Login from './components/Login.vue';

export default {
  components: { Login },
  data() {
    return {
      isLoggedIn: false
    };
  }
};
</script>