<template>
  <div class="section">

    <div v-if="error">
      <p class="has-text-centered has-text-weight-medium has-text-danger">{{error}}</p>
    </div>

    <div v-if="!loading && !error && !availableTimes.length">
      <p class="as-text-centered has-text-weight-medium has-text-info">
        No times available!
      </p>
    </div>

    <div v-if="!loading && !error && availableTimes.length" class="columns is-multiline is-centered">
      <div v-for="time in availableTimes" :key="time.id">
        <div class="column"><TimeCard :booking="time" @update="handleBookingUpdate"></TimeCard></div>
      </div>
    </div>
  </div>
</template>

<script>
import TimeCard from "@/components/TimeCard.vue";
import bookingService from "@/service/BookingService";
import BookingTimeDetails from "@/models/BookingTimeDetails";

export default {
  components: {
    TimeCard
  },
  props: {
    filters: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      availableTimes: [],
      loading: false,
      error: null
    }
  },
  watch: {
    filters: {
      handler: 'fetchAvailableTimes',
      immediate: true,
    }
  },
  methods: {
    async fetchAvailableTimes() {
      this.loading = true;
      this.error = null;

      try {
        const response = await bookingService.getAvailableTimes(
            this.filters.timeFrom,
            this.filters.timeTo,
            this.filters.workshopNames,
            this.filters.machineType,
        );

        const responseData = response.data.length ? response.data : [];
        this.availableTimes = responseData.map(item => new BookingTimeDetails (
            item.id,
            item.workshopName,
            item.workshopAddress,
            item.vehicleTypes,
            item.time,
        ))
      } catch (err) {
        this.error = 'Failed to load available times. Please try again later.'
      } finally {
        this.loading = false;
      }
    },
    handleBookingUpdate() {
      this.fetchAvailableTimes()
    }
  }
}

</script>
