<template>
  <div class="modal is-active">
    <div class="modal-background"></div>
    <div class="modal-content">
      <div class="box">
        <h3 class="has-text-centered title is-4">Book time: {{booking.time}}</h3>
        <div class="modal-flex-container is-flex is-justify-content-center">
          <div class="booking-details is-flex is-flex-direction-column is-justify-content-center">
              <p class="title is-6">Workshop details</p>
              <p>Name: <b>{{booking.workshopName}}</b></p>
              <p>Vehicles served: {{booking.vehicleTypes.toString()}}</p>
            <p class="m-0 p-0">
          <span class="icon has-text-info">
            <font-awesome-icon :icon="faMap()" />
          </span>
              {{booking.workshopAddress}}</p>
          </div>
          <form @submit.prevent="submitBooking" class="contact-info-form">
            <div class="field">
              <label class="label">Name</label>
              <div class="control">
                <input v-model="contactInfo.name" class="input" type="text" placeholder="John Doe" required minlength="2">
              </div>
            </div>
            <div class="field">
              <label class="label">E-mail</label>
              <div class="control">
                <input v-model="contactInfo.email" class="input" type="email" placeholder="example@example.com" required minlength="3">
              </div>
            </div>
            <div class="field">
              <label class="label">Phone number</label>
              <div class="control">
                <input v-model="contactInfo.phone" class="input" type="tel" placeholder="+372 5555555" required minlength="5" pattern="[+][0-9]+">
              </div>
            </div>
            <div class="control has-text-centered">
              <button class="button is-link" type="submit" :disabled="isSubmitting">Book</button>
            </div>
          </form>
        </div>
        <div v-if="errorMessage">
          <p class="has-text-centered has-text-weight-medium has-text-danger">{{errorMessage}}</p>
        </div>
        <div v-if="successMessage">
          <p class="has-text-centered has-text-weight-medium has-text-success">{{successMessage}}</p>
        </div>
      </div>
    </div>
    <button @click="closeModal" class="modal-close is-large" aria-label="close"></button>
  </div>
</template>

<script>
import bookingService from "@/service/BookingService";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faMap} from "@fortawesome/free-regular-svg-icons";

export default {
  components: {FontAwesomeIcon},
  props: {
    booking: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      contactInfo: {
        name: '',
        email: '',
        phone: '',
      },
      successMessage: null,
      errorMessage: null,
      isSubmitting: false,
    }
  },
  methods: {
    faMap() {
      return faMap
    },
    closeModal() {
      this.$emit('close');
      this.$emit('update');
    },
    async submitBooking() {
      try {
        this.isSubmitting = true;
        const {id, workshopName, time} = this.booking;
        const contactInfoCombined = `name: ${this.contactInfo.name}, email: ${this.contactInfo.email}, phone: ${this.contactInfo.phone}`;

        await bookingService.bookTime(id, contactInfoCombined, workshopName);

        this.successMessage = `Time: ${time} to workshop ${workshopName} booked successfully`;
        this.errorMessage = null;
        this.clearForm();

      } catch (err) {
        console.log(err)
        this.errorMessage = `Failed to book time. Please choose another time or try again later.`
        this.successMessage = null;
      }
    },
    clearForm() {
      this.contactInfo.name = '';
      this.contactInfo.email = '';
      this.contactInfo.phone = '';
    }
  }
}
</script>

<style>
  .modal-flex-container {
    gap: 20px;
  }
</style>