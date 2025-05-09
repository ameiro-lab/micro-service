import { createI18n } from 'vue-i18n'
import ja from '../locales/ja'
import en from '../locales/en'

// 対応している言語リスト
const availableLangs = { ja, en }

// ローカルストレージから言語取得
let storedLang = localStorage.getItem('lang')

// 不正な値が入っていた場合の処理（undefined, typoなど）
if (!Object.keys(availableLangs).includes(storedLang)) {
  storedLang = 'ja' // デフォルト言語
  localStorage.setItem('lang', storedLang)
  console.info(`🌏 無効な言語コードのため、'ja' を設定します`)

} else {
  // 有効な場合は、そのまま使用
  console.info(`🌏 有効な言語コードのため、` + storedLang + `を使用します`)
}

// i18nインスタンス作成
const i18n = createI18n({
  legacy: false,
  locale: storedLang,
  fallbackLocale: 'en',
  messages: availableLangs,
})

export default i18n
