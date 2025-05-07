<template>
  <v-app>
    <ApplicationBar />
    <v-main class="px-5">
      <div style="height: calc(100vh - 50px);" class="pt-5">
        <router-view />
      </div>
    </v-main>
  </v-app>
</template>

<script setup>
import ApplicationBar from '@/component/thing/ApplicationBar.vue';
</script>
