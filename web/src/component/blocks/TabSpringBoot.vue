<template>
  <!-- User Importボタン -->
  <v-btn text="Import User" color="primary" variant="flat" @click="userImport" />
</template>

<script setup>
import { ref } from 'vue'
import api from '@/api/api'

/** リアクティブデータの定義 */

/** メソッドの定義 */
// user import押下時
const userImport = async () => {

  try {
    const res = await api.userImport();
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