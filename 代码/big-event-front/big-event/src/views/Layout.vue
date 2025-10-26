<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom,
    ElementPlus
} from '@element-plus/icons-vue'
import {ElMessage,ElMessageBox} from 'element-plus'
import avatar from '@/assets/default.png'
//导入接口函数
import {userInfoGetService} from '@/api/user.js'
//导入pinia
import {useUserInfoStore} from '@/stores/user.js'
const userInfoStore = useUserInfoStore();
import {ref} from 'vue'
import { info } from 'sass';
import touxiangImg from '@/assets/touxiang.png'
//图片URL
const touxiang = ref(touxiangImg)
const getUserInfo = async () => {
  try {
    const res = await userInfoGetService()
    
    if (JSON.stringify(res.data) !== JSON.stringify(userInfoStore.info)) {
      userInfoStore.setInfo(res.data)
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}
getUserInfo()

//dropDown条目被点击后，回调的函数
import {useRouter} from 'vue-router'
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore()
const router = useRouter()
const handleCommand = async (command) => {
    if (command === 'logout') {
        try {
            await ElMessageBox.confirm(
                '你确认退出登录吗？', 
                '温馨提示',
                {
                    confirmButtonText: '确认',
                    cancelButtonText: '取消',
                    type: 'warning',
                }
            )
            
            // 用户点击了确认 - 执行退出登录逻辑
            userInfoStore.clearUserInfo()
            tokenStore.removeToken()
            
            // 添加小的延迟确保状态更新完成
            await new Promise(resolve => setTimeout(resolve, 10))
            
            // 跳转到登录页
            router.push('/login')
            ElMessage.success("退出成功")
        } catch (error) {
            // 区分是用户取消还是其他错误
            if (error === 'cancel' || error === 'close') {
                // 用户点击了取消或关闭按钮
                ElMessage({
                    type: 'info',
                    message: '取消退出',
                })
            } else {
                // 其他错误
                console.error('退出登录出错:', error)
                ElMessage({
                    type: 'error',
                    message: '操作失败，请重试',
                })
            }
        }
    } else {
        router.push('/user/' + command)
    }
}

</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#232323"  text-color="#fff"
                router>
                <el-menu-item index = '/article/category'>
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>文章分类</span>
                </el-menu-item>
                <el-menu-item index ='/article/manage'>
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>文章管理</span>
                </el-menu-item>
                <el-sub-menu >
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index ='/user/info'>
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index ='/user/avatar'>
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index ='/user/password'>
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div>黑马程序员：<strong>{{ userInfoStore.info.nickname }}</strong></div>
                <!-- <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" /> -->
                <!-- 下拉菜单 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="touxiang" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="password" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>大事件 ©2023 Created by 黑马程序员</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>