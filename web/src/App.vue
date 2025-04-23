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
.layout-25-75 {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
}
.layout-25-75 .content-25 {
  width: 25%;
  min-width: 150px;
  max-width: 25%;
}
.layout-25-75 .content-75 {
  width: 75%;
}
</style>