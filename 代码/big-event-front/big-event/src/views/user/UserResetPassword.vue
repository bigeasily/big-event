<template>
    <div class="reset-password-container">
        <div class="reset-password-card">
            <h2 class="title">重置密码</h2>

            <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" class="reset-form">
                <!-- 原密码 -->
                <el-form-item label="原密码" prop="oldPassword">
                    <el-input v-model="form.oldPassword" type="password" placeholder="请输入原密码" show-password clearable />
                </el-form-item>

                <!-- 新密码 -->
                <el-form-item label="新密码" prop="newPassword">
                    <el-input v-model="form.newPassword" type="password" placeholder="请输入新密码" show-password clearable />
                    <div class="password-tips">
                        密码需包含字母或数字，长度5-16位
                    </div>
                </el-form-item>

                <!-- 确认密码 -->
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input v-model="form.confirmPassword" type="password" placeholder="请再次输入新密码" show-password
                        clearable />
                </el-form-item>

                <!-- 按钮组 -->
                <el-form-item class="button-group">
                    <el-button type="primary" :loading="loading" @click="handleSubmit()" class="submit-btn">
                        确认重置
                    </el-button>
                    <el-button @click="handleCancel">
                        取消
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

// 表单数据
const form = reactive({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
})

// 验证规则
const validateOldPassword = (rule, value, callback) => {
    if (!value) {
        callback(new Error('请输入原密码'))
    } else if (value.length < 5) {
        callback(new Error('密码长度不能少于5位'))
    } else {
        callback()
    }
}

const validateNewPassword = (rule, value, callback) => {
    const passwordRegex = /^[a-zA-Z0-9]{5,16}$/

    if (!value) {
        callback(new Error('请输入新密码'))
    } else if (!passwordRegex.test(value)) {
        callback(new Error('密码需包含字母或数字，长度5-16位'))
    } else if (value === form.oldPassword) {
        callback(new Error('新密码不能与原密码相同'))
    } else {
        callback()
    }
}

const validateConfirmPassword = (rule, value, callback) => {
    if (!value) {
        callback(new Error('请再次输入密码'))
    } else if (value !== form.newPassword) {
        callback(new Error('两次输入的密码不一致'))
    } else {
        callback()
    }
}

const rules = {
    oldPassword: [
        { required: true, validator: validateOldPassword, trigger: 'blur' }
    ],
    newPassword: [
        { required: true, validator: validateNewPassword, trigger: 'blur' }
    ],
    confirmPassword: [
        { required: true, validator: validateConfirmPassword, trigger: 'blur' }
    ]
}
import { userResetPasswordService } from '@/api/user'
// 提交表单
const handleSubmit = async () => {
    try {
        // 验证表单
        await formRef.value.validate()
        const requestData = {
            old_pwd: form.oldPassword,
            new_pwd: form.newPassword,
            re_pwd: form.confirmPassword
        }

        // 显示确认对话框
        await ElMessageBox.confirm('确定要重置密码吗？', '提示', {
            type: 'warning',
            confirmButtonText: '确定',
            cancelButtonText: '取消'
        })
        let result = await userResetPasswordService(requestData);
        console.log(result)
        // 成功的情况
        ElMessage.success(result.message || '重置成功')

        // 显示成功消息后跳转
        setTimeout(() => {
            router.push('/login')
            ElMessage.success('请重新登录')
        }, 1000) // 延迟1秒让用户看到成功消息


        // 重置表单
        formRef.value.resetFields()



    } catch (error) {
        if (error === 'cancel') {
            // 用户取消操作，不处理
            return
        }

    } finally {
        loading.value = false
    }
}

// 取消操作
const handleCancel = () => {
    formRef.value.resetFields()
    router.back() // 返回上一页
}



</script>

<style scoped>
.reset-password-container {
    min-height: 94%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 20px;
}

.reset-password-card {
    background: white;
    border-radius: 12px;
    padding: 40px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 500px;
}

.title {
    text-align: center;
    margin-bottom: 30px;
    color: #333;
    font-size: 24px;
    font-weight: 600;
}

.reset-form {
    margin-top: 20px;
}

.password-tips {
    font-size: 12px;
    color: #666;
    margin-top: 4px;
}

.button-group {
    display: flex;
    justify-content: center;
    gap: 20px;
    margin-top: 30px;
}

.submit-btn {
    min-width: 120px;
}

:deep(.el-form-item__label) {
    font-weight: 500;
}
</style>