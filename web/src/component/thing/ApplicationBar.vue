<template>
  <v-app-bar
    app
    :elevation="2" color="primary" density="compact">
    <v-app-bar-title>{{ title }}</v-app-bar-title>
    <template v-slot:prepend>
      <v-app-bar-nav-icon
        icon="mdi-bone"
        @click="isDrawer = !isDrawer" />
    </template>
    <template v-slot:append>
      <!-- <v-spacer></v-spacer>
      <LangChangeButton color="primary" /> -->
    </template>
  </v-app-bar>

  <!-- サイドメニュー（TO DO：背景画像を設定する） -->
  <v-navigation-drawer
    temporary
    v-model="isDrawer"
    location="left">
    <!-- アイコン -->
    <v-list-item
      lines="two"
      prepend-avatar="https://github.com/ameiro-lab.png"
      title="Ameiro-Lab"
      subtitle="portfolio"  />
    <v-divider></v-divider>
    <!-- アイテム -->
    <v-list density="compact" nav>
      <v-list-item v-for="item in drawerMenuList" :key="item.value"
        :prepend-icon="item.icon"
        :title="item.title"
        :value="item.value"
        @click="onItemClick(item)"  />
    </v-list>
  </v-navigation-drawer>
</template>

<script setup>
import { ref } from 'vue'
import api from '@/api/api'
import { useRouter } from 'vue-router'
import LangChangeButton from '@/component/thing/LangChangeButton.vue';

/** ルーター */
const router = useRouter()

/** リアクティブデータの定義 */
const title = ref('Tech Pawprints');
const isDrawer = ref(false); 
const drawerMenuList = [
  { title: 'Home', value: 'home', icon: 'mdi-home-city' },
  { title: 'About Me', value: 'aboutme', icon: 'mdi-face-woman-profile' },
  { title: 'Works', value: 'works', icon: 'mdi-laptop-account' },
  { title: 'Contact', value: 'contact', icon: 'mdi-earth' },
]

/** メソッドの定義 */
// メニューアイテム押下時
function onItemClick(item) {
  // ルーターの遷移
  router.push(item.value) // drawerMenuList内のvalueを指定
}

</script>