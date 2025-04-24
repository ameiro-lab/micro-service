/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// Composables
import { createVuetify } from 'vuetify'

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    defaultTheme: 'light',
    themes: {
      light: {
        colors: {
          primary: '#8B5E3C',
          base: '#FAF9F6',
          accent: '#FFEB3B',
          green: '#6B8E23',
          text: '#333333',
          link: '#8BC6E0',
        },
      },
    },
  },
  defaults: {
    // 各textプロパティの uppercase を無効化
    VBtn: { style: 'text-transform: none;' },
    VTab: { style: 'text-transform: none;' },
    VChip: { style: 'text-transform: none;' },
  },
})
