<template>
    <div class="schedule-group">
      <!-- Multi-Select for Days of the Week -->
      <div class="multi-select">
        <div class="selected-options" @click="toggleDropdown">
          <span v-if="schedule.days.length === 0">Select Days</span>
          <span v-for="day in schedule.days" :key="day" class="selected-day">
            {{ day }} <button @click.stop="removeDay(day)">❌</button>
          </span>
        </div>
        <div v-if="dropdownVisible" class="dropdown">
          <label v-for="day in availableDays" :key="day">
            <input type="checkbox" :value="day" v-model="schedule.days" @change="emitUpdate" />
            {{ day }}
          </label>
        </div>
      </div>
  
      <!-- Start Time Dropdown -->
      <select v-model="schedule.startTime" @change="emitUpdate">
        <option v-for="time in timeSlots" :key="time" :value="time">{{ time }}</option>
      </select>
  
      <span>-</span>
  
      <!-- End Time Dropdown -->
      <select v-model="schedule.endTime" @change="emitUpdate">
        <option v-for="time in timeSlots" :key="time" :value="time">{{ time }}</option>
      </select>
  
      <!-- Workout Type Dropdown -->
      <select v-model="schedule.workout" @change="emitUpdate">
        <option>Basketball</option>
        <option>Tennis</option>
        <option>Weights</option>
        <option>General Cardio</option>
      </select>
  
      <!-- Workout Description (Same Line as Other Attributes) -->
      <input v-model="schedule.description" placeholder="Workout details" @input="emitUpdate" class="description-input">
  
      <button @click="removeSchedule" class="remove-btn">❌</button>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      schedule: Object,
      availableDays: Array,
      timeSlots: Array
    },
    data() {
      return { dropdownVisible: false };
    },
    methods: {
      toggleDropdown() {
        this.dropdownVisible = !this.dropdownVisible;
      },
      removeDay(day) {
        this.$emit("remove-day", day);
      },
      emitUpdate() {
        this.$emit("update-schedule", this.schedule);
      },
      removeSchedule() {
        this.$emit("remove-schedule");
      }
    }
  };
  </script>
  
  <style scoped>
  .schedule-group {
    display: flex;
    align-items: center;
    gap: 10px;
    flex-wrap: wrap;
  }
  
  .description-input {
    width: 200px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  </style>