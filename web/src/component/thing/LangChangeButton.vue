<template>
  <v-btn-toggle
    v-model="mode"
    :color="color"
    rounded="0"
    group
  >
    <v-btn
      value="en"
      :disabled="mode === 'en'"
    >English</v-btn>

    <v-btn
      value="ja"
      :disabled="mode === 'ja'"
    >日本語</v-btn>
  </v-btn-toggle>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useI18n } from 'vue-i18n'

const { locale } = useI18n()
const mode = ref(localStorage.getItem('lang') || 'ja')

// 言語が変わったら反映＆保存
watch(mode, (newLang) => {
  if (['ja', 'en'].includes(newLang)) {
    locale.value = newLang
    localStorage.setItem('lang', newLang)
  } else {
    console.warn('言語変換エラー:', newLang)
  }
})

defineProps({
  color: {
    type: String,
    default: 'green' // デフォルト色（任意）
  }
})

</script>
