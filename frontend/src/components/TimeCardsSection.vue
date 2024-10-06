<template>

<!--  <div class="column" v-for="item in items">
    {{ item.message }}
  </div>-->

  <div class="section columns is-multiline is-centered">
    <div v-for="item in items" :key="item">
      <div class="column"><TimeCard></TimeCard></div>
    </div>
  </div>
</template>

<script>
import TimeCard from "@/components/TimeCard.vue";

export default {
  components: {
    TimeCard
  },
  data() {
    return {
      items: [1,2,3,4,5,6,7,8,9]
    }
  }
}

</script>
