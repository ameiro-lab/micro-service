<template>
  <v-row>
    <!-- テキスト -->
    <v-col cols="7">
      <v-card color="green">
        <v-card-title class="font-weight-bold">Welcome to my portfolio!</v-card-title>
        <v-card-text>
          <p>Here, you’ll find an introduction to who I am as a developer, the projects I’ve built, and the journey I’m on—with a little help from my loyal assistant, Mugi the dog 🐕💻</p>
          <p>Whether you’re sniffing around out of curiosity or scouting for talent, I hope you enjoy exploring this space!</p>
        </v-card-text>

        <v-card-title class="font-weight-bold">About Me</v-card-title>
        <v-card-text>
          <p>I am a passionate software developer based in Tokyo, Japan.</p>
          <p>My career began in a completely different field. I studied the humanities at junior college and worked in an administrative role for five years. However, my curiosity for technology and my love for creating things led me to transition into software development in 2022. Since then, I have been continuously learning and improving my skills in web development.</p>
          <p>Every day brings new challenges and opportunities for growth, but I find great joy in coding and building meaningful applications. This journey has been both exciting and fulfilling, and I am eager to continue deepening my expertise in the field.</p>
          <p>I am constantly striving to improve my web development skills and actively embracing new technologies to become a better developer.</p>
        </v-card-text>
      </v-card>
    </v-col>
    <!-- レーダーチャート -->
    <v-col cols="5">
      <v-card color="base">
        <Radar :data="data" :options="options" />
      </v-card>
    </v-col>
  </v-row>
  
</template>

<script setup>
import { ref } from 'vue'
import api from '@/api/api'


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
        color: 'rgba(218, 165, 105, 1)', // 項目テキストの色
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

/** リアクティブデータの定義 */

/** メソッドの定義 */

</script>