<template>
  <v-row>
    <v-col v-for="(item, index) in treasures" :key="item.value"
      cols="12" md="4"
      class="d-flex">
        <v-card
          class="cardContainer px-2"
          variant="outlined"
          elevation="2"
          rounded="xl"
          color="primary"
          :style="getCardStyle(index)"
          @mouseenter="hoverStates[index] = true"
          @mouseleave="hoverStates[index] = false"
          >
          <v-card-title class="text-h6 font-weight-bold">
            {{ item.title }}
          </v-card-title>
          <v-card-text class="text-body-2 text-grey-darken-2 flex-grow-1">
            {{ item.text }}
          </v-card-text>
  
          <v-divider></v-divider>
  
          <v-card-actions class="justify-end">
            <div class="text-caption text-grey-darken-1 mt-2">
              {{ item.releaseDate }}
            </div>
            <v-btn icon color="primary" variant="tonal">
              <v-icon>mdi-paw</v-icon>
            </v-btn>
          </v-card-actions>
          
        </v-card>
    </v-col>
  </v-row>
</template>

<script setup>
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'
import api from '@/api/api'
import HoverImage from '@/assets/bg-img/bg_yabu.jpg'

/** plugins */
const { t } = useI18n()

/** リアクティブデータの定義 */
const treasures = [
  {
    value: 'resume',
    title: '履歴書メーカー',
    releaseDate:  t('releaseDate', { year: '2024', month: '12' }),
    text: '入力フォームに必要事項を記入するだけで、PDF形式の履歴書を自動生成できます。【デモあり】',
    hasDemo: true,
  },
  {
    value: 'mail',
    title: 'お問い合わせ機能',
    releaseDate:  t('releaseDate', { year: '2025', month: '6' }),
    text: 'reCAPTCHAやレート制限を導入したセキュアなお問い合わせ機能。このサイトで実際に運用中で、TLS対応で通信も安全です。',
    hasDemo: true,
  },
    {
    value: '1',
    title: 'シンプル',
    releaseDate:  t('releaseDate', { year: 'yyyy', month: 'mm' }),
    text: 'or style.',
    hasDemo: true,
  },
]
const hoverStates = ref(treasures.map(function() {  // ホバー状態の管理
  return false; // 初期値は全てfalse　例：ref([false, false, false]);
}));

/** メソッドの定義 */
// カードのスタイルを設定する
function getCardStyle(index) {
  const isHover = hoverStates.value[index];

  return {
    backgroundColor: isHover ? "#F5E1C8" : "transparent",
    transform: isHover ? "translateY(2px)" : "translateY(0)",
    transition: "transform 0.2s ease, background-color 0.2s ease"
    // backgroundImage: isHover ? `url(${HoverImage})` : "none",
    // backgroundSize: "cover",
    // backgroundPosition: "center",
    // backgroundRepeat: "no-repeat"
  };
}

</script>

<style scope>
.cardContainer {
  height: 100%;            /* 親要素の高さに合わせる（v-colのh-100と連携） */
  width: 100%;             /* 横幅いっぱいに広げる */
  display: flex;           /* Flexレイアウトを有効に */
  flex-direction: column;  /* 子要素を縦に並べる */
}
</style>