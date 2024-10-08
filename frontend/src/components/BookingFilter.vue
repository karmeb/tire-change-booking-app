<template>

  <form @submit.prevent="applyFilters" class="container is-flex is-flex-direction-row is-justify-content-flex-end padding my-2" style="gap:10px">
    <div class="field is-flex is-flex-direction-column">
      <label class="label">Workshop</label>
      <div class="control">
          <div class="select">
            <select v-model="selectedWorkshops">
              <option></option>
              <option v-for="workshop in workshops" :key="workshop">
                {{workshop}}
              </option>
            </select>
          </div>
      </div>
    </div>
    <div class="field is-flex is-flex-direction-column">
      <label class="label">Machine Type</label>
      <div class="control">
        <div class="select">
          <select v-model="selectedMachineTypes">
            <option></option>
            <option v-for="type in vehicleTypes" :key="type">
              {{type}}
            </option>
          </select>
        </div>
      </div>
    </div>
<!--    <div class="field is-flex is-flex-direction-column">
      <label class="label">Time from</label>
      <div class="control">
        <div class="select">
          <select v-model="timeFrom" >
            <option>2024-10-17</option>
          </select>
        </div>
      </div>
    </div>-->
    <div class="field is-flex is-flex-direction-column">
      <div class="control">
        <div class="select">
          <VueDatePicker
              v-model="dateRange"
              :auto-apply="true"
              range
              :enable-time-picker="false"
              :clearable="false"
              placeholder="Select date range"
              :min-date="new Date()"
              required>
          </VueDatePicker>
        </div>
      </div>
    </div>
    <div class="field is-align-content-center">
      <div class="control">
        <button class="button is-link">Filter available times</button>
      </div>
    </div>
  </form>

</template>

<script>
import BookingService from "@/service/BookingService";
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import Util from "@/util/Util";


export default {
  components: {VueDatePicker},
  data() {
    return {
      selectedWorkshops: [],
      selectedMachineTypes: [],
      workshops: [],
      vehicleTypes: [],
      dateRange: null,
    }
  },
  methods: {
    applyFilters() {
      this.$emit('filter-applied', {
        workshopNames:  this.selectedWorkshops,
        machineType: this.selectedMachineTypes,
        timeFrom: Util.formatDateToString(this.dateRange[0]),
        timeTo: Util.formatDateToString(this.dateRange[1]),
      })
    },
    async fetchAvailableWorkshopNames() {
      try {
        const response = await BookingService.getAvailableWorkshops();
        this.workshops = response.data;
      } catch (err) {
        console.error("error fetching workshops: ", err);
      }
    },
    async fetchSupportedVehicleTypes() {
      try {
        const response = await BookingService.getSupportedVehicleTypes();
        this.vehicleTypes = response.data;
      } catch (err) {
        console.error("error fetching vehicle types: ", err);
      }
    }
  },
  mounted() {
    this.fetchSupportedVehicleTypes();
    this.fetchAvailableWorkshopNames();
  }
}
</script>

<style>

</style>