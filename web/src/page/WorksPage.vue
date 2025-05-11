<template>
  <!-- イモ -->
  <SweetPotato ref="sweetPotato" />
  <div class="text-subtitle-1 mb-4">{{ t('works.text') }}
    <!-- ヒント -->
    <v-btn icon variant="outlined" color="primary" size="x-small"
      @click="onClickHint">
      <v-icon>mdi-paw</v-icon>
    </v-btn>
  </div>

  <!-- タブバー -->
  <div class="layout-20-80":style="{ width: '100%', height: 'calc(100vh - 20vh)' }">
    <v-tabs class="content-20"
      v-model="tab" direction="vertical">
      <v-tab v-for="item in tabsList" :key="item.value"
        :value="item.value" :text="item.text"
        :prepend-icon="item.icon" :color="item.color"  />
    </v-tabs>
  
    <!-- ウィンドウ -->
    <v-window class="content-80"
      v-model="tab" transition="slide-y-transition">
      <v-sheet border
        class="pa-3"
        :style="{ height: 'calc(100vh - 20vh)' }">
        <v-window-item value="spring">
          <TabSpringBoot />
        </v-window-item>
        <v-window-item value="golang">
          <TabGolang />
        </v-window-item>
        <v-window-item value="express">
          <TabExpress />
        </v-window-item>
      </v-sheet>
    </v-window>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api/api'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import TabSpringBoot from '@/component/blocks/TabSpringBoot.vue'
import TabGolang from '@/component/blocks/TabGolang.vue'
import TabExpress from '@/component/blocks/TabExpress.vue'
import SweetPotato from '@/component/thing/SweetPotato.vue';
import MugiShit from '@/component/thing/MugiShit.vue';
import MugiShithigt from '@/component/thing/MugiShithigt.vue';

/** plugins */
const router = useRouter()
const { t } = useI18n()

/** DOM参照 */
const sweetPotato = ref({});

/** リアクティブデータの定義 */
const tab = ref('');    // タブ初期値
const tabsList = [      // タブバーの設定
  { text: 'Spring', value: 'spring', color: '#6DB33F', icon: 'mdi-leaf' },
  { text: 'Golang', value: 'golang', color: '#00ADD8', icon: 'mdi-paw' }, // icon :'mdi-language-go'
  { text: 'Express.js', value: 'express', color: '#68A063', icon: 'mdi-nodejs' },
]

/** メソッドの定義 */
// ヒント押下時、
const onClickHint = async () => {
  console.log('ヒントを押下')

  // イモを出す
    await sweetPotato.value.show();
}

onMounted(() => {

});
</script>