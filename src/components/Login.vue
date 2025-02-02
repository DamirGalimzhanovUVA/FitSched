<template>
  <div class="login-container">
    <h2>Login</h2>
    <input v-model="email" placeholder="Email">
    <input v-model="password" type="password" placeholder="Password">
    <button @click="handleLogin">Login</button>

    <p>Don't have an account?</p>
    <button @click="goToSignUp">Sign Up</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    handleLogin() {
      const storedUsers = JSON.parse(localStorage.getItem('allUsers')) || [];
      const user = storedUsers.find(u => u.email === this.email && u.password === this.password);

      if (user) {
        localStorage.setItem('user', JSON.stringify(user));
        window.location.href = "find-partners.html"; // 🔄 Redirect to Find Partners
      } else {
        alert("Invalid credentials");
      }
    },
    goToSignUp() {
      window.location.href = "signup.html"; // 🔄 Redirect to Sign-Up Page
    }
  }
};
</script>