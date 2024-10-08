<template>
  <BookingNavBar></BookingNavBar>
  <BookingFilter @filter-applied="updateFilters" ></BookingFilter>
  <TimeCardsSection v-if="filters" :filters="filters"></TimeCardsSection>
</template>

<script>
import BookingNavBar from './components/BookingNavBar.vue'
import BookingFilter from './components/BookingFilter.vue'
import TimeCardsSection from "@/components/TimeCardsSection.vue";
import Util from "@/util/Util";

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
        timeFrom: Util.formatDateToString(new Date()),
        timeTo: Util.formatDateToString(Util.getFutureDate(14)),
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


