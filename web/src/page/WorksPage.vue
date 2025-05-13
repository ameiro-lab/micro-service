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

  <v-row dense>
    <!-- タブバー -->
    <v-col cols="auto" class="mr-12">
      <v-tabs
        v-model="tab"
        :direction="mobile ? 'horizontal' : 'vertical'">
        <v-tab v-for="item in tabsList" :key="item.value"
          :value="item.value" :text="item.text"
          :prepend-icon="item.icon" :color="item.color"  />
      </v-tabs>
    </v-col>
    <!-- ウィンドウ -->
    <v-col cols="12" md="9">
      <v-window
        v-model="tab">
        <v-sheet class="pa-3">
          <v-window-item value="spring" transition="none" reverse-transition="none">
            <TabSpringBoot />
          </v-window-item>
          <v-window-item value="golang" transition="none" reverse-transition="none">
            <TabGolang />
          </v-window-item>
          <v-window-item value="express" transition="none" reverse-transition="none">
            <TabExpress />
          </v-window-item>
        </v-sheet>
      </v-window>
    </v-col>
  </v-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useDisplay } from 'vuetify'
import TabSpringBoot from '@/component/blocks/TabSpringBoot.vue'
import TabGolang from '@/component/blocks/TabGolang.vue'
import TabExpress from '@/component/blocks/TabExpress.vue'
import SweetPotato from '@/component/thing/SweetPotato.vue';

/** plugins */
const { t } = useI18n()
const { mobile } = useDisplay() // Vuetifyのレスポンシブ情報を取得

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