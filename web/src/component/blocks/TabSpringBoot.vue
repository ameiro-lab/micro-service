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
        @click="onClickItem(item.value)"
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
          <v-icon color="primary">mdi-paw</v-icon>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script setup>
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'
import api from '@/api/api'

/** plugins */
const { t } = useI18n()


/** リアクティブデータの定義 */
const treasures = [
  {
    value: 'login',
    title: '認証システム',
    releaseDate: t('releaseDate', { year: '2025', month: '3' }),
    text: 'Spring Securityを使用したJWT認証によるログイン機能です。このサイトで実際に運用中で、マイクロサービスに対応させました。',
    hasDemo: false,
  },
  {
    value: 'import',
    title: 'CSVインポート機能',
    releaseDate:  t('releaseDate', { year: '2025', month: '3' }),
    text: 'ログインIDとパスワードが記載されたcsvファイルをインポートします。パスワードはハッシュ化してPostgreSQLに登録されます。',
    hasDemo: true,
  },
  {
    value: '',
    title: '10文字以内',
    releaseDate:  t('releaseDate', { year: 'yyyy', month: 'mm' }),
    text: 'This card is',
    hasDemo: false,
  },
]
const hoverStates = ref(treasures.map(function() {  // ホバー状態の管理
  return false; // 初期値は全てfalse　例：ref([false, false, false]);
}));

/** メソッドの定義 */
const onClickItem = (value) => {

  if (value === 2 ) {
    userImport();

  } else {
    return;
  }
}

// CSVインポート機能
const userImport = async () => {

  console.log("CSVインポート機能、実行");
  // try {
  //   const res = await api.userImport();
  //   const { status, data, message } = res.data;

  //   if (status === 200) {
  //     console.log("Success:", data);
  //     text.value = message;
  //   } else if (status === 401) {
  //     console.error("Error:", message);
  //   }

  // } catch (error) {
  //   console.error("Error:", error);
  // }
}

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