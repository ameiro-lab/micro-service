<template>
  <v-app>
    <ApplicationBar />
    <v-main class="mt-2 mx-5">
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup>
import ApplicationBar from '@/component/thing/ApplicationBar.vue';
</script>
