<template>
  <v-row align="stretch" justify="center">
    <!-- プロフィールカード -->
    <v-col cols="12" md="7">
      <v-card style="height: 100%;">
        <v-row align="center" no-gutters class="bg-primary py-2">
          <v-col cols="auto">
            <v-avatar size="80" class="ma-2">
              <img src="https://github.com/ameiro-lab.png" alt="Your Avatar" class="fit-image" />
            </v-avatar>
          </v-col>
          <v-col class="pl-4 pb-4">
            <div class="text-h5 font-weight-bold">xxx xxx</div>
            <div class="text-body-2">Web Engineer / Since 2022</div>
          </v-col>
        </v-row>
        <!-- 自己紹介 -->
        <section class="mb-6">
          <v-card-title>{{ t('aboutme.profile') }}</v-card-title>
          <v-card-text>
            <span
              v-for="(line, index) in profileText"
              :key="index"
              class="formatted-line">
              {{ line }}<br />
            </span>
          </v-card-text>
        </section>
      </v-card>
    </v-col>

    <!-- レーダーチャートカード -->
    <v-col cols="12" md="5">
      <v-card
        style="height: 100%;"
        @mouseenter="isRadarHovered = true"
        @mouseleave="isRadarHovered = false">
        <template v-if="!isRadarHovered">
          <Radar :data="data" :options="options" />
        </template>
        <template v-else>
          <v-card-title class="mb-3">
            {{ t('aboutme.tech') }}
            <span class="text-medium-emphasis text-subtitle-2">{{ t('aboutme.tech.lastmodi') }}</span>
          </v-card-title>
          <v-card-text>
            <v-row no-gutters class="mb-1"
              v-for="(item, index) in stackList" :key="item.label">
              <v-col cols="1" />
              <v-col cols="5" class="mb-1">
                <strong>
                  {{ item.label }}
                </strong>
              </v-col>
              <v-col class="text-center">
                {{ item.value }}%
                <v-icon v-if="item.value >= 80"
                  color="primary" size="small">mdi-crown</v-icon>
              </v-col>
            </v-row>
          </v-card-text>
        </template>
      </v-card>
    </v-col>

    <!-- 資格 -->
    <v-col cols="12" md="12">
      <v-card >
        <v-card-title class="text-h6 mb-2">
          {{ t('aboutme.qualification') }}
        </v-card-title>
        <v-card-text>
          <v-row
            class="mb-2 pl-3" no-gutters
            v-for="item in qualificationList" :key="item.name">
            <v-col cols="12">
              <v-chip color="primary" variant="outlined" size="small">
                {{ item.name }}
              </v-chip>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-col>

    <!-- 経歴 -->
    <v-col cols="12" md="12">
      <v-card >
        <v-card-title class="text-h6 mb-2">
          {{ t('aboutme.projects') }}
        </v-card-title>
        
        <!-- スマホ版 -->
        <div v-if="$vuetify.display.smAndDown">
          <v-expansion-panels multiple variant="accordion">
            <v-expansion-panel rounded="0"
              v-for="(project, index) in projectList"
              :key="project.title">
              <v-expansion-panel-title color="primary"
              >
                {{ project.title }}
              </v-expansion-panel-title>
              <v-expansion-panel-text>
                <!-- 期間 -->
                <v-row no-gutters class="my-2 text-medium-emphasis">
                  <v-col>
                    <v-icon class="mr-1">mdi-calendar</v-icon>
                    {{ project.period }}
                  </v-col>
                </v-row>
                <!-- 顧客 -->
                <v-row no-gutters class="mb-2 text-medium-emphasis">
                  <v-col>
                    <v-icon class="mr-1">mdi-account</v-icon>
                    {{ project.client }}
                  </v-col>
                </v-row>
                <!-- 役割 -->
                <v-row no-gutters class="mb-2 text-medium-emphasis">
                  <v-col>
                    <v-icon class="mr-1">mdi-briefcase</v-icon>
                    {{ project.role }}
                  </v-col>
                </v-row>
                <!-- 詳細
                <v-row no-gutters class="mb-2">
                  {{ project.description }}
                </v-row> -->
                <!-- 技術スタック -->
                <v-row no-gutters class="mb-2">
                  <v-chip-group column>
                    <v-chip
                      v-for="tech in project.techList"
                      :key="tech"
                      color="primary"
                      variant="outlined"
                      size="small">
                      {{ tech }}
                    </v-chip>
                  </v-chip-group>
                </v-row>
              </v-expansion-panel-text>
            </v-expansion-panel>
          </v-expansion-panels>
        </div>

        <!-- PC版 -->
        <div v-else>
          <v-timeline
            class="pb-5 px-5"
            align="start" side="end" line-color="primary"
            truncate-line="end">
            <v-timeline-item
              v-for="(project, index) in projectList"
              :key="index"
              min-width="100%"
              :dot-color="'primary'"
              :icon="'mdi-briefcase-outline'">
              <!-- 期間 -->
              <template #opposite>
                <div class="text-grey-darken-1">{{ project.period }}</div>
              </template>
              <!-- カード -->
              <v-card>
                <v-card-title class="text-h6 bg-primary">{{ project.title }}</v-card-title>
                <v-card-subtitle class="pt-4">
                  {{ project.client }} — {{ project.role }}
                </v-card-subtitle>
                <v-card-text>
                  {{ project.description }}
                  <v-chip-group column class="mt-3" tag="div">
                    <v-chip
                      v-for="tech in project.techList"
                      :key="tech"
                      class="ma-1"
                      size="small"
                      color="primary"
                      variant="outlined">
                      {{ tech }}
                    </v-chip>
                  </v-chip-group>
                </v-card-text>
              </v-card>
            </v-timeline-item>
          </v-timeline>
        </div>
      </v-card>
    </v-col>
  </v-row>

  <!-- 吹き出し -->
  <div style="position: relative; bottom: 35px">
    <SpeechBubble
      ref="speechBubble" position="bottom" />
  </div>
  <!-- キャラクター -->
  <v-row align="stretch" justify="center">
    <v-col cols="auto">
      <div style="width: 200px; height: auto;">
        <MugiShithigt
          ref="mugiShithigt"
          @on-click="onclickMugi"  />
      </div>
    </v-col>
  </v-row>

</template>

<script setup>
import { ref } from 'vue'
import { useDisplay } from 'vuetify'
import { useI18n } from 'vue-i18n'
import MugiShithigt from '@/component/thing/MugiShithigt.vue';
import SpeechBubble from '@/component/thing/SpeechBubble.vue';

/** plugins */
const { t } = useI18n()

/** DOM参照 */
const mugiShithigt = ref({})
const speechBubble = ref(false)

/** チャート.js関連 */
import { Radar } from 'vue-chartjs'
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
} from 'chart.js'
ChartJS.register(
  RadialLinearScale, PointElement, LineElement,
  Filler, Tooltip,Legend
)
// スタックリスト
const stackList = [
  { label: t('aboutme.tech.fe'), text: 'Vue.js(JS/TS), HTML/CSS', value: 82 },
  { label: t('aboutme.tech.arch'), text: 'DDD, Clean Architecture', value: 28 },
  { label: t('aboutme.tech.infra'), text: 'Docker, AWS×', value: 32 },
  { label: t('aboutme.tech.db'), text: 'PostgreSQL', value: 42 },
  { label: t('aboutme.tech.be'), text: 'SpringBoot(Java), Golang, Node.js(JS/TS)', value: 56 },
  { label: t('aboutme.tech.test'), text: 'JUnit, Jest×', value: 52 },
  { label: t('aboutme.tech.tools'), text: 'Git, VSCode, Eclipse', value: 60 },
];
// レーダーチャートに表示するデータ
const data = {
  labels: stackList.map(item => item.label),
  datasets: [
    {
      label: 'Tech Stack',
      // backgroundColor: 'rgba(255, 205, 86, 0.2)',          // やさしい黄色の透明塗り
      // borderColor: 'rgba(255, 205, 86, 1)',                // 線は明るく引き締める黄色
      // pointBackgroundColor: 'rgba(255, 205, 86, 1)',       // 各点も同じ黄色で統一感
      // pointBorderColor: '#fff',                            // 白で際立たせる
      // pointHoverBackgroundColor: '#fff',                   // ホバー時は白でふんわり
      // pointHoverBorderColor: 'rgba(255, 205, 86, 1)',      // ホバー外枠は黄色で明るく
      backgroundColor: 'rgba(218, 165, 105, 0.2)',          // 背景は麦色をイメージした淡い茶色
      borderColor: 'rgba(218, 165, 105, 1)',                // 枠線も同系色で落ち着きのある印象に
      pointBackgroundColor: 'rgba(218, 165, 105, 1)',       // 点も同じカラーで統一感
      pointBorderColor: '#fff',                             // 白い枠でくっきり感をプラス
      pointHoverBackgroundColor: '#fff',                    // ホバー時は白でやわらかく
      pointHoverBorderColor: 'rgba(218, 165, 105, 1)',      // ホバー外枠は同じ色で統一
      data: stackList.map(item => item.value),
    },
  ]
}
// レーダーチャートのオプション設定
const options = {
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    r: {
      min: 0,
      max: 100,
      ticks: {
        stepSize: 20, // お好みで調整可能（10, 25 などもOK）
        color: 'grey' // 数値の色
      },
      grid: {
        // color: 'orange',
      },
      pointLabels: {
        color: 'black', // 項目テキストの色
      },
    },
  },
  // ツールチップの設定
  plugins: {
    tooltip: {
      callbacks: {
        label: function (context) {
          const label = context.label;
          const match = stackList.find(item => item.label === label);
          return match ? `${match.text}` : label;
        },
      },
      backgroundColor: 'rgba(255, 255, 255, 0.9)', // 背景色（任意）
      titleColor: '#000',  // タイトルの色
      bodyColor: '#333',   // 本文の色
      borderColor: '#ccc', // 枠線の色
      borderWidth: 1,
      titleFont: { weight: 'bold' }, // フォント太さなども設定可能
    },
  },
}

/** 
 * リアクティブデータの定義
 */
const isRadarHovered = ref(false) // レーダーチャートのホバー状態を管理
const profileText = ref(formatText(t('aboutme.profile.text')))  // i18nでメッセージを取得し、改行を処理
const qualificationList = [       // 資格リスト
  { name: 'Oracle Certified Java Programmer, Silver SE 1', date: '2024年4月' },
  // { name: '秘書技能検定準１級', date: '2017年2月' },
  // { name: 'ビジネス能力検定3級', date: '2017年2月' }
];
const projectList = ref([         // プロジェクトリスト
  {
    title: t('aboutme.project3.title'),
    period: 'Jul 2024 - now',
    client: t('aboutme.project3.client'),
    role: t('aboutme.project3.role'),
    description: t('aboutme.project3.description'),
    techList: [
      'Java (JDK21)',
      'Spring Boot (3.3.2)',
      'MyBatis',
      'PostgreSQL',
      'Vue.js',
      'JavaScript (Vuetify)',
      'Node.js',
      'Docker',
      'Git'
    ]
  },
  {
    title: t('aboutme.project2.title'),
    period: 'May 2023 - Jun 2024',
    client: t('aboutme.project2.client'),
    role: t('aboutme.project2.role'),
    description: t('aboutme.project2.description'),
    techList: [
      'Java (JDK8)',
      'Spring Boot',
      'Spring Data JPA',
      'Oracle',
      'Vue.js',
      'React',
      'JavaScript (Vuetify, Material UI)',
      'AWS',
      'Jenkins',
      'Git'
    ]
  },
  {
    title: t('aboutme.project1.title'),
    period: 'Nov 2022 - Apr 2023',
    client: t('aboutme.project1.client'),
    role: t('aboutme.project1.role'),
    description: t('aboutme.project1.description'),
    techList: [
      'Java',
      'Spring',
      'MyBatis',
      'PostgreSQL',
      'AWS (CodeCommit)',
      'Thymeleaf',
      'Git'
    ]
  }
]);

/** 
 * メソッドの定義
 */
// 改行文字を変換する
function formatText(text) {
  return text.split('\n');
}

// ムギを押下時、
const onclickMugi = () => {
  // 吹き出しを表示する
  let message = Math.random() < 0.5 ? t('aboutme.mugi.1') : t('aboutme.mugi.2');
  speechBubble.value.showBubble(message);
  // 尻尾を振る
  mugiShithigt.value.shakeTail();
  // animateTailShake('');mugiShithigt
}

</script>

<style>
.fit-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* アスペクト比を保ちつつ、枠にピッタリ */
}
</style>