<template>
  <div class="card">
    <div class="card-content">
      <div class="content">
        <p class="m-0 p-0 card-text has-text-weight-semibold">Workshop: London</p>
        <p class="m-0 p-0">
          <span class="icon has-text-info">
            <font-awesome-icon :icon="faClock" />
          </span>11:09 PM - 1 Jan 2016</p>
        <p class="m-0 p-0"><span class="icon has-text-info">
            <font-awesome-icon :icon="faMap" />
          </span>
          14 Bury New Rd, Manchester</p>
        <p class="m-0 p-0"> Vehicle types: car, bike </p>
      </div>
    </div>
    <footer class="card-footer">
      <a href="#" class="card-footer-item">Book</a>
    </footer>
  </div>
</template>
<script setup>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faClock, faMap, } from "@fortawesome/free-regular-svg-icons";
</script>

<style>
</style>