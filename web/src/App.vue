<template>
  <v-app>
    <router-view></router-view>
  </v-app>
</template>

<script setup>

</script>

<style>
* {
  font-family: 'Poppins', sans-serif !important;
}
/* 25対75のレイアウト */
.layout-20-80 {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
}
.layout-20-80 .content-20 {
  width: 20%;
  min-width: 150px;
  max-width: 20%;
}
.layout-20-80 .content-80 {
  width: 80%;
}
</style>