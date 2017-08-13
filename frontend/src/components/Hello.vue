<template>
  <div class="query">
    <p>Enter lat/lon coordinates below</p>
    <div>
      From:
      <input v-model="topLeft.lat">
      <input v-model="topLeft.lon">
    </div>
    <div>
      To:
      <input v-model="bottomRight.lat">
      <input v-model="bottomRight.lon">
    </div>
    <button v-on:click="getBoundaries">
      Go
    </button>
    <ul>
      <li v-for="boundary in boundaries">
        {{ boundary }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'query',
  data: function () {
    return {
      topLeft: {
        lat: 47.294134,
        lon: 14.430542
      },
      bottomRight: {
        lat: 54.438103,
        lon: 6.849976
      },
      boundaries: []
    }
  },
  methods: {
    getBoundaries: function () {
      let endpoint = `http://localhost:7070/boundaries?topLeft=${this.topLeft.lat},${this.topLeft.lon}&bottomRight=${this.bottomRight.lat},${this.bottomRight.lon}`
      this.$http.get(endpoint).then(function (response) {
        this.boundaries = response.body
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
