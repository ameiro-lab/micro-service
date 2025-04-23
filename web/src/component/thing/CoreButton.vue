<template>
    <div class="div__design">
        <button class="button_design"
            @click="onClick"
        >{{ label }}</button>
    </div>
</template>

<script setup>

/** props */
defineProps({
  label: String
});

/** emit */
const emit = defineEmits(["click"]);

/** メソッド */
const onClick = () => {
  emit("click", "Message from HelloWorld!");
};

</script>

<style>
/** 基本デザイン */
.div__design .button_design {
    position: relative; /** 疑似要素の基準点 */
    display: flex;    /** 中の要素を横並び */
    justify-content: space-around;
    align-items: center;
    margin: 0 auto;
    /* max-width: auto; */
    padding: 10px 25px;
    color: #191970;    /** 文字色 */
    transition: 0.3s ease-in-out; /** すべての変化を0.3秒で滑らかに */
    font-weight: 500; /** フォントの太さ */
}
/** 背景の設定 */
.div__design .button_design::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  background-color: rgba(25, 25, 112, 0.3); /** 背景 */
  transition: all 0.3s;
}
/** ホバー時の動作 */
.div__design .button_design:hover::before {
  opacity: 0;                 /** 背景を透明化 */
  transform: scale(0.4, 0.4); /** 背景を縮小 */
}

/** 外枠の設定 */
.div__design .button_design::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  opacity: 0;
  transition: all 0.3s;
  border: 2px solid rgb(25, 25, 112, .4);   /** 外枠 */
  transform: scale(1.2, 1.2);
}
/** ホバー時の動作 */
.div__design .button_design:hover::after {
  opacity: 1;
  transform: scale(1, 1);
}

/** クリック時の動作 */
.div__design .button_design:active {
    transform: translateY(3px); /* ボタンを下に押し込む */
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2); /* 影を弱める */
}

</style>