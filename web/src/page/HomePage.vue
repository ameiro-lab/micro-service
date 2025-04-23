<template>
  <!-- ルーター -->
  <v-btn @click="router.push('/aboutme')">自己紹介</v-btn>
  <p></p>
  <v-btn @click="router.push('/works')">APIコレクション</v-btn>
  <p></p>
  <v-btn>お問い合わせ</v-btn>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

/** ルーター */
const router = useRouter()

</script>

<style>

</style>