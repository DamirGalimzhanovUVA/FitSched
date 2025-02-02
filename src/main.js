import { createApp } from 'vue';
import FindPartners from './components/FindPartners.vue';
import Requests from './components/Requests.vue'

createApp(FindPartners).mount('#find_partners');
createApp(Requests).mount('#requests')