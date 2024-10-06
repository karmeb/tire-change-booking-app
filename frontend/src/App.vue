<template>
  <BookingNavBar></BookingNavBar>
<!--  <h1 class="is-primary title is-3 has-text-centered has-text-weight-semibold is-family-code has-text-primary">
    Book Tire Change Time
  </h1>-->
  <BookingFilter></BookingFilter>
  <TimeCardsSection></TimeCardsSection>
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
    TimeCardsSection
  }
}



</script>


