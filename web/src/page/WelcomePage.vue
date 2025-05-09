<template>
  <!-- wellcome -->
  <v-row dense>
    <v-col cols="12">
      <v-card color="green" variant="outlined">
        <v-card-title class="text-h5 font-weight-bold">{{ t('welcome.title') }}</v-card-title>
        <v-card-text class="text-body-1">
          <span
            v-for="(line, index) in formattedText"
            :key="index"
            class="formatted-line">{{ line }}</span>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col cols="12">
      <CoreButton :label="t('welcome.btn')" @click="callMugi" />
    </v-col>
  </v-row>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import CoreButton from '@/component/thing/CoreButton.vue';

/** plugins */
const router = useRouter()
const { t, locale } = useI18n()

// i18nでメッセージを取得し、改行を処理
const formattedText = ref(formatText(t('welcome.text', { me: 'xxx' })))

// メニューページに遷移する
function callMugi() {
  router.push({ name: 'home' });
}

// 改行文字を変換
function formatText(text) {
  return text.split('\n');
}

</script>

<style scoped>
.formatted-line {
  display: block; /* 改行をブロック要素として表示 */
  margin-bottom: 0.5rem; /* 行間を調整 */
}
</style>
