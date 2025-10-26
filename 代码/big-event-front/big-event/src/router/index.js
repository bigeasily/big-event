import { createRouter , createWebHistory } from "vue-router";
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import ArticleCategoryVue from "@/views/article/ArticleCategory.vue";
import ArticleManageVue from "@/views/article/ArticleManage.vue";
import UserInfoVue from "@/views/user/UserInfo.vue";
import UserAvatarVue from "@/views/user/UserAvatar.vue";
import UserResetPasswordVue from "@/views/user/UserResetPassword.vue";

const routes = [
    {path:'/login',component:LoginVue},
    {   path: '/', 
        component: LayoutVue,
          //重定向
        redirect: '/article/category',
        //子路由
        children: [
            { path: '/article/category', component: ArticleCategoryVue },
            { path: '/article/manage', component: ArticleManageVue },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVue },
            { path: '/user/password', component: UserResetPasswordVue },
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
});
export default router