<template>
    <div>
      <h2>Welcome to the Dashboard</h2>
      <button @click="logout">Logout</button>
    </div>
  </template>
  
  <script>
  import { useRouter } from 'vue-router';
  
  export default {
    setup() {
      const router = useRouter();
  
      const logout = () => {
        router.push('/');
      };
  
      return { logout };
    }
  };
  </script>