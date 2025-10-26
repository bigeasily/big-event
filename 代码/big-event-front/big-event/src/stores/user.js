// stores/userInfo.js
import { defineStore } from "pinia"
import { ref } from 'vue'

export const useUserInfoStore = defineStore('userInfo', () => {
    // 1.定义用户信息
    const info = ref({})
    
    // 2.定义修改用户信息的方法
    const setInfo = (newInfo) => {
        info.value = newInfo
    }
    
    // 3.定义清空用户信息的方法
    const removeInfo = () => {
        info.value = {}  // 保持响应式，但清空内容
    }
    
    // 4.添加一个安全清除方法
    const clearUserInfo = () => {
        removeInfo()
    }

    return { info, setInfo, removeInfo, clearUserInfo }
}, {
    persist: true
})