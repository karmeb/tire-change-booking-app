<template>
  <form @submit.prevent="applyFilters"
        class="filter-container is-vcentered is-flex-direction-row is-justify-content-flex-end mr-4"
        style="gap: 10px">
    <div class="field">
      <label class="label">Workshops</label>
        <multiselect v-model="selectedWorkshops"
                     :options="workshops"
                     :multiple="true"
                     :close-on-select="false"
                     :clear-on-select="false"
                     :preserve-search="false"
                     placeholder="Select workshop(s)"
                     :preselect-first="false"
                     :searchable="true"
                     :allow-empty="true">
        </multiselect>
    </div>
    <div class="field">
      <label class="label">Vehicle types</label>
            <multiselect v-model="selectedVehicleTypes"
                         :options="vehicleTypes"
                         :multiple="true"
                         :close-on-select="false"
                         :clear-on-select="false"
                         :preserve-search="false"
                         placeholder="Select vehicle type(s)"
                         :preselect-first="false"
                         :searchable="true"
                         :allow-empty="true">
            </multiselect>
    </div>
    <div class="field">
      <label class="label">Date range</label>
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
    <div class="field">
        <button class="button is-link form-submit-button">Filter available times</button>
    </div>
  </form>

</template>

<script>
import BookingService from "@/service/BookingService";
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import Util from "@/util/Util";
import Multiselect from 'vue-multiselect'


export default {
  components: { VueDatePicker, Multiselect },
  data() {
    return {
      selectedWorkshops: [],
      selectedVehicleTypes: [],
      workshops: [],
      vehicleTypes: [],
      dateRange: null,
    }
  },
  methods: {
    applyFilters() {
      this.$emit('filter-applied', {
        workshopNames:  this.selectedWorkshops,
        machineType: this.selectedVehicleTypes,
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
@import '~vue-multiselect/dist/vue-multiselect.min.css';
.form-submit-button{
  margin-top: 20px;
}

.filter-container.is-vcentered {
  display: flex;
  flex-wrap: wrap;
  align-content: center;
  align-items: center;
}

</style>