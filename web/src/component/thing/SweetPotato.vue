<template>
  <div style="width: 300px; height: auto; position: relative;" class="overlay-center">
    <img id="imo-before" src="@/assets/png/imo_before.png" style="position: absolute; top: 0; left: 0; opacity: 0; width: 250px;" />
    <img id="imo-after" src="@/assets/svg/imo_after.svg" style="position: absolute; top: 0; left: 0; opacity: 0;" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { animateFadeInFrom, animationBounce } from '@/plugins/animations';


/**
 * メソッドの定義
 */
// イモの出現
const show = async () => {
  
  const imoBefore = document.getElementById('imo-before');
  const imoAfter = document.getElementById('imo-after');

  try {
    // 1つ目のアニメーション
    await animateFadeInFrom(imoBefore, 'top');

    // 画像を切り替え
    await new Promise(resolve => setTimeout(resolve, 600));

    // 1つ目の画像を非表示に (opacity 0)
    imoBefore.style.opacity = '0';
    imoBefore.style.visibility = 'hidden'; // visibilityで非表示にして、再表示時にリセットできるようにする

    // 2つ目の画像を表示
    imoAfter.style.opacity = '1';
    imoAfter.style.visibility = 'visible'; // visibilityを表示に戻す

    // 2つ目のアニメーション
    await animationBounce(imoAfter);

    // 再度imoBeforeを表示させる
    imoBefore.style.visibility = 'visible';  // 次の準備のため

  } catch (error) {
    console.error('アニメーションエラー:', error);
  }

}

// メソッドの公開
defineExpose({
  show,
})

</script>

<style>
.overlay-center {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 9999;
  pointer-events: none; /* マウスイベントNG */
}

/** 
中央に置く構文
position: fixed;
top: 50%;
left: 50%;
transform: translate(-50%, -50%);
*/
</style>