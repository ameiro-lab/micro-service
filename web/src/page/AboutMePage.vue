<template>
<v-container fluid>
  <v-row align="stretch" justify="start">
    <!-- プロフィールカード -->
    <v-col cols="12" md="7">
      <v-card class="pa-3" color="base" style="height: 100%;">
        <v-row align="center" no-gutters class="pb-4 mb-4">
          <v-col cols="auto">
            <v-avatar size="80" class="shadow-4">
              <img src="https://github.com/ameiro-lab.png" alt="Your Avatar" class="fit-image" />
            </v-avatar>
          </v-col>
          <v-col class="pl-4">
            <div class="text-h5 font-weight-bold">xxx xxx</div>
            <div class="text-body-2 text-grey">Web Engineer / Since 2022</div>
          </v-col>
        </v-row>
        <!-- 自己紹介 -->
        <section class="mb-6">
          <v-card-title>Profile</v-card-title>
          <v-card-text>
            <p>東京を拠点に活動しているWebエンジニアです。</p>
            <p>社会人としての第一歩は事務職でしたが、モノづくりへの興味から2022年にJavaを学び始めました。
            現在の担当は、業務システムのフロントエンド開発です。業務システムといえば、効率や機能を重視することが多い一方で、そのような中でもUIやアニメーションに遊び心を加えることを大切にしています。
            ユーザーが日々使うたびに明るい気持ちになるような、ユニークな提案ができるエンジニアを目指しています。</p>
          </v-card-text>
        </section>
      </v-card>
    </v-col>

    <!-- レーダーチャートカード -->
    <v-col cols="12" md="5">
      <v-card
        class="pa-3"
        style="height: 100%;"
        @mouseenter="isRadarHovered = true"
        @mouseleave="isRadarHovered = false">
        <template v-if="!isRadarHovered">
          <Radar :data="data" :options="options" />
        </template>
        <template v-else>
          <v-card-title>
            My Skillset
          </v-card-title>
          <v-card-text>
            <div v-for="(item, index) in stackList" :key="index">
              <v-row no-gutters>
                <v-col cols="12" class="mb-1">
                  <strong>{{ item.label }}</strong>:　{{ getDescription(item.label) }}
                </v-col>
              </v-row>
            </div>
          </v-card-text>
        </template>
      </v-card>
    </v-col>

    <!-- 資格 -->
    <v-col cols="12" md="12">
      <v-card class="pa-3">
        <v-card-title class="text-h6 mb-2">
          Qualification
        </v-card-title>
        <div
            v-for="item in qualificationList" :key="item.name"
            class="d-flex flex-column">
            <v-row class="mb-2" no-gutters>
              <v-col cols="12">
                <v-chip class="w-100" color="primary" text-color="white">
                  {{ item.name }}
                </v-chip>
              </v-col>
            </v-row>
          </div>
      </v-card>
    </v-col>

    <!-- 経歴 -->
    <v-col cols="12" md="12">
      <v-card class="pa-3">
      <v-card-title class="text-h6 mb-2">
        Projects
      </v-card-title>
      <v-timeline align="start" side="end" line-color="primary">
      <v-timeline-item
        v-for="(project, index) in projectList"
        :key="index"
        :dot-color="'primary'"
        :icon="'mdi-briefcase-outline'">
        <template #opposite>
          <div class="text-caption text-grey-darken-1">{{ project.period }}</div>
        </template>

        <v-card elevation="2" class="pa-4">
          <v-card-title class="text-h6">{{ project.title }}</v-card-title>
          <v-card-subtitle class="text-subtitle-2">
            {{ project.client }} — {{ project.role }}
          </v-card-subtitle>
          <v-card-text class="mt-2">
            <p>{{ project.description }}</p>
            <v-chip-group column class="mt-3" tag="div">
              <v-chip
                v-for="(tech, i) in project.techList"
                :key="i"
                class="ma-1"
                size="small"
                color="primary"
                variant="outlined"
              >
                {{ tech }}
              </v-chip>
            </v-chip-group>
          </v-card-text>
        </v-card>
      </v-timeline-item>
    </v-timeline>
      <!-- 
      パドルを使った場合
      <v-expansion-panels multiple>
        <v-expansion-panel
          v-for="(project, index) in projectList"
          :key="index"
        >
          <v-expansion-panel-title>
            {{ project.title }}
          </v-expansion-panel-title>
          <v-expansion-panel-text>
            <p><strong>📅 Period:</strong> {{ project.period }}</p>
            <p v-if="project.client"><strong>🏢 Client:</strong> {{ project.client }}</p>
            <p><strong>👨‍💻 Role:</strong> {{ project.role }}</p>
            <p class="mt-2">{{ project.description }}</p>
            <p class="mt-2"><strong>Tech Stack:</strong></p>
            <v-chip-group column>
              <v-chip
                v-for="(tech, i) in project.techList"
                :key="i"
                class="ma-1"
                size="small">
                {{ tech }}
              </v-chip>
            </v-chip-group>
          </v-expansion-panel-text>
        </v-expansion-panel>
      </v-expansion-panels> -->


    </v-card>
    </v-col>
  </v-row>
</v-container>

<!-- キャラクター -->
<v-row dense>
  <v-spacer></v-spacer>
  <v-col cols="4" class="d-none d-md-flex justify-end align-end">
    <div style="width: 200px; height: auto;">
      <BasicOsuwari @on-click="onclickBasicOsuwari" />
    </div>
  </v-col>
</v-row>

<div style="width: 200px; height: auto;">
  <VectorTest />
</div>
  
</template>

<script setup>
import { ref, computed } from 'vue'
import { useDisplay } from 'vuetify'
import BasicOsuwari from '@/component/thing/BasicOsuwari.vue'
import VectorTest from '@/component/thing/VectorTest.vue'

// Vuetifyの画面サイズ情報を取得（useDisplayはVuetifyが提供するComposable）
const { mobile } = useDisplay()

// 共通データ
const md = 6;
const titleClass = 'text-h5 font-weight-bold'
const textClass = 'text-body-1'

const isRadarHovered = ref(false)  // ホバー状態を管理

import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
} from 'chart.js'
import { Radar } from 'vue-chartjs'

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
)

/** Chart.js */
// スタックリスト
const stackList = [
  { label: 'Frontend', text: 'Vue.js(JS/TS), HTML/CSS', value: 82 },
  { label: 'Architecture', text: 'DDD, Clean Architecture', value: 28 },
  { label: 'Infrastructure', text: 'Docker, AWS×', value: 32 },
  { label: 'Database', text: 'PostgreSQL', value: 42 },
  { label: 'Backend', text: 'SpringBoot(Java), Golang, Node.js(JS/TS)', value: 56 },
  { label: 'Testing', text: 'JUnit, Jest×', value: 52 },
  { label: 'Productivity', text: 'Git, VSCode, Eclipse', value: 60 },
];
// 表示するデータ
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

// オプション設定（必要に応じてカスタマイズ）
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

// 資格リスト
const qualificationList = [
  { name: 'Oracle Certified Java Programmer, Silver SE 1', date: '2024年4月' },
  { name: '秘書技能検定準１級', date: '2017年2月' },
  { name: 'ビジネス能力検定3級', date: '2017年2月' }
];

// プロジェクトリスト（SES）
const projectList = [
  {
    title: 'Product Test Management System',
    period: 'Jul 2024 - now',
    client: 'Semiconductor Manufacturing Company',
    role: 'Group Member (Full-Stack Developer)',
    description: 'Developing a web application to streamline product testing and improve data traceability.',
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
    title: 'Batch Operation Management System',
    period: 'May 2023 - Jun 2024',
    client: 'Securities Company',
    role: 'Group Member (Full-Stack Developer)',
    description: 'Developed a portal system to replace Excel-based batch operation management in a test environment, improving efficiency and reducing costs.',
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
    title: 'Learning Management System (LMS)',
    period: 'Nov 2022 - Apr 2023',
    client: 'Educational Institution',
    role: 'Group Member (Backend Developer)',
    description: 'Developed a learning management system to support e-learning, focusing on backend performance and system architecture.',
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
];


/** リアクティブデータの定義 */

/** メソッドの定義 */
const onclickBasicOsuwari = () => {
  console.log('Osuwari clicked!')
}

// labelに応じた日本語の解説文を返すメソッド
function getDescription(label) {
  switch (label) {
    case 'Frontend':
      return 'フロントエンド開発';
    case 'Architecture':
      return 'アーキテクチャ設計';
    case 'Infrastructure':
      return 'インフラ構築';
    case 'Database':
      return 'データベース設計・運用';
    case 'Backend':
      return 'バックエンド開発';
    case 'Testing':
      return 'テスト自動化';
    case 'Productivity':
      return '生産性向上ツール';
    default:
      return '';
  }
}

</script>

<style>
.fit-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* アスペクト比を保ちつつ、枠にピッタリ */
}
</style>