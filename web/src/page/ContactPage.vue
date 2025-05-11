<template>
  <v-container class="px-4" max-width="600">
        <v-card class="pa-8 elevation-1" color="primary" variant="outlined">
          <v-card-title class="text-h5 font-weight-bold mb-6">
            {{ $t('contact.title') }}
          </v-card-title>
          
          <v-card-text>
            <p v-if="$t('contact.text')">{{ $t('contact.text') }}</p>

            <!-- ステップ表示部分 -->
            <div v-if="currentStep === 1">
              <v-form ref="contactForm" v-model="isFormValid" @submit.prevent="validateAndConfirm">
                <!-- お名前 -->
                <v-text-field
                  v-model="formData.name"
                  :label="$t('contact.name.label')"
                  :placeholder="$t('contact.name.placeholder')"
                  :rules="[rules.required]"
                  variant="outlined"
                  color="primary"
                  class="mb-4"
                ></v-text-field>
                
                <!-- メールアドレス -->
                <v-text-field
                  v-model="formData.email"
                  :label="$t('contact.email.label')"
                  :placeholder="$t('contact.email.placeholder')"
                  :rules="[rules.required, rules.email]"
                  variant="outlined"
                  color="primary"
                  class="mb-4"
                ></v-text-field>
                
                <!-- 件名 -->
                <v-text-field
                  v-model="formData.subject"
                  :label="$t('contact.subject.label')"
                  :placeholder="$t('contact.subject.placeholder')"
                  :rules="[rules.required]"
                  variant="outlined"
                  color="primary"
                  class="mb-4"
                ></v-text-field>
                
                <!-- お問い合わせ内容 -->
                <v-textarea
                  v-model="formData.message"
                  :label="$t('contact.message.label')"
                  :placeholder="$t('contact.message.placeholder')"
                  :rules="[rules.required]"
                  variant="outlined"
                  color="primary"
                  rows="6"
                  class="mb-4"
                ></v-textarea>
                <!-- 送信 -->
                <div class="d-flex justify-center mt-4">
                  <v-btn
                    color="primary"
                    type="submit"
                    :disabled="!isFormValid">
                    {{ $t('contact.confirm') }}
                  </v-btn>
                </div>
              </v-form>
            </div>
            
            <!-- ステップ2: 確認画面 -->
            <div v-else-if="currentStep === 2">
              <v-list>
                <v-list-item>
                  <v-list-item-title>{{ $t('contact.name.label') }}</v-list-item-title>
                  <v-list-item-subtitle>{{ formData.name }}</v-list-item-subtitle>
                </v-list-item>
                
                <v-divider class="mb-3" />
                
                <v-list-item>
                  <v-list-item-title>{{ $t('contact.email.label') }}</v-list-item-title>
                  <v-list-item-subtitle>{{ formData.email }}</v-list-item-subtitle>
                </v-list-item>
                
                <v-divider class="mb-3" />
                
                <v-list-item>
                  <v-list-item-title>{{ $t('contact.subject.label') }}</v-list-item-title>
                  <v-list-item-subtitle>{{ formData.subject }}</v-list-item-subtitle>
                </v-list-item>
                
                <v-divider class="mb-3" />
                
                <v-list-item>
                  <v-list-item-title>{{ $t('contact.message.label') }}</v-list-item-title>
                  <v-list-item-subtitle class="text-wrap">{{ formData.message }}</v-list-item-subtitle>
                </v-list-item>
              </v-list>
              <!-- ボタン -->
              <div class="d-flex justify-center gap-4 mt-4">
                <v-btn variant="outlined" @click="currentStep = 1">
                  戻る
                </v-btn>
                <v-btn class="ml-5"
                  color="primary" @click="submitForm"
                  :loading="isSubmitting" >
                  {{ $t('contact.submit') }}
                </v-btn>
              </div>
            </div>
          </v-card-text>
        </v-card>
        
        <!-- 送信後のスナックバー -->
        <v-snackbar
          v-model="snackbar.show"
          :color="snackbar.color"
          :timeout="3000"
        >
          {{ snackbar.text }}
          <template v-slot:actions>
            <v-btn
              color="white" variant="text"
              @click="snackbar.show = false">
              閉じる
            </v-btn>
          </template>
        </v-snackbar>

  </v-container>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();

// フォームの状態管理
const contactForm = ref(null);
const currentStep = ref(1);
const isFormValid = ref(false);
const isSubmitting = ref(false);

// フォームデータ
const formData = reactive({
  name: 'a',
  email: 'example@xx.com',
  subject: 'a',
  message: 'a'
});

// バリデーションルール
const rules = {
  required: value => !!value || t('validation.required'),
  email: value => {
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return pattern.test(value) || t('validation.invalidEmail');
  }
};

// 通知メッセージの状態
const snackbar = reactive({
  show: false,
  text: '',
  color: 'success'
});

// バリデーション実行して確認画面に進む
const validateAndConfirm = async () => {
  const { valid } = await contactForm.value.validate();
  
  if (valid) {
    currentStep.value = 2;
  }
};

// フォーム送信処理
const submitForm = async () => {
  isSubmitting.value = true;
  
  try {
    // 実際の送信処理をここに実装
    // 例: await axios.post('/api/contact', formData);
    
    // 成功時の処理
    snackbar.text = 'お問い合わせを送信しました。ありがとうございます。';
    snackbar.color = 'success';
    snackbar.show = true;
    
    // フォームリセット
    resetForm();
  } catch (error) {
    // エラー時の処理
    console.error('送信エラー:', error);
    snackbar.text = '送信に失敗しました。しばらく経ってからお試しください。';
    snackbar.color = 'error';
    snackbar.show = true;
  } finally {
    isSubmitting.value = false;
  }
};

// フォームリセット
const resetForm = () => {
  contactForm.value?.reset();
  Object.keys(formData).forEach(key => {
    formData[key] = '';
  });
  currentStep.value = 1;
};
</script>