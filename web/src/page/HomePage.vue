<template>
  <v-row class="card-row">
    <!-- カードをv-colで囲んでグリッドシステムを適用 -->
    <v-col class="card-column"
      v-for="(item, index) in cardItemList" :key="item.value"
      cols="12" md="6" lg="4">
      <!-- カード -->
      <v-card class="card-content" @click="onGo(item)"
        :style="getCardStyle(index)" color="black"
        hover
        @mouseenter="hoverStates[index] = true"
        @mouseleave="hoverStates[index] = false">
        <div class="card-inner">
          <div class="text-h5 font-weight-bold">
            {{ item.title }}
          </div>
          <div v-for="(line, i) in formatText(item.text)" :key="i">
            {{ line }}
          </div>
          <div class="button-container">
            <v-btn
              :text="`Go to ${item.buttonText}`"
              append-icon="mdi-chevron-right"
              :color="index === 0 
                        ? 'accent' 
                        : (index % 2 === 0 ? 'green' : 'base')"
              variant="flat"
              block />
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import bgYabu from '@/assets/bg-img/bg_yabu.jpg'
import guideMugi from '@/assets/bg-img/hover.jpg'
import bgSougen from '@/assets/bg-img/bg_sougen.jpg'
import bgYozora from '@/assets/bg-img/bg_yozora.jpg'
import bgMoon from '@/assets/bg-img/bg_moon.jpg'
import bgOshiro from '@/assets/bg-img/bg_oshiro.jpg'

/** ルーター */
const router = useRouter()

/** データの定義 */
const cardItemList = [
  {
    value: 'aboutme',
    title: 'Paws up and nice to meet you!',
    text: 'Sniff sniff... who’s that?\nI’m so happy to meet you!\nWould you like to know what my human is up to?\nCome check it out!',
    buttonText: 'About Me',
    image: bgOshiro,
    hoverImage: guideMugi,
  },
  {
    value: 'works',
    title: 'Buried Treasures of My Code!',
    text: 'Would you like to see what I’ve dug up?\nSome cool tech treasures await—go on, take a peek!\nFrom back-end magic to front-end gems, it’s all here!',
    buttonText: 'Works',
    image: bgYabu,
    hoverImage: guideMugi,
  },
  {
    value: 'contact',
    title: 'Need Help?',
    text: 'Do you have a question or a request?\nNo worries—I’ll fetch it to my human right away!\nJust leave a message, and I’ll make sure they hear you.\nTail wagging all the way! 🐾',
    buttonText: 'Contact',
    image: bgSougen,
    hoverImage: guideMugi,
  },
  {
    value: 'home',
    title: 'No Title',
    text: 'Coming Soon...',
    buttonText: '???',
    image: bgYozora,
    hoverImage: guideMugi,
  },
  {
    value: 'home',
    title: 'No Title',
    text: 'Coming Soon...',
    buttonText: '???',
    image: bgMoon,
    hoverImage: guideMugi,
  },
];
// ホバー状態の管理
const hoverStates = ref(cardItemList.map(function() {
  return false; // 初期値は全てfalse
// 例：cardItemListが3つの要素を持っている場合
// const hoverStates = ref([false, false, false]);
}));

/** メソッドの定義 */
// 画面遷移
function onGo(item) {
  // Contactは未実装のため
  if(item.value === 'contact') {
    return;
  }
  router.push(item.value);
}

// 改行文字を変換
function formatText(text) {
  return text.split('\n');
}

// カードの背景画像を取得
function getCardStyle(index) {
  // 対象カードの取得
  const item = cardItemList[index];
  // hoverStates.value[index] の値を使って、ホバー状態をチェック
  // hoverStates.value[index] が true なら、ホバー時の画像（item.hoverImage）を使用
  // それ以外の場合は通常の画像（item.image）を使用
  const bgImage = hoverStates.value[index] ? item.hoverImage : item.image;
  return {
    backgroundImage: `url(${bgImage})`, // backgroundImage プロパティ
    backgroundSize: 'cover',            // 画像をカード全体にフィットさせる
    backgroundPosition: 'center',       // 画像の位置を中央に設定
    transition: 'background-image 0.3s ease', // 背景画像の遷移を滑らかに行う
  }
}

</script>

<style>
/* カード内のレイアウト調整 */
.card-inner {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 10px;
}
.button-container {
  margin-top: auto;
  padding-top: 16px;
}
/* PC表示（md以上）での行の高さ固定 */
@media (min-width: 960px) {
  .card-row {
    height: 300px;
    margin-top: 10px;
    margin-bottom: 10px;
  }
  .card-column {
    height: 100%;
  }
  .card-content {
    height: 100%;
  }
}
/* スマホ表示での調整 */
@media (max-width: 959px) {
  .card-content {
    height: auto;
    min-height: 250px; /* スマホでの最小高さ */
    display: flex;
    flex-direction: column;
  }
  .card-inner {
    min-height: 250px; /* 最小高さ */
    display: flex;
    flex-direction: column;
  }
}
/** ホバー時 */
</style>