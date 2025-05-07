<template>
  <!-- wellcome -->
  <v-row dense>
    <v-col cols="12">
      <v-card color="green" variant="outlined">
        <v-card-title class="text-h5 font-weight-bold">Welcome to my portfolio!</v-card-title>
        <v-card-text class="text-body-1">
          <p>
            Here, you’ll get a peek into who I am as a developer—my story, my skills, <br />
            and the projects I’ve had the pleasure of working on. <br />
            And of course, I’m not doing this alone—my trusty sidekick, Mugi the dog, is here to guide you.
          </p>
          <p>
            Whether you’re just curious or scouting for talent, I hope this space brings a smile to your face.
          </p>
          <p>
            So, first things first — summon your fuzzy buddy with a sweet potato!
          </p>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col cols="12">
      <CoreButton label="Come here, Mugi!" @click="callMugi" />
    </v-col>
  </v-row>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { gsap } from 'gsap'
import { useRouter } from 'vue-router'
import CoreButton from '@/component/thing/CoreButton.vue'

/** ルーター */
const router = useRouter()

// メニューページに遷移する
function callMugi() {
  router.push({ name: 'home' });
}

</script>