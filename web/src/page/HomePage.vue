<template>
  <div class="home-container">
    <div
      ref="slideInDiv"
      class="circle">
      <MugiShit @on-click="onclickMugiShit" />
      <!-- 吹き出し -->
      <div class="speech-bubble text-center">
        {{ speechBubbleText }}
      </div>
    </div>

    <!-- 円形に配置される選択肢 -->
    <div class="circle option-circle"
      v-for="(item, index) in filteredItems"
      :key="item.value"
      :ref="el => circleRefs[index] = el"
      @click="onGo(item.value)"
      @mouseenter="speechBubbleText = item.text"
      @mouseleave="speechBubbleText = t('home.message.default')"
      >
      {{ t('home.' + item.value) }}
    </div>
  </div>

</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useDisplay } from 'vuetify'
import { gsap } from 'gsap'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import MugiShit from '@/component/thing/MugiShit.vue';
import SpeechBubble from '@/component/thing/SpeechBubble.vue'

/** ルーター */
const router = useRouter()
const { t, locale } = useI18n()

// 状態管理
const slideInDiv = ref(null);
const circleRefs = [] // 各円形ボタンの要素参照を格納する配列

const speechBubbleText = ref(t('home.message.init'));
const itemList = [
  {
    value: 'none',
    title: 'No Title',
    text: t('home.message.none'),
  },
  {
    value: 'aboutme',
    text: t('home.message.aboutme'),
  },
  {
    value: 'works',
    text: t('home.message.works'),
  },
  {
    value: 'contact',
    text: t('home.message.contact'),
  },
  {
    value: 'logout',
    text: t('home.message.logout'),
  },
];

// Vuetifyのレスポンシブ情報を取得
const { mobile } = useDisplay()
// スマホ画面では一部アイテムを除外
const filteredItems = computed(() =>
mobile.value
    ? itemList.filter(item => item.value !== 'none' && item.value !== 'logout')
    : itemList
)

onMounted(() => {
  spreadMenu();
  slideInCharacter();
})

/**
 * キャラを下から表示するアニメーション
 */
function slideInCharacter() {
  gsap.fromTo(
    slideInDiv.value,
    { y: 100, opacity: 0 }, // 初期位置：下から100px、透明
    { y: 0, opacity: 1, duration: 1, ease: "power2.out" } // 最終位置：通常の位置、1秒間でアニメーション
  );
}

/**
 * 5つの選択肢を円形に展開するアニメーション
 */
function spreadMenu() {

  const radius = 180 // 円形配置の半径（中心からの距離）
  // const angleIncrement = (Math.PI * 2) / itemList.length // 各選択肢の間隔（角度）
  const angleIncrement = (Math.PI * 2) / 8

  // 真上に配置されるサークルを調整
  // - Math.PI / 2 は12時の方向（上）を基準
  let angleOffset;
  if (!mobile.value) {
    angleOffset = -Math.PI / 2 - angleIncrement * 2 // PCは丸が5個なので、2つ分の角度をオフセット 
    
  } else {
    angleOffset = -Math.PI / 2 - angleIncrement // PCは丸が3個なので、そのまま12時の方向 
  }

  const centerX = window.innerWidth / 2 // 画面中央のX座標
  const centerY = window.innerHeight / 2 // 画面中央のY座標

  // 各選択肢の位置を計算し、アニメーションを実行　circleRefsで取得した要素をループ
  circleRefs.forEach((el, i) => {
    // 各選択肢の角度（位置）を計算
    const angle = i * angleIncrement + angleOffset
    const x = Math.cos(angle) * radius // X座標の計算
    const y = Math.sin(angle) * radius // Y座標の計算

    // GSAPを使用したアニメーション
    gsap.fromTo(
      el, // アニメーション対象の要素
      {
        x: 0, // 初期位置X（中央）
        y: 0, // 初期位置Y（中央）
        opacity: 0, // 初期状態：透明
        scale: 0.5, // 初期状態：縮小
      },
      {
        x, // 計算した位置へ移動
        y, // 計算した位置へ移動
        opacity: 1, // 完全に表示
        scale: 1, // 通常のサイズに戻す
        duration: 0.8, // アニメーション時間
        ease: 'back.out(1.7)', // イージング（少し跳ねるような動き）
        delay: i * 0.05, // 各選択肢が少しずつアニメーション開始
      }
    )
  })
}

/**
 * 各選択肢の円形ボタンクリック時
 * @param {string} item - クリックされた要素
 */
function onGo(value) {
  // 未実装のため
  if(value === 'none-1' || value === 'none-2') {
    return;
  }
  router.push(value);
}

const onclickMugiShit = () => {
  console.log('Osuwari clicked!')
}

</script>


<style scoped>
.home-container {
  position: relative; /* 子要素の絶対配置の基準点 */
  width: 100%; /* ビューポート幅いっぱいに広がる */
  height: 100%; /* ツールバーを差し引いて、ビューポート高さいっぱいに広がる */
  /* background: #f0f4f8;  */
  /* overflow: hidden; はみ出た部分を隠す */
  display: flex; /* フレックスボックスレイアウト */
  align-items: center; /* 縦方向中央揃え */
  justify-content: center; /* 横方向中央揃え */
}

.circle {
  position: absolute; /* 絶対配置 */
  display: flex; /* フレックスボックスレイアウト */
  cursor: pointer; /* カーソルをポインターに変更してクリック可能なことを示す */
  width: 200px;
  height: auto;
}

.option-circle {
  opacity: 0; /* 初期状態では透明（非表示） */
  pointer-events: auto; /* クリックイベントを有効化 */
  width: 100px; /* 円の幅 */
  height: 100px; /* 円の高さ */
  background: #ff8bc3; /* 背景の色 */
  border-radius: 50%; /* 円形にする */
  color: white; /* 白色のテキスト */
  font-weight: bold; /* 太字テキスト */
  align-items: center; /* テキスト縦方向中央揃え */
  justify-content: center; /* テキスト横方向中央揃え */
}
.option-circle:hover {
  background: orange; /* ホバー時の背景色 */
}

.speech-bubble {
  position: absolute;
  top: 100%; /* ターゲット要素の下に表示 */
  left: 50%;
  transform: translateX(-50%);
  background: #8B5E3C;
  color: #FFF;
  padding: 12px;
  width: 200px; /* 幅を1000pxに設定 */
  max-width: 100%; /* 親要素の幅を超えないように設定（レスポンシブ対応） */
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-size: 14px;
  line-height: 1.4;
  z-index: 10;
  white-space: normal;
}

/* 吹き出しの矢印（上に向けて表示） */
.speech-bubble::after {
  content: "";
  position: absolute;
  bottom: 100%; /* 吹き出しの上に矢印 */
  left: 50%;
  transform: translateX(-50%);
  border: 8px solid transparent;
  border-bottom-color: #8B5E3C; /* 上に向けるために bottom に色をつける */
}

</style>