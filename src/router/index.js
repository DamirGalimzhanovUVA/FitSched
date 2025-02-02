import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/Login.vue';
import FindPartners from '../components/FindPartners.vue';

const routes = [
  { path: '/', component: Login },
  { path: '/find-partners', component: FindPartners }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;