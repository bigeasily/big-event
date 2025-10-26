<script setup>

  import { onMounted } from 'vue'


onMounted(() => {
  console.log('父组件挂载完成')
})
</script>

<template>
   <router-view></router-view>
</template>

<style scoped></style>
