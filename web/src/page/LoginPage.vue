<template>
  <div class="bg-login-page pa-12">
    <!-- <Mugi /> -->

    <!-- ログイン -->
    <v-card class="mx-auto px-6 py-4" max-width="350">
      <v-card-title class="text-center">Ready to Paw In? </v-card-title>
      <v-form
        v-model="form"
        @submit.prevent="onLogin">
        <!-- フォーム -->
        <v-text-field
            v-model="userName"
            :readonly="isLoading"
            label="User Name"
            clearable />
        <v-text-field
            v-model="password"
            :readonly="isLoading"
            label="Password"
            clearable />
        <!-- ボタン -->
        <CoreButton label="Login" type="submit" />
      </v-form>
    </v-card>
  </div>
  
  <!-- スナックバー -->
  <v-snackbar
    v-model="isSackbar" timeout="2000"
    location="top" transition="slide-y-reverse-transition" color="primary">
    Uh-oh... We can't find your pawprint.
  </v-snackbar>
  <!-- api動作確認ボタン -->
  <!-- <CoreButton label="TEST API" @click="apiTest" /> -->
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/api/api'
import CoreButton from '@/component/thing/CoreButton.vue';
// import Mugi from '@/component/thing/Mugi.vue';

/** ルーター */
const router = useRouter()

/** リアクティブデータの定義 */
const form = ref(false);
const isLoading = ref(false);
const isSackbar = ref(false);
const userName = ref(null);
const password = ref(null);

/** メソッドの定義 */
// ログインボタン押下時
const onLogin = async () => {

  // ローディングを開始する。
  isLoading.value = true;

  // リクエストボディを作成する。
  const data = {
    'userName': userName.value,
    'password': password.value
  };

  // API通信（POST）
  await api.login(data).then(res => {
    const { status, data, message } = res.data;

    if (status === 200) {
      // console.log("Success:", data);
      // ホーム画面に遷移する。
      router.push({ name: 'home' })

    } else {
      console.log("status", status);
      console.log("message", message);
    }

  }).catch(error => {
    console.error("ERR_MSG", error.response.message);
    console.error(error);
    // スナックバーを表示する。
    isSackbar.value = true;
});

  // ローディングを終了する。
  isLoading.value = false;
};

// api通信動作確認用
const apiTest = async () => {

  try {
    const res = await api.apiTest(requestData);
    const { status, data, message } = res.data;

    if (status === 200) {
      console.log("Success:", data);
      text.value = message;
    } else if (status === 401) {
      console.error("Error:", message);
    }

  } catch (error) {
    console.error("Error:", error);
  }
}
</script>

<style>
.bg-login-page {
  background-image: url('@/assets/bg-img/bg_oshiro_castle.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 100vh; /* 画面全体を覆う */
}
</style>