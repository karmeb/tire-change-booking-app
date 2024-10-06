<template>

  <div class="container is-flex is-flex-direction-row is-justify-content-flex-end padding my-2" style="gap:10px">
    <div class="field is-flex is-flex-direction-column">
      <label class="label">Workshop</label>
      <div class="control">
          <div class="select">
            <select>
              <option>London</option>
              <option>Manchester</option>
            </select>
          </div>
      </div>
    </div>
    <div class="field is-flex is-flex-direction-column">
      <label class="label">Machine Type</label>
      <div class="control">
        <div class="select">
          <select>
            <option>Car</option>
            <option>Truck</option>
          </select>
        </div>
      </div>
    </div>
    <div class="field is-flex is-flex-direction-column">
      <label class="label">Timeframe</label>
      <div class="control">
        <div class="select">
          <select>
            <option>Time TBD</option>
          </select>
        </div>
      </div>
    </div>
    <div class="field is-align-content-center">
      <div class="control">
        <button class="button is-link">Filter available times</button>
      </div>
    </div>
  </div>

</template>
<script setup>
</script>

<style>

</style>