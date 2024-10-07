<template>

  <div class="container is-flex is-flex-direction-row is-justify-content-flex-end padding my-2" style="gap:10px">
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
    <div class="field is-flex is-flex-direction-column">
      <label class="label">Time from</label>
      <div class="control">
        <div class="select">
          <select v-model="timeFrom" >
            <option>2024-10-17</option>
          </select>
        </div>
      </div>
    </div>
    <div class="field is-flex is-flex-direction-column">
      <label class="label">Time to</label>
      <div class="control">
        <div class="select">
          <select v-model="timeTo">
            <option>2024-10-20</option>
          </select>
        </div>
      </div>
    </div>
    <div class="field is-align-content-center">
      <div class="control">
        <button @click="applyFilters" class="button is-link">Filter available times</button>
      </div>
    </div>
  </div>

</template>

<script>
import BookingService from "@/service/BookingService";

export default {
  data() {
    return {
      selectedWorkshops: [],
      selectedMachineTypes: [],
      timeFrom: '',
      timeTo: '',
      workshops: [],
      vehicleTypes: [],
    }
  },
  methods: {
    applyFilters() {
      this.$emit('filter-applied', {
        workshopNames:  this.selectedWorkshops,
        machineType: this.selectedMachineTypes,
        timeFrom: this.timeFrom,
        timeTo: this.timeTo,
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
    },
  },
  mounted() {
    this.fetchSupportedVehicleTypes();
    this.fetchAvailableWorkshopNames();
  }
}
</script>

<style>

</style>