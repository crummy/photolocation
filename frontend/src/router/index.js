import Vue from 'vue'
import Router from 'vue-router'
// import Hello from '@/components/Hello'
import Map from '@/components/Map'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Map',
      component: Map
    }
  ]
})
