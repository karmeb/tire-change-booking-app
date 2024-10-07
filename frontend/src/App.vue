<template>
  <BookingNavBar></BookingNavBar>
<!--  <h1 class="is-primary title is-3 has-text-centered has-text-weight-semibold is-family-code has-text-primary">
    Book Tire Change Time
  </h1>-->
  <BookingFilter @filter-applied="updateFilters" ></BookingFilter>
  <TimeCardsSection v-if="filters" :filters="filters"></TimeCardsSection>
</template>

<script>
import BookingNavBar from './components/BookingNavBar.vue'
import BookingFilter from './components/BookingFilter.vue'
import TimeCardsSection from "@/components/TimeCardsSection.vue";

export default {
  name: 'App',
  components: {
    BookingNavBar,
    BookingFilter,
    TimeCardsSection,
  },
  data () {
    return {
      filters: {
        workshopNames: [],
        machineType: [],
        timeFrom: '2024-10-07',
        timeTo: '2024-10-17',
      }
    }
  },
  methods: {
    updateFilters(newFilters) {
      this.filters = newFilters;
    }
  }
}
</script>


