<template>
  <v-btn-toggle
    v-model="mode"
    color="green"
    rounded="0"
    group>
    <v-btn value="en">English</v-btn>
    <v-btn value="ja">日本語</v-btn>
  </v-btn-toggle>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useI18n } from 'vue-i18n'

const { t, locale } = useI18n()
const mode = ref(localStorage.getItem('lang') || 'ja') // 初期値をローカルストレージから取得

// 言語が変わったら i18n に反映し、保存もする
watch(mode, (newLang) => {
  locale.value = newLang
  localStorage.setItem('lang', newLang)
})
</script>
