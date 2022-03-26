<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'HkGit',
  data() {
    return {
      url: 'https://github.com/Trappist-One'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
