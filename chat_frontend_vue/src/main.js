import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import store from './store'

Vue.config.productionTip = false
Vue.prototype.axios = axios
axios.defaults.baseURL = "http://localhost:8080"
axios.defaults.withCredentials = true;
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
