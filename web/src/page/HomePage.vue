<template>
  <!-- ルーター -->
  <v-btn @click="router.push('/works')">APIコレクション</v-btn>
  <v-btn @click="router.push('/aboutme')">自己紹介</v-btn>
  <v-btn>お問い合わせ</v-btn>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import ApplicationBar from '@/component/thing/ApplicationBar.vue'

/** ルーター */
const router = useRouter()

</script>

<style>
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