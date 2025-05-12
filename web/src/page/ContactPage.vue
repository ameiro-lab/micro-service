<template>
  <div class="pb-5">
    <v-card
      class="elevation-1" variant="outlined" color="primary">
      <v-card-text class="text-h5 font-weight-bold mb-3">
        {{ $t('contact.title') }}
      </v-card-text>
      
      <v-card-text class="mb-5">
        <p v-if="$t('contact.text')">{{ $t('contact.text') }}</p>

        <!-- ステップ表示部分 -->
        <div v-if="currentStep === 1">
          <v-form ref="contactForm" v-model="isFormValid" @submit.prevent="validateAndConfirm">
            <!-- お名前 -->
            <v-row>
              <v-col cols="12" md="5">
                <v-text-field
                  v-model="formData.name"
                  :label="$t('contact.name.label')"
                  :placeholder="$t('contact.name.placeholder')"
                  :rules="[rules.required]"
                  variant="outlined"
                  color="primary" />
              </v-col>
            </v-row>
            
            
            <!-- メールアドレス -->
             <v-row dense>
              <v-col cols="12" md="5">
                <v-text-field
                  v-model="formData.email"
                  :label="$t('contact.email.label')"
                  :placeholder="$t('contact.email.placeholder')"
                  :rules="[rules.required, rules.email]"
                  variant="outlined"
                  color="primary" />
              </v-col>
            </v-row>
            
            <!-- 件名チップ -->
            <v-row dense>
              <v-col cols="12">
                <p>{{ $t('contact.subject.select') }}</p>
                <v-chip-group
                  v-model="selectedSubject"
                  column
                  mandatory>
                  <v-chip
                    v-for="option in subjectOptions"
                    :key="option"
                    :value="option"
                    base-color="primary"

                    selected-class="bg-primary"
                    variant="tonal">
                    {{ option }}
                  </v-chip>
                </v-chip-group>
              </v-col>
              <!-- 件名入力（選択された内容を編集可） -->
              <v-col cols="12">
                <v-text-field
                  v-model="formData.subject"
                  :label="$t('contact.subject.label')"
                  :placeholder="$t('contact.subject.placeholder')"
                  :rules="[rules.required]"
                  variant="outlined"
                  color="primary" />
              </v-col>
            </v-row>
            
            <!-- お問い合わせ内容 -->
             <v-row dense>
              <v-col cols="12">
                <v-textarea
                  v-model="formData.message"
                  :label="$t('contact.message.label')"
                  :placeholder="$t('contact.message.placeholder')"
                  :rules="[rules.required]"
                  variant="outlined"
                  color="primary"
                  rows="6" />
              </v-col>
            </v-row>

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
              {{ $t('contact.back') }}
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
          {{ $t('contact.close') }}
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();

// フォームの状態管理
const contactForm = ref(null);
const currentStep = ref(1);
const isFormValid = ref(false);
const isSubmitting = ref(false);

// フォームデータ
const formData = reactive({
  name: '',
  email: '',
  subject: '',
  message: ''
});

const selectedSubject = ref('')
const subjectOptions = [
  t('contact.subject.request'),
  t('contact.subject.collab'),
  t('contact.subject.other')
]

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

// 送信ボタン押下時、
const validateAndConfirm = async () => {

  console.log("selectedSubject:", selectedSubject.value);
  console.log("name:", formData.name);
  console.log("email:", formData.email);
  console.log("message:", formData.message);
  console.log("subject:", formData.subject);

  // バリデーション実行
  const { valid } = await contactForm.value.validate();

  if (!valid) {
    // バリデーションエラー時
    console.warn(t('validation.invalid'));
    // 例：ページ上部にスクロールしてエラーを目立たせる
    window.scrollTo({ top: 0, behavior: 'smooth' });

  } else {
    // 確認画面に進む
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
    snackbar.text = t('contact.success');
    snackbar.color = 'success';
    snackbar.show = true;
    
    // フォームリセット
    resetForm();

  } catch (error) {
    // エラー時の処理
    console.error('送信エラー:', error);
    snackbar.text = t('contact.failure');
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