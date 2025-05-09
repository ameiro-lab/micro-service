import { createI18n } from 'vue-i18n'
import ja from '../locales/ja'
import en from '../locales/en'

// ローカルストレージから言語設定を取得、なければデフォルトは 'ja'
const storedLang = localStorage.getItem('lang') || 'ja';

const i18n = createI18n({
  legacy: false,
  locale: storedLang, // デフォルト
  fallbackLocale: 'en',
  messages: {
    ja,
    en,
  }
})

export default i18n
