<template>
  <!-- イモ -->
  <SweetPotato ref="sweetPotato" />  
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
      <CoreButton @click="callMugi" :label="t('welcome.btn')"  />
    </v-col>
  </v-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import CoreButton from '@/component/thing/CoreButton.vue';
import SweetPotato from '@/component/thing/SweetPotato.vue';

/** plugins */
const router = useRouter()
const { t } = useI18n()

/** DOM参照 */
const sweetPotato = ref({});

// i18nでメッセージを取得し、改行を処理
const formattedText = ref(formatText(t('welcome.text')))

onMounted(() => {
  
});

// メニューページに遷移する
const callMugi = async () => {

  try {
    
    // イモを出す
    await sweetPotato.value.show();

    // ページ遷移
    router.push({ name: 'home' });

  } catch (error) {
    console.error('アニメーションエラー:', error);
  }
}

// 改行文字を変換
function formatText(text) {
  return text.split('\n');
}

</script>

<style>
.formatted-line {
  display: block; /* 改行をブロック要素として表示 */
  margin-bottom: 0.5rem; /* 行間を調整 */
}

/* #imo-before {
  position: relative; または absolute。top が効くように
  top: -100px; 初期位置
  visibility: hidden;
} */
</style>
