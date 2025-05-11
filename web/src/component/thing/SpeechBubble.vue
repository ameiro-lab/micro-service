<!-- Bubble.vue 　position: relative;　の親要素で囲むこと -->
<template>
  <div v-if="isSpeechBuble"
    :class="['speech-bubble', positionClass]"  :style="baseStyle">
    <div ref="slotContent"><!-- ※スロットの高さを検知 -->
      {{ message }}
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { gsap } from 'gsap'

// propsの定義
const props = defineProps({
  position: {
    type: String,
    default: 'top', // top, bottom, left, right
  },
  color: {
    type: String,
    default: '#8B5E3C', // primary
  },
})

// リアクティブデータの定義
const message = ref('');
const isSpeechBuble = ref(false)
const slotContent = ref(null)    // slot内の要素のDOM　※使ってないけど高さ取得したい時用

/**
 * 吹き出しを表示するメソッド
 */
const showBubble = async (pram) => {

  message.value = pram;   // メッセージを設定する
  isSpeechBuble.value = true  // 吹き出しを表示する

  await nextTick() // 後続の処理で要素を取得する為、DOMの更新を待つ(GSAPなど)
  
  /**  スロットの高さを取得 
   * let num = slotContent.value.offsetHeight 
   * console.log("スロットの高さ", num);
  */

  // ふわっとアニメーション
  gsap.fromTo(
    '.speech-bubble',
    { opacity: 0, y: 10 },  // 10 下から 0 の位置へ
    { opacity: 1, y: 0, duration: 0.4, ease: 'power2.out' }
  )
}

/**
 * computed
 */
// 矢印に応じたクラス名を付与する
const positionClass = computed(() => {
  return `arrow-${props.position}`
})

// 吹き出しの基本スタイルを設定
const baseStyle = computed(() => {
  let baseStyle = {
    'background': props.color,      // 吹き出し背景色
     '--arrow-color': props.color,  // 矢印の色
  }

  // 吹き出しの位置と矢印のスタイルを変更
  switch (props.position) {
    // 上向きの矢印の場合、
    case 'top':
    default:
      baseStyle = {
        ...baseStyle,
        top: '100%', /* ターゲット要素の下に表示 */
        left: '50%',
        transform: 'translateX(-50%)',
      }
      break
    // 下向きの矢印の場合、
    case 'bottom':
      baseStyle = {
        ...baseStyle,
        top: '-30%', /* ターゲット要素の上に表示 */
        left: '50%',
        transform: 'translateX(-50%)',
      }
      break
  }
  
  return baseStyle
})

// 初期表示
onMounted(async () => {

})

// メソッドの公開
defineExpose({
  showBubble,
})
</script>

<style scoped>
.speech-bubble {
  position: absolute;
  padding: 12px;
  color: #FFF;
  border-radius: 10px;
  max-width: 600px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-size: 14px;
  line-height: 1.4;
  z-index: 10;
  white-space: normal;
}

.speech-bubble::after {
  content: "";
  position: absolute;
  border: 8px solid transparent;
}

/* 上に矢印 */
.arrow-bottom::after {
  top: 100%;
  left: 50%;
  transform: translateX(-50%);
  border-top-color:  var(--arrow-color);;
}

/* 下に矢印 */
.arrow-top::after {
  bottom: 100%;
  left: 50%;
  transform: translateX(-50%);
  border-bottom-color:  var(--arrow-color);;
}
</style>