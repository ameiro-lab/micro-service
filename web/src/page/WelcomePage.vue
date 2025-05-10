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

  <!-- public/imo_before.svg からイモ画像を参照 -->
  <v-row>
    <v-spacer></v-spacer>
      <div style="width: 300px; height: auto; position: relative;">
        <img id="imo-before" src="@/assets/svg/imo_before.svg" style="position: absolute; top: 0; left: 0; visibility: visible;" />
        <img id="imo_after" src="@/assets/svg/imo_after.svg" style="position: absolute; top: 0; left: 0; visibility: hidden;" />
      </div>
    <v-spacer></v-spacer>
  </v-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import CoreButton from '@/component/thing/CoreButton.vue';
import { animateFlyAcrossScreen, animationBounce } from '@/plugins/animations';

/** plugins */
const router = useRouter()
const { t, locale } = useI18n()

// i18nでメッセージを取得し、改行を処理
const formattedText = ref(formatText(t('welcome.text')))

onMounted(() => {
  
});

// メニューページに遷移する
function callMugi() {
  const imoBefore = document.getElementById('imo-before');
  const imoAfter = document.getElementById('imo_after');

  // アニメーションを実行
  animationBounce(imoBefore, 0.7)
    .then(() => {
      // アニメーションが完了した後、画像を変更
      imoBefore.style.visibility = 'hidden';  // imo-beforeを非表示
      imoAfter.style.visibility = 'visible';  // imo_afterを表示
    })
    .then(() => {
      // imo_after に対して再度アニメーションを実行
      return animationBounce(imoAfter, 0.7);  // Promiseを返す
    })
    .then(() => {
      // アニメーション後にメニューページに遷移
      router.push({ name: 'home' });
    })
    .catch((error) => {
      // エラーハンドリング
      console.error('Animation error:', error);
    });
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
