<template>
  <v-toolbar color="primary" title="Home" />

  <div class="layout-25-75" style="width: 100%;">
    <!-- タブバー -->
    <v-tabs class="content-25"
      v-model="tab" direction="vertical">
      <v-tab v-for="item in tabsList" :key="item.value"
        :value="item.value" :text="item.text"
        :prepend-icon="item.icon" :color="item.color"  />
    </v-tabs>
  
    <!-- ウィンドウ -->
    <v-window class="content-75"
      v-model="tab" transition="slide-y-transition">
      <v-container fluid>
        <v-window-item value="spring">
          <TabSpringBoot />
        </v-window-item>
        <v-window-item value="golang">
          <TabGolang />
        </v-window-item>
        <v-window-item value="express">
          <TabExpress />
        </v-window-item>
      </v-container>
    </v-window>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import TabSpringBoot from '@/component/blocks/TabSpringBoot.vue'
import TabGolang from '@/component/blocks/TabGolang.vue'
import TabExpress from '@/component/blocks/TabExpress.vue'

/** ルーター */
const router = useRouter()

const tab = ref('');    // 初期値
// タブバーの設定
const tabsList = [
  { text: 'Spring', value: 'spring', color: '#6DB33F', icon: 'mdi-leaf' },
  { text: 'Golang', value: 'golang', color: '#00ADD8', icon: 'mdi-paw' }, // icon :'mdi-language-go'
  { text: 'Express.js', value: 'express', color: '#68A063', icon: 'mdi-nodejs' },
]

</script>

<style>
/* 25対75のレイアウト */
.layout-25-75 {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
}
.layout-25-75 .content-25 {
  width: 25%;
  min-width: 150px;
  max-width: 25%;
}
.layout-25-75 .content-75 {
  width: 75%;
}
</style>