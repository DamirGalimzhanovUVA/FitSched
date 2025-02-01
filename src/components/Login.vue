<template>
    <div class="login-container">
      <h2>Login</h2>
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label for="email">Email:</label>
          <input v-model="email" type="email" id="email" required />
        </div>
        
        <div class="input-group">
          <label for="password">Password:</label>
          <input v-model="password" type="password" id="password" required />
        </div>
  
        <button type="submit">Login</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        email: '',
        password: '',
        errorMessage: ''
      };
    },
    methods: {
      handleLogin() {
        if (this.email === 'admin@example.com' && this.password === 'password123') {
          this.$emit('login-success'); // Notify parent
        } else {
          this.errorMessage = 'Invalid credentials';
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .login-container {
    width: 300px;
    margin: 100px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    text-align: center;
  }
  
  .input-group {
    margin-bottom: 15px;
  }
  
  input {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  button {
    width: 100%;
    padding: 10px;
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
  </style>