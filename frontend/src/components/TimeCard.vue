<template>
  <div class="card">
    <div class="card-content">
      <div class="content">
        <p class="m-0 p-0 card-text has-text-weight-semibold">Workshop: {{booking.workshopName}}</p>
        <p class="m-0 p-0">
          <span class="icon has-text-info">
            <font-awesome-icon :icon="faClock()" />
          </span>{{booking.time}}</p>
        <p class="m-0 p-0">
          <span class="icon has-text-info">
            <font-awesome-icon :icon="faMap()" />
          </span>
          {{booking.workshopAddress}}</p>
        <p class="m-0 p-0"> Vehicles served: {{booking.vehicleTypes.toString()}}</p>
      </div>
    </div>
    <footer class="card-footer">
      <button @click="openModal" class="card-footer-item has-text-link">Book</button>
    </footer>
  </div>

  <BookingModal v-if="isModalOpen"
                :booking = "booking"
                @close="isModalOpen = false"
                @update="updateBookingList"
  ></BookingModal>


</template>
<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faClock, faMap, } from "@fortawesome/free-regular-svg-icons";
import BookingModal from "@/components/BookingModal.vue";

export default {
  name: 'TimeCard',
  components: {
    BookingModal,
    FontAwesomeIcon
  },
  methods: {
    faClock() {
      return faClock
    },
    faMap() {
      return faMap
    },
    openModal() {
      this.isModalOpen = true;
    },
    updateBookingList() {
      this.$emit('update')
    }
  },
  props: {
    booking: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      isModalOpen: false,
    }
  }
}
</script>

<style>
</style>